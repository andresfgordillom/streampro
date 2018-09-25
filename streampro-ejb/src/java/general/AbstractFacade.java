package general;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.hibernate.Transaction;
import org.hibernate.impl.SessionImpl;
import util.HqlLightUtil;
import util.SerialClone;

public abstract class AbstractFacade<T> {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private Transaction tx;
    private SessionImpl sess;
    private EntityManager em_;
    private Class<T> entityClass;

    protected abstract EntityManager getEntityManager();
    
    protected EntityManager getEntityManager2() {
        return emf.createEntityManager();
    }

    protected SessionImpl getSess() {
        return sess;
    }

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected void beginTransaction() {
        //Obteniendo sesion
        em_ = getEntityManager2();
        sess = (SessionImpl) em_.getDelegate();
        tx = sess.getTransaction();

        //Empezando Transaccion
        tx.begin();
    }

    protected void commitTransaction() {
        tx.commit();
    }

    protected void rollbackTransaction() {
        try {
            tx.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void rollbackTransaction(StringBuilder err) {
        if (!err.toString().isEmpty()) {
            try {
                tx.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void endTransaction() {
        //Cerrando conexion
        if (em_ != null) {
            em_.close();
        }
    }

    public T create(T entity) throws Exception {

        //Obteniendo sesion
        beginTransaction();

        //Realizando Operacion
        try {
            sess.save(entity);
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();

            rollbackTransaction();
            System.out.println("FALLA, creando registro !" + e);

            throw e;
        }

        //Cerrando conexion
        endTransaction();
        return entity;
    }

    public T edit(T entity) throws Exception {

        //Obteniendo sesion
        beginTransaction();

        //Realizando Operacion
        try {
            sess.update(entity);

            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();

            rollbackTransaction();
            System.out.println("FALLA, editando registro !" + e);

            throw e;
        }

        //Cerrando conexion
        endTransaction();
        return entity;

    }

    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    public T find(Object id) {
        Object obj = getEntityManager().find(entityClass, id);
        //Evita problema two-session
        Object copy = SerialClone.clone(obj);
        return (T) copy;
    }

    public List<T> find(String hql, boolean all, int firstReg, int maxReg) {
        EntityManager em = getEntityManager();
        List l = new ArrayList();
        try {
            Query q = em.createQuery(hql);
            if (!all) {
                q.setMaxResults(maxReg);
                q.setFirstResult(firstReg);
            }
            l = q.getResultList();
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando HQL: " + hql);
            e.printStackTrace();
        }
        return l;
    }

    protected long countNative(String sql) {
        EntityManager em = getEntityManager();

        long cuenta = 0;
        try {
            Query q = em.createNativeQuery(sql);

            Object result = q.getSingleResult();

            if (result != null) {
                cuenta = Long.parseLong(result.toString());
            }
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando SQL: " + sql);
            e.printStackTrace();
        }
        return cuenta;
    }

    protected long countGeneric(String hql) {
        EntityManager em = getEntityManager();

        long cuenta = 0;
        try {
            Query q = em.createQuery(hql);

            Object result = q.getSingleResult();

            if (result != null) {
                cuenta = Long.parseLong(result.toString());
            }
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando HQL: " + hql);
            e.printStackTrace();
        }
        return cuenta;
    }

    protected List findGeneric(String hql, boolean all, int firstReg, int maxReg) {
        EntityManager em = getEntityManager();
        List l = new ArrayList();
        try {
            Query q = em.createQuery(hql);
            if (!all) {
                q.setMaxResults(maxReg);
                q.setFirstResult(firstReg);
            }
            l = q.getResultList();
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando HQL: " + hql);
            e.printStackTrace();
        }
        return l;
    }

    protected List<T> find(String sql, Class classResult, boolean all, int firstReg, int maxReg) {
        EntityManager em = getEntityManager();
        List l = new ArrayList();
        try {
            Query q = em.createNativeQuery(sql, classResult);
            if (!all) {
                q.setMaxResults(maxReg);
                q.setFirstResult(firstReg);
            }
            l = q.getResultList();
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando SQL: " + sql);
            e.printStackTrace();
        }
        return l;
    }

    protected List<T> findNative(String sql, boolean all, int firstReg, int maxReg) {
        EntityManager em = getEntityManager();
        List l2 = new ArrayList();
        try {
            Query q = em.createNativeQuery(sql);
            if (!all) {
                q.setMaxResults(maxReg);
                q.setFirstResult(firstReg);
            }
            l2 = q.getResultList();
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando SQL: " + sql);
            e.printStackTrace();
        }
        return l2;
    }

    protected List findNative(String sql, boolean all, int firstReg, int maxReg, Class cla) {
        EntityManager em = getEntityManager();
        List l2 = new ArrayList();
        try {
            Query q = em.createNativeQuery(sql, cla);
            if (!all) {
                q.setMaxResults(maxReg);
                q.setFirstResult(firstReg);
            }
            l2 = q.getResultList();
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando SQL: " + sql);
            e.printStackTrace();
        }
        return l2;
    }

    protected List findNativeGeneric(String sql, boolean all, int firstReg, int maxReg) {
        EntityManager em = getEntityManager();
        List l2 = new ArrayList();
        try {
            Query q = em.createNativeQuery(sql);
            if (!all) {
                q.setMaxResults(maxReg);
                q.setFirstResult(firstReg);
            }
            l2 = q.getResultList();
        } catch (Exception e) {
            System.out.println("Supertree3 dice FALLA, ejecutando SQL: " + sql);
            e.printStackTrace();
        }
        return l2;
    }

    protected int deleteUpdateNative(String sql) {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery(sql);
        return q.executeUpdate();
    }

    //DELEGATE
    public List<T> findAll() {
        String hql = "SELECT obj FROM " + entityClass.getCanonicalName() + " obj ";
        return find(hql, true, 0, 0);
    }

    public List<T> findAllOrderAsc(String campo) {
        String hql = "SELECT obj FROM " + entityClass.getCanonicalName() + " obj ORDER BY obj." + campo + " ASC ";
        return find(hql, true, 0, 0);
    }

    public List<T> findAllOrderDesc(String campo) {
        String hql = "SELECT obj FROM " + entityClass.getCanonicalName() + " obj ORDER BY obj." + campo + " DESC ";
        return find(hql, true, 0, 0);
    }

    public List<T> findBy(String campo, Object val) {
        String hql = "SELECT obj FROM " + entityClass.getCanonicalName() + " obj WHERE obj." + campo + " = '" + val + "' ORDER BY obj." + campo + " ASC";
        return find(hql, true, 0, 0);
    }

    public T findObjectBy(String campo, Object val) {
        String hql = "SELECT obj FROM " + entityClass.getCanonicalName() + " obj WHERE obj." + campo + " = '" + val + "' ORDER BY obj." + campo + " ASC";
        List l = find(hql, false, 0, 1);
        if (l.size() > 0) {
            return (T) l.get(0);
        } else {
            return null;
        }
    }

    protected <T> T numFromHQL(String hql, T valInit) {
        List l = findGeneric(hql, true, 0, 0);
        if (l.size() > 0 && l.get(0) != null) {
            return (T) l.get(0);
        } else {
            return valInit;
        }
    }

    protected <T> T numFromSQL(String sql, T valInit) {
        List l = findNative(sql, true, 0, 0);
        if (l.size() > 0 && l.get(0) != null) {
            return (T) l.get(0);
        } else {
            return valInit;
        }
    }

    protected T objectFromHQL(String hql) {
        List l = findGeneric(hql, true, 0, 0);
        if (l.size() > 0 && l.get(0) != null) {
            return (T) l.get(0);
        } else {
            return null;
        }
    }

    protected T objectFromSQL(String sql) {
        List l = findNative(sql, true, 0, 0, entityClass);
        if (l.size() > 0 && l.get(0) != null) {
            return (T) l.get(0);
        } else {
            return null;
        }
    }

    protected List<T> parseListLightFromHQL(String hql) {
        List<Object[]> lstObjs = findGeneric(hql, true, 0, 0);
        return new HqlLightUtil(entityClass).parseObjectLightList(hql, lstObjs);
    }

    protected List<T> parseListLightFromHQL(String hql, int firstReg, int maxReg) {
        List<Object[]> lstObjs = findGeneric(hql, false, firstReg, maxReg);
        return new HqlLightUtil(entityClass).parseObjectLightList(hql, lstObjs);
    }

    protected List parseListLightFromHQL(String hql, Class classLoader) {
        List<Object[]> lstObjs = findGeneric(hql, true, 0, 0);
        return new HqlLightUtil(classLoader).parseObjectLightList(hql, lstObjs);
    }

    protected List parseListLightFromHQL(String hql, int firstReg, int maxReg, Class classLoader) {
        List<Object[]> lstObjs = findGeneric(hql, false, firstReg, maxReg);
        return new HqlLightUtil(classLoader).parseObjectLightList(hql, lstObjs);
    }

    protected Object parseObjectLightFromHQL(String hql, Object[] objs, Class classLoader) {
        return new HqlLightUtil(classLoader).parseObjectLight(hql, objs);
    }
}
