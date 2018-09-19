/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Album;
import facade.AlbumFacade;
import general.EntityControl;
import interfaces.EntityControlInterface;
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
        this.setOrderBy("title");
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
            //Redireccionando
            return facesUtil.getFacesParamValue("redirectRule_");
        } catch (Exception e) {
            msgErr = "FALLA, actualizando registro ! =>" + e.toString();
        }

        return null;
    }

    @Override
    public String create() {
        
        try {
            facade.create(obj);
            facesUtil.redirect("detail/album.xhtml?" + this.getEntityID() + "_=" + obj.getIdalbum());
            msg = "Registro realizado correctamente!";
        } catch (Exception e) {
            msgErr = "FALLA, creando album !" + e;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCountQueryObj() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getQueryObjects() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SelectItem[] getAllSelect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List autoComplete(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
