/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Deezifyuser;
import facade.UserFacade;
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
public class UserController extends EntityControl implements EntityControlInterface {

    @EJB
    private UserFacade facade;
    Deezifyuser obj = new Deezifyuser();

    public UserController() {
        this.setEntityName("Deezifyuser");
        this.setAllResult(true);
        this.setFirstRegList(0);
        this.setMaxRegList(25);
        this.setOrderBy("nickname");
        this.setAscDesc("DESC");
        this.setEntityID("iduser");
    }

    @Override
    public Deezifyuser getObj() {
        return obj;
    }

    @Override
    public void setObj(Object obj) {
        this.obj = (Deezifyuser) obj;
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
        this.obj = facade.find(facesUtil.getFacesParamInteger("iduser_"));
        return "";
    }

    public String recuperaById(Integer iduser) {
        this.obj = facade.find(iduser);
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
            msgErr = "FALLA, creando Usuario !" + e;
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
