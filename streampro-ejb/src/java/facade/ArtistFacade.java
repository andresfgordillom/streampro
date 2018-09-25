package facade;

import entity.Albumhasartist;
import entity.Artist;
import general.AbstractFacade;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import util.StringUtil;

@Stateless
public class ArtistFacade extends AbstractFacade<Artist> {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public ArtistFacade() {
        super(Artist.class);
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
                + " FROM Artist obj "
                + orderBy;
        return hql;
    }

    public int countAll() {
        String hql = this.queryAll(true, "", "");
        return this.numFromHQL(hql, new Long(0)).intValue();
    }

    public List<Artist> listAll(String attr_order, String ascDesc) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, true, 0, 0);
    }

    public List<Artist> listAll(String attr_order, String ascDesc, int firstReg, int maxReg) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, false, firstReg, maxReg);
    }

    public int searchFullTextCount(String valBusq) {
        String sql = "SELECT count(*)"
                + " FROM artist"
                + " WHERE to_tsvector(idartist||' '||stage_name)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return numFromSQL(sql, new BigInteger("0")).intValue();
    }

    public List<Artist> searchFullTextList(String valBusq, boolean allReg, int firstReg, int maxReg) {
        String sql = "SELECT *"
                + " FROM artist"
                + " WHERE to_tsvector(idartist||' '||stage_name)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return this.findNative(sql, allReg, firstReg, maxReg, Artist.class);
    }

    public List<Artist> getMainArtistFromAlbum(Integer idalbum) {
        String hql = "SELECT a.idartist"
                + " FROM Albumhasartist a"
                + " WHERE a.idalbum.idalbum = " + idalbum;
        return this.find(hql, true, 0, 0);
    }

    public HashMap<Integer, List<Artist>> getMapAllMainArtists() {
        HashMap<Integer, List<Artist>> resultMap = new HashMap<>();

        String sql = "SELECT *"
                + " FROM albumhasartist";
        List<Albumhasartist> AhAs = findNative(sql, true, 0, 0, Albumhasartist.class);

        for (Albumhasartist aha : AhAs) {
            Integer idalb = aha.getIdalbum().getIdalbum();
            if (!resultMap.containsKey(idalb)) {
                resultMap.put(idalb, addArtistsToMapByIdAlbum(idalb, AhAs));
            }
        }

        return resultMap;
    }

    private List<Artist> addArtistsToMapByIdAlbum(int idalbum, List<Albumhasartist> AhAs) {
        List<Artist> artists = new ArrayList<>();

        for (Albumhasartist aha : AhAs) {
            int idalb = aha.getIdalbum().getIdalbum();
            if (idalb == idalbum) {
                artists.add(aha.getIdartist());
            }
        }

        return artists;
    }
}
