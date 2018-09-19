package util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwar Alonso Rojas Blanco
 * @email edwar.red@gmail.com
 */
public class HqlLightUtil {

    Class pojoClass;

    public HqlLightUtil(Class pojoClass) {
        this.pojoClass = pojoClass;
    }

    public List parseObjectLightList(String hql, List<Object[]> valList) {
        List lst = new ArrayList();
        for (Object[] objs : valList) {
            lst.add(parseObjectLight(hql, objs));
        }
        return lst;
    }

    public <T> T parseObjectLight(String hql, Object[] vals) {
        String select = "";
        try {
            select = hql.substring(0, hql.trim().toUpperCase().indexOf("FROM")).replaceAll("SELECT", "").replaceAll("select", "");
            String[] fields = select.split(",");

            //Determinando todas las clases que intervienen
            List<Class> lstClassAll = new ArrayList<>();
            for (String field : fields) {
                field = field.trim();
                lstClassAll.addAll(getClasses(field));
            }

            //Creando Objectos Necesarios
            List<Object> lstObjs = getObjectsFromClass(lstClassAll);
            lstObjs.add(pojoClass.newInstance());

            //Estableciendo Valores a Los Objetos
            int i = 0;
            for (String field : fields) {
                factoryObject(field, lstObjs, getAttr(field), vals[i]);
                i++;
            }

            return (T) findObjectByClass(pojoClass, lstObjs);
        } catch (Exception e) {
            System.err.append("FALLA (" + this.getClass().getSimpleName() + "): Parseando HqlUtilLight >> \n " + select + "\n");
            e.printStackTrace();
            return null;
        }
    }

    private void factoryObject(String field, List<Object> lstObjs, String attr, Object value) throws Exception {
        //Separando tipos de Dato POJO
        String[] arr = field.split("\\.");
        if (arr.length > 0) {
            arr[0] = null;
        }

        if (arr.length > 2) {
            Class cVal = null;
            List<Class> lstAux = new ArrayList<>();
            for (int i = 1; i < arr.length - 1; i++) {
                if (i == 1) {
                    cVal = findMethod("get" + toFirstUpper(arr[i]).trim(), pojoClass).getReturnType();
                    Object objVal = findObjectByClass(cVal, lstObjs);

                    Method m = findMethod("set" + toFirstUpper(arr[i]).trim(), pojoClass);
                    Object objInv = findObjectByClass(pojoClass, lstObjs);
                    m.invoke(objInv, objVal);
                }

                if (i > 1) {
                    Class c0 = lstAux.get(i - 2);
                    cVal = findMethod("get" + toFirstUpper(arr[i]).trim(), c0).getReturnType();
                    Object objVal = findObjectByClass(cVal, lstObjs);

                    Method m = findMethod("set" + toFirstUpper(arr[i]).trim(), c0);
                    Object objInv = findObjectByClass(c0, lstObjs);
                    m.invoke(objInv, objVal);
                }

                lstAux.add(cVal);
            }
            //Set value
            Class cInv = lstAux.get(lstAux.size() - 1);
            Method m = findMethod("set" + toFirstUpper(arr[arr.length - 1]).trim(), cInv);
            Object objInv = findObjectByClass(cInv, lstObjs);
            m.invoke(objInv, value);

        } else {
            Method m = findMethod("set" + toFirstUpper(arr[1]).trim(), pojoClass);
            Object objInv = findObjectByClass(pojoClass, lstObjs);
            m.invoke(objInv, value);
        }

    }

    private Method findMethod(String name, Class c) {
        Method[] arrM = c.getMethods();
        for (Method m : arrM) {
            if (m.getName().trim().equals(name)) {
                return m;
            }
        }
        return null;
    }

    private Object findObjectByClass(Class c, List<Object> lstObjs) {
        for (Object obj : lstObjs) {
            if (obj.getClass().getSimpleName().trim().equals(c.getSimpleName().trim())) {
                return obj;
            }
        }
        return null;
    }

    private List<Object> getObjectsFromClass(List<Class> lstClass) throws Exception {
        List<Class> lstC = new ArrayList<>();
        for (Class c : lstClass) {
            if (!isClassInList(c, lstC)) {
                lstC.add(c);
            }
        }
        List<Object> lstObjs = new ArrayList<>();
        for (Class cn : lstC) {
            lstObjs.add(cn.newInstance());
        }

        return lstObjs;
    }

    private boolean isClassInList(Class c, List<Class> lstC) {
        for (Class c0 : lstC) {
            if (c0.getName().trim().equals(c.getName().trim())) {
                return true;
            }
        }
        return false;
    }

    private String getAttr(String expr) {
        String[] arr = expr.split("\\.");
        if (arr.length > 1) {
            return arr[arr.length - 1];
        }
        return null;
    }

    private List<Class> getClasses(String expr) throws Exception {
        List<Class> lstClass = new ArrayList<>();

        //Separando tipos de Dato POJO
        String[] arr = expr.split("\\.");
        if (arr.length > 0) {
            arr[0] = null;
            if (arr.length > 1) {
                arr[arr.length - 1] = null;
            }
        }

        int i = 0;
        for (String s : arr) {
            if (s != null) {
                Class c = null;
                if (i == 0) {
                    c = pojoClass.getMethod("get" + toFirstUpper(s).trim()).getReturnType();
                } else {
                    Class c0 = lstClass.get(i - 1);
                    c = c0.getMethod("get" + toFirstUpper(s).trim()).getReturnType();
                }

                lstClass.add(c);
                i++;
            }
        }
        return lstClass;
    }

    private String toFirstUpper(String s) {
        String s2 = SerialClone.clone(s);
        s2 = s2.substring(1, s2.length());
        return s.substring(0, 1).toUpperCase() + s2;
    }

    public static void main(String[] args) {
        String hql = " SELECT c.idcartera,c.idperiodo.nombre, c.idmatricula.idvinculacion.idcarrera.nombre,c.saldocartera"
                + "   FROM Cartera c "
                + "  WHERE c.idmatricula.idestudiante.idestudiante=1"
                + "  ORDER BY c.idperiodo.nombre,c.idmatricula.idvinculacion.idcarrera.nombre DESC ";

        /*Cartera c = new HqlLightUtil(Cartera.class).parseObjectLight(hql, vals);

         System.out.println("ID:" + c.getIdcartera());
         System.out.println("PERIODO:" + c.getIdperiodo().getNombre());
         System.out.println("CARRERA;" + c.getIdmatricula().getIdvinculacion().getIdcarrera().getNombre());
         System.out.println("SALDO:" + c.getSaldocartera());*/
    }
}
