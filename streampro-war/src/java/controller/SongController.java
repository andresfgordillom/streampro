/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Album;
import entity.Artist;
import entity.Song;
import facade.AlbumFacade;
import facade.SongFacade;
import general.EntityControl;
import interfaces.EntityControlInterface;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
@ManagedBean
@RequestScoped
public class SongController extends EntityControl implements EntityControlInterface {

    @EJB
    private SongFacade facade;
    @EJB
    private AlbumFacade albumfacade;
    Song obj = new Song();
    List<Artist> artists = new ArrayList<>();

    public SongController() {
        this.setEntityName("Song");
        this.setAllResult(true);
        this.setFirstRegList(0);
        this.setMaxRegList(25);
        this.setOrderBy("title");
        this.setAscDesc("ASC");
        this.setEntityID("idsong");
    }

    @Override
    public Song getObj() {
        return obj;
    }

    @Override
    public void setObj(Object obj) {
        this.obj = (Song) obj;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public String getAllObjects() {
        Integer pag = facesUtil.getFacesParamInteger("pag_");
        if (pag == null) {
            pag = 0;
        }
        this.lst = facade.listAll(this.orderByCampo(), this.ascDesc(), pag * this.getMaxRegList(), this.getMaxRegList());
        return "";
    }

    @Override
    public int getCountAllObjects() {
        this.totalCount = facade.countAll();
        return this.totalCount;
    }

    @Override
    public String getObjectById() {
        this.obj = facade.find(facesUtil.getFacesParamInteger("idsong_"));
        return "";
    }

    public String getAlbumById() {
        Album alb = albumfacade.find(facesUtil.getFacesParamInteger("idalbum_"));
        getObj().setIdalbum(alb);
        return "";
    }

    public String recuperaById(Integer idsong) {
        this.obj = facade.find(idsong);
        return "";
    }

    @Override
    public String edit() {

        try {
            obj = facade.edit(obj);
            msg = "Se actualizó el registro con éxito ";
            setSuccessful(true);
        } catch (Exception e) {
            msgErr = "FALLA, actualizando registro ! =>" + e.toString();
        }

        return null;
    }

    @Override
    public String create() {
        try {
            this.getAlbumById();

            if (artists.isEmpty()) {
                msgErr = "¡Debe seleccionar al menos un artista!";
                return null;
            }

            StringBuilder sb = new StringBuilder();
            facade.create(obj, artists, sb);

            if (sb.toString().isEmpty()) {
                msg = "¡Registro realizado correctamente!";
                setSuccessful(true);
            } else {
                msgErr = "¡FALLA, creando Canción !" + sb.toString();
            }
        } catch (Exception e) {
            msgErr = "FALLA, creando Canción !" + e;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCountQueryObj() {
        setTotalCount(facade.searchFullTextCount(queryVal));
        return getTotalCount();
    }

    @Override
    public String getQueryObjects() {
        Integer pag = facesUtil.getFacesParamInteger("pag_");
        if (pag == null) {
            pag = 0;
        }
        String busqueda = facesUtil.getFacesParamValue("busq_");
        //Buscando con Postgres
        setLst(facade.searchFullTextList(busqueda, false, this.getMaxRegList() * pag, this.getMaxRegList()));
        return null;
    }

    @Override
    public SelectItem[] getAllSelect() {
        List<Song> l = facade.findAllOrderDesc("title");
        return getSelectItem(l).toArray(new SelectItem[0]);
    }

    private List<SelectItem> getSelectItem(List<Song> list) {
        SelectItem sel = new SelectItem(null, "----------");

        List lstS = new ArrayList();
        lstS.add(sel);

        for (Song l : list) {
            sel = new SelectItem(l, l.getTitle());
            lstS.add(sel);
        }
        return lstS;
    }

    @Override
    public List autoComplete(String query) {
        return facade.searchFullTextList(query, false, 0, this.maxRegList);
    }

}
