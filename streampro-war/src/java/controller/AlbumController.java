/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Album;
import facade.AlbumFacade;
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
public class AlbumController extends EntityControl implements EntityControlInterface {

    @EJB
    private AlbumFacade facade;
    Album obj = new Album();

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
        this.obj = facade.find(facesUtil.getFacesParamInteger("idalbum_"));
        return "";
    }

    public String recuperaById(Integer idalbum) {
        this.obj = facade.find(idalbum);
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

}
