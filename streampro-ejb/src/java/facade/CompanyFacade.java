package facade;

import entity.Company;
import general.AbstractFacade;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import util.StringUtil;

@Stateless
public class CompanyFacade extends AbstractFacade<Company> {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public CompanyFacade() {
        super(Company.class);
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
                + " FROM Company obj "
                + orderBy;
        return hql;
    }

    public int countAll() {
        String hql = this.queryAll(true, "", "");
        return this.numFromHQL(hql, new Long(0)).intValue();
    }

    public List<Company> listAll(String attr_order, String ascDesc) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, true, 0, 0);
    }

    public List<Company> listAll(String attr_order, String ascDesc, int firstReg, int maxReg) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, false, firstReg, maxReg);
    }
    
    public int searchFullTextCount(String valBusq) {
        String sql = "SELECT count(*)"
                + " FROM company"
                + " WHERE to_tsvector(idcompany||' '||comp_name)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return numFromSQL(sql, new BigInteger("0")).intValue();
    }

    public List<Company> searchFullTextList(String valBusq, boolean allReg, int firstReg, int maxReg) {
        String sql = "SELECT *"
                + " FROM company"
                + " WHERE to_tsvector(idcompany||' '||comp_name)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return this.findNative(sql, allReg, firstReg, maxReg, Company.class);
    }

}
