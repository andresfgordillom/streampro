package facade;

import entity.Album;
import entity.Albumhasartist;
import entity.Artist;
import general.AbstractFacade;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.hibernate.impl.SessionImpl;
import util.StringUtil;

@Stateless
public class AlbumFacade extends AbstractFacade<Album> {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public AlbumFacade() {
        super(Album.class);
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
                + " FROM Album obj "
                + orderBy;
        return hql;
    }

    public int countAll() {
        String hql = this.queryAll(true, "", "");
        return this.numFromHQL(hql, new Long(0)).intValue();
    }

    public List<Album> listAll(String attr_order, String ascDesc) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, true, 0, 0);
    }

    public List<Album> listAll(String attr_order, String ascDesc, int firstReg, int maxReg) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, false, firstReg, maxReg);
    }

    public int searchFullTextCount(String valBusq) {
        String sql = "SELECT count(*)"
                + " FROM album"
                + " WHERE to_tsvector(idalbum||' '||title)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return numFromSQL(sql, new BigInteger("0")).intValue();
    }

    public List<Album> searchFullTextList(String valBusq, boolean allReg, int firstReg, int maxReg) {
        String sql = "SELECT *"
                + " FROM album"
                + " WHERE to_tsvector(idalbum||' '||title)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return this.findNative(sql, allReg, firstReg, maxReg, Album.class);
    }

    public Album create(Album alb, List<Artist> artists, StringBuilder msg) throws Exception {
        //Obteniendo sesion
        beginTransaction();

        //Realizando Operacion
        try {
            SessionImpl sess = getSess();
            sess.save(alb);

            for (Artist art : artists) {
                Albumhasartist aha = new Albumhasartist();
                aha.setIdalbumartist(alb.getIdalbum() + "_" + art.getIdartist());
                aha.setIdalbum(alb);
                aha.setIdartist(art);
                sess.save(aha);
            }

            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
            System.out.println("FALLA, creando registro !" + e);
        }

        //Cerrando conexion
        endTransaction();
        return alb;
    }

    public Album edit(Album alb, List<Artist> artists, StringBuilder msg) throws Exception {
        //Obteniendo sesion
        beginTransaction();

        //Realizando Operacion
        try {
            SessionImpl sess = getSess();
            sess.update(alb);

            sess.createSQLQuery("DELETE FROM albumhasartist WHERE idalbum = " + alb.getIdalbum()).executeUpdate();

            for (Artist art : artists) {
                Albumhasartist aha = new Albumhasartist();
                aha.setIdalbumartist(alb.getIdalbum() + "_" + art.getIdartist());
                aha.setIdalbum(alb);
                aha.setIdartist(art);
                sess.save(aha);
            }

            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
            System.out.println("FALLA, creando registro !" + e);
        }

        //Cerrando conexion
        endTransaction();
        return alb;
    }
}
