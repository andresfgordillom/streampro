package facade;

import entity.Artist;
import entity.Song;
import entity.Songhasartist;
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
public class SongFacade extends AbstractFacade<Song> {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public SongFacade() {
        super(Song.class);
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
                + " FROM Song obj "
                + orderBy;
        return hql;
    }

    public int countAll() {
        String hql = this.queryAll(true, "", "");
        return this.numFromHQL(hql, new Long(0)).intValue();
    }

    public List<Song> listAll(String attr_order, String ascDesc) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, true, 0, 0);
    }

    public List<Song> listAll(String attr_order, String ascDesc, int firstReg, int maxReg) {
        String hql = this.queryAll(false, attr_order, ascDesc);
        return this.find(hql, false, firstReg, maxReg);
    }

    public int searchFullTextCount(String valBusq) {
        String sql = "SELECT count(*)"
                + " FROM song"
                + " WHERE to_tsvector(idsong||' '||title)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return numFromSQL(sql, new BigInteger("0")).intValue();
    }

    public List<Song> searchFullTextList(String valBusq, boolean allReg, int firstReg, int maxReg) {
        String sql = "SELECT *"
                + " FROM song"
                + " WHERE to_tsvector(idsong||' '||title)@@to_tsquery('" + new StringUtil().reemplazaEspacios(valBusq, "&").trim() + "')";
        return this.findNative(sql, allReg, firstReg, maxReg, Song.class);
    }

    public List<Song> listAllByAlbum(Integer idalbum) {
        String hql = " SELECT obj "
                + " FROM Song obj "
                + " WHERE obj.idalbum.idalbum = " + idalbum
                + " ORDER BY obj.discnumber, obj.songnumber ASC";
        return this.find(hql, true, 0, 0);
    }

    public List<Song> listAllByList(Integer idplaylist) {
        String hql = " SELECT phs.idsong"
                + " FROM Playlisthassong phs"
                + " WHERE phs.idplaylist.idplaylist = " + idplaylist
                + " ORDER BY phs.idsong.title ASC";
        return this.find(hql, true, 0, 0);
    }

    public Song create(Song song, List<Artist> artists, StringBuilder msg) throws Exception {
        //Obteniendo sesion
        beginTransaction();

        //Realizando Operacion
        try {
            SessionImpl sess = getSess();
            sess.save(song);

            for (Artist art : artists) {
                Songhasartist sha = new Songhasartist();
                sha.setIdsongartist(song.getIdsong() + "_" + art.getIdartist());
                sha.setIdsong(song);
                sha.setIdartist(art);
                sess.save(sha);
            }

            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
            System.out.println("FALLA, creando registro !" + e);
        }

        //Cerrando conexion
        endTransaction();
        return song;
    }

}
