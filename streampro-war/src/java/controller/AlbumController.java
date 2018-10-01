/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Album;
import entity.Artist;
import entity.Song;
import facade.AlbumFacade;
import facade.ArtistFacade;
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
public class AlbumController extends EntityControl implements EntityControlInterface {

    @EJB
    private AlbumFacade facade;
    @EJB
    private ArtistFacade artistFacade;
    @EJB
    private SongFacade songFacade;
    Album obj = new Album();
    List<Artist> mainArtists = new ArrayList<>();
    List<Song> songs = new ArrayList<>();
    HashMap<Integer, List<Artist>> allMainArtists = new HashMap<>();
    HashMap<Integer, List<Artist>> allArtistsFromSongs = new HashMap<>();

    public AlbumController() {
        this.setEntityName("Album");
        this.setAllResult(true);
        this.setFirstRegList(0);
        this.setMaxRegList(25);
        this.setOrderBy("date");
        this.setAscDesc("DESC");
        this.setEntityID("idalbum");
    }

    @Override
    public Album getObj() {
        return obj;
    }

    @Override
    public void setObj(Object obj) {
        this.obj = (Album) obj;
    }

    public List<Artist> getMainArtists() {
        return mainArtists;
    }

    public void setMainArtists(List<Artist> mainArtists) {
        this.mainArtists = mainArtists;
    }

    public HashMap<Integer, List<Artist>> getAllMainArtists() {
        return allMainArtists;
    }

    public void setAllMainArtists(HashMap<Integer, List<Artist>> allMainArtists) {
        this.allMainArtists = allMainArtists;
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
        setAllMainArtists(artistFacade.getMapAllMainArtists());
        return "";
    }

    @Override
    public int getCountAllObjects() {
        this.totalCount = facade.countAll();
        return this.totalCount;
    }

    @Override
    public String getObjectById() {
        Integer idalbum = facesUtil.getFacesParamInteger("idalbum_");
        this.obj = facade.find(idalbum);
        setMainArtists(artistFacade.getMainArtistFromAlbum(idalbum));
        return "";
    }

    public String recuperaById(Integer idalbum) {
        this.obj = facade.find(idalbum);
        return "";
    }

    @Override
    public String edit() {
        try {
            if (mainArtists.isEmpty()) {
                msgErr = "¡Debe seleccionar al menos un artista!";
                return null;
            }

            StringBuilder sb = new StringBuilder();
            facade.edit(obj, mainArtists, sb);

            if (sb.toString().isEmpty()) {
                msg = "¡Registro realizado correctamente!";
                setSuccessful(true);
            } else {
                msgErr = "¡FALLA, creando Álbum !" + sb.toString();
            }
        } catch (Exception e) {
            msgErr = "FALLA, actualizando registro ! =>" + e.toString();
        }

        return null;
    }

    @Override
    public String create() {
        try {
            if (mainArtists.isEmpty()) {
                msgErr = "¡Debe seleccionar al menos un artista!";
                return null;
            }

            StringBuilder sb = new StringBuilder();
            facade.create(obj, mainArtists, sb);

            if (sb.toString().isEmpty()) {
                msg = "¡Registro realizado correctamente!";
                setSuccessful(true);
            } else {
                msgErr = "¡FALLA, creando Álbum !" + sb.toString();
            }
        } catch (Exception e) {
            msgErr = "FALLA, creando Álbum !" + e;
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
        List<Album> l = facade.findAllOrderDesc("date");
        return getSelectItem(l).toArray(new SelectItem[0]);
    }

    private List<SelectItem> getSelectItem(List<Album> list) {
        SelectItem sel = new SelectItem(null, "----------");

        List lstA = new ArrayList();
        lstA.add(sel);

        for (Album a : list) {
            sel = new SelectItem(a, a.getTitle());
            lstA.add(sel);
        }
        return lstA;
    }

    @Override
    public List autoComplete(String query) {
        return facade.searchFullTextList(query, false, 0, this.maxRegList);
    }

    public List<Artist> mainArtistsByIdAlbum(Integer idalbum) {
        if (idalbum != null) {
            return getAllMainArtists().get(idalbum);
        }

        return new ArrayList<>();
    }

    public String getSongsByAlbum() {
        setSongs(songFacade.listAllByAlbum(getObj().getIdalbum()));
        setAllArtistsFromSongs(artistFacade.getMapAllArtistsFromSongs(getObj().getIdalbum()));
        return "";
    }

    public List<Artist> artistsFromSong(Integer idsong) {
        if (idsong != null) {
            return getAllArtistsFromSongs().get(idsong);
        }

        return new ArrayList<>();
    }

}
