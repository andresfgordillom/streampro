package general;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author root
 */
public class EntityControl {

    //Generales
    protected String msg; //Mensaje de SUCESFULL o warning
    protected String msgErr;//Mensaje de WARNING
    public ValuesFromSesion valuesFromSesion = new ValuesFromSesion(FacesContext.getCurrentInstance());
    public FacesUtil facesUtil = new FacesUtil(FacesContext.getCurrentInstance());
    private String entityName; //Nombre de la clase pojo entidad
    private String entityID; //Nombre del atributo ID de la clase pojo entidad
    //Atributos de Lista
    protected List lst; //Lista que se comparte en todas las busquedas
    protected String orderBy; //Nombre del campo por el que se ordenara lista
    protected String ascDesc; //Orden 'ASC: ascendente' o 'DESC: descendente'
    protected int firstRegList = 0; //Primer registro actual de la lista
    protected int maxRegList = 100; //Maximo numero de registros por lista
    protected boolean allResult = true; //TRUE, mostrar todos los resultados de la lista;  FALSE, paginar
    protected int totalCount = 0; //Total de registros encontrados en la consulta, funciona solo si antes se ha consultado
    //Atributos para Consultar
    protected String queryVal;//Valor de busqueda
    protected boolean successful = false;

    //GETTERS AN SETTERS -----------------------------------------------------------------
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List getLst() {
        return lst;
    }

    public void setLst(List lst) {
        this.lst = lst;
    }

    public String getAscDesc() {
        return ascDesc;
    }

    public void setAscDesc(String ascDesc) {
        this.ascDesc = ascDesc;
    }

    public int getFirstRegList() {
        return firstRegList;
    }

    public void setFirstRegList(int firstRegList) {
        this.firstRegList = firstRegList;
    }

    public int getMaxRegList() {
        return maxRegList;
    }

    public void setMaxRegList(int maxRegList) {
        this.maxRegList = maxRegList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgErr() {
        return msgErr;
    }

    public void setMsgErr(String msgErr) {
        this.msgErr = msgErr;
    }

    public String getQueryVal() {
        return queryVal;
    }

    public void setQueryVal(String queryVal) {
        this.queryVal = queryVal;
    }

    public boolean isAllResult() {
        return allResult;
    }

    public void setAllResult(boolean allResult) {
        this.allResult = allResult;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    //Metodos de Paginacion
    public int getPagActual() {
        return (int) (Math.round((this.firstRegList + 1) / this.maxRegList)) + 1;
    }

    public int getTotalPag() {
        return (int) (Math.round(this.totalCount / this.maxRegList)) + 1;
    }

    //Metodos para lista DE CONSULTAS----------------------------------------------------------
    public void setAscDesc_() {
        if (ascDesc.compareTo("ASC") == 0) {
            ascDesc = "DESC";
        } else {
            ascDesc = "ASC";
        }
    }

    /*public String orderByCampo() {
     Order = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cOrder_");
     setAscDesc_();
     return null;
     }*/
    public String first() {
        this.firstRegList = 0;
        return null;
    }

    public String next() {
        this.firstRegList += this.maxRegList;
        if (firstRegList >= this.totalCount) {
            this.firstRegList = 0;
        }
        return null;
    }

    public String back() {
        this.firstRegList -= this.maxRegList;
        if (firstRegList <= -maxRegList) {
            this.firstRegList = (int) (this.totalCount / maxRegList) * maxRegList;
        }
        return null;
    }

    public String last() {
        this.firstRegList = (int) (this.totalCount / maxRegList) * maxRegList;
        return null;
    }

    public int getTotalLista() {
        int n = 0;
        try {
            n = lst.size();
        } catch (Exception e) {
        }
        return n;
    }

    //Implementaciones de metodos genericos
    public List allObj(AbstractFacade facade) {
        //this.lst=facade.findAll();
        this.totalCount = lst.size();
        return lst;
    }

    public Object recuperaById_Integer(AbstractFacade facade) {
        String id_ = "0";
        try {
            id_ = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(this.getEntityID() + "_");
            return facade.find(Integer.parseInt(id_));
        } catch (Exception e) {
            msgErr = "FALLA, recibiendo parametro '" + this.getEntityID() + "_'";
            System.out.println(msgErr);
            e.printStackTrace();
        }
        return null;
    }

    public Object recuperaById_Integer(AbstractFacade facade, String nomParam) {
        String id_ = "0";
        try {
            id_ = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(nomParam);
            return facade.find(Integer.parseInt(id_));
        } catch (Exception e) {
            msgErr = "FALLA, recibiendo parametro '" + nomParam + "'";
            System.out.println(msgErr);
            e.printStackTrace();
        }
        return null;
    }

    public Object recuperaById_String(AbstractFacade facade) {
        String id_ = "0";
        try {
            id_ = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(this.getEntityID() + "_");
            return facade.find(id_);
        } catch (Exception e) {
            msgErr = "FALLA, recibiendo parametro '" + this.getEntityID() + "_', y buscando registro";
            System.out.println(msgErr);
            e.printStackTrace();
        }
        return null;
    }

    public Object recuperaById_String(AbstractFacade facade, String nomParam) {
        String id_ = "0";
        try {
            id_ = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(nomParam);
            return facade.find(id_);
        } catch (Exception e) {
            msgErr = "FALLA, recibiendo parametro '" + nomParam + "', y buscando registro";
            System.out.println(msgErr);
            e.printStackTrace();
        }
        return null;
    }

    public int recuperaNumAllObj(AbstractFacade facade) {
        String hql = "SELECT count(obj) FROM " + this.getEntityName() + " obj";
        List l = facade.find(hql, true, 0, 0);
        int n = 0;
        try {
            n = Integer.parseInt(l.get(0).toString());
        } catch (Exception e) {
            msgErr = "FALLA, calculando total de registros->" + e;
        }
        this.totalCount = n;
        return n;
    }

    public void recuperaAllObj(AbstractFacade facade) {
        String hql = "SELECT obj FROM " + this.getEntityName() + " obj ORDER BY " + this.getOrderBy() + " " + this.getAscDesc();
        lst = facade.find(hql, false, this.getFirstRegList(), this.getMaxRegList());
    }

    private int recuperaNumAllObj(String hql, AbstractFacade facade) {
        List l = facade.find(hql, true, 0, 0);
        int n = 0;
        try {
            n = Integer.parseInt(l.get(0).toString());
        } catch (Exception e) {
            msgErr = "FALLA, calculando total de registros->" + e;
        }
        this.totalCount = n;
        return n;
    }

    private void recuperaAllObj(String hql, AbstractFacade facade) {
        lst = facade.find(hql, false, this.getFirstRegList(), this.getMaxRegList());
    }

    private void recuperaAllObj(String hql, AbstractFacade facade, boolean allResult) {
        lst = facade.find(hql, allResult, this.getFirstRegList(), this.getMaxRegList());
    }

    //Calcular total busqueda
    private int totalBuscarNumObj(AbstractFacade facade, String sql, boolean buscarPorId, Object id) {
        //Si es nu valor muy corto
        if (queryVal == null || queryVal.trim().compareTo("") == 0 || queryVal.trim().length() < 0) {
            return 0;
        }

        return 0;
    }

    private void recuperaBuscar(AbstractFacade facade, String sql, boolean buscarPorId, Object id, Class classEntity) {
        //Si es nu valor muy corto
        if (queryVal == null || queryVal.trim().compareTo("") == 0 || queryVal.trim().length() < 0) {
            return;
        }

        lst = new ArrayList();
    }

    public String orderByCampo() {
        String ord = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cOrder_");
        if (ord == null || ord.trim().equals("") || ord.trim().toUpperCase().equals("NULL")) {
            orderBy = this.getOrderBy();
        } else {
            orderBy = ord;
        }
        setAscDesc_();
        return orderBy;
    }

    public String ascDesc() {
        String ad = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ascDesc_");
        if (ad == null || ad.trim().equals("") || ad.trim().toUpperCase().equals("NULL")) {
            ascDesc = this.getAscDesc();
        } else {
            ascDesc = ad;
        }
        setAscDesc_();
        return ascDesc;
    }
}
