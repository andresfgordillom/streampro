package facade;

import entity.Deezifyuser;
import general.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Stateless
public class UserFacade extends AbstractFacade<Deezifyuser> {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public UserFacade() {
        super(Deezifyuser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private String queryAll(boolean isCount, String attr_order, String ascDesc) {
        String select = " SELECT obj ";
        String orderBy = " ORDER BY obj." + attr_order + " " + ascDesc;

        if (isCount) {
            select = " SELECT COUNT(obj) ";
            orderBy = "";
        }

        String hql = select
                + " FROM Deezifyuser obj "
                + orderBy;
        return hql;
    }

    public int countAll() {
        String hql = this.queryAll(true, "", "");
        return this.numFromHQL(hql, new Long(0)).intValue();
    }

    public List<Deezifyuser> listAll(String attr_order, String ascDesc) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, true, 0, 0);
    }

    public List<Deezifyuser> listAll(String attr_order, String ascDesc, int firstReg, int maxReg) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, false, firstReg, maxReg);
    }

}
