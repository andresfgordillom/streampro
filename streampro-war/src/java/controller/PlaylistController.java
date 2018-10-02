/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Artist;
import entity.Playlist;
import entity.Playlisthassong;
import entity.Song;
import facade.ArtistFacade;
import facade.PlaylistFacade;
import facade.SongFacade;
import general.EntityControl;
import interfaces.EntityControlInterface;
import java.util.ArrayList;
import java.util.HashMap;
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
public class PlaylistController extends EntityControl implements EntityControlInterface {

    @EJB
    private PlaylistFacade facade;
    @EJB
    private SongFacade songFacade;
    @EJB
    private ArtistFacade artistFacade;
    Playlist obj = new Playlist();
    Playlisthassong phs = new Playlisthassong();
    List<Song> songs = new ArrayList<>();
    HashMap<Integer, List<Artist>> allArtistsFromSongs = new HashMap<>();

    public PlaylistController() {
        this.setEntityName("Playlist");
        this.setAllResult(true);
        this.setFirstRegList(0);
        this.setMaxRegList(25);
        this.setOrderBy("listTitle");
        this.setAscDesc("ASC");
        this.setEntityID("idplaylist");
    }

    @Override
    public Playlist getObj() {
        return obj;
    }

    @Override
    public void setObj(Object obj) {
        this.obj = (Playlist) obj;
    }

    public Playlisthassong getPhs() {
        return phs;
    }

    public void setPhs(Playlisthassong phs) {
        this.phs = phs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public HashMap<Integer, List<Artist>> getAllArtistsFromSongs() {
        return allArtistsFromSongs;
    }

    public void setAllArtistsFromSongs(HashMap<Integer, List<Artist>> allArtistsFromSongs) {
        this.allArtistsFromSongs = allArtistsFromSongs;
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
        this.obj = facade.find(facesUtil.getFacesParamInteger("idplaylist_"));
        return "";
    }

    public String recuperaById(Integer idplaylist) {
        this.obj = facade.find(idplaylist);
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
            facade.create(obj);
            msg = "¡Registro realizado correctamente!";
            setSuccessful(true);
        } catch (Exception e) {
            msgErr = "FALLA, creando Lista de Reproducción !" + e;
            e.printStackTrace();
        }

        return null;
    }

    public String addSonToPlaylist() {
        try {
            this.getObjectById();

            if (getPhs().getIdsong() == null) {
                msgErr = "¡Debe seleccionar una canción!";
                return null;
            }

            StringBuilder sb = new StringBuilder();
            facade.addSonToList(obj, getPhs(), sb);

            if (sb.toString().isEmpty()) {
                msg = "¡Registro realizado correctamente!";
                setSuccessful(true);
            } else {
                msgErr = "¡FALLA, agregando Canción a Lista!" + sb.toString();
            }
        } catch (Exception e) {
            msgErr = "FALLA, creando Lista de Reproducción !" + e;
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
        List<Playlist> l = facade.findAllOrderDesc("listTitle");
        return getSelectItem(l).toArray(new SelectItem[0]);
    }

    private List<SelectItem> getSelectItem(List<Playlist> list) {
        SelectItem sel = new SelectItem(null, "----------");

        List lstL = new ArrayList();
        lstL.add(sel);

        for (Playlist l : list) {
            sel = new SelectItem(l, l.getListTitle());
            lstL.add(sel);
        }
        return lstL;
    }

    @Override
    public List autoComplete(String query) {
        return facade.searchFullTextList(query, false, 0, this.maxRegList);
    }

    public String getSongsByList() {
        getObjectById();
        setSongs(songFacade.listAllByList(getObj().getIdplaylist()));
        setAllArtistsFromSongs(artistFacade.getMapAllArtistsFromSongs(null, getObj().getIdplaylist()));
        return "";
    }

    public List<Artist> artistsFromSong(Integer idsong) {
        if (idsong != null) {
            return getAllArtistsFromSongs().get(idsong);
        }

        return new ArrayList<>();
    }

}
