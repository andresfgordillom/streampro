/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Country;
import facade.CountryFacade;
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
public class CountryController extends EntityControl implements EntityControlInterface {

    @EJB
    private CountryFacade facade;
    Country obj = new Country();

    public CountryController() {
        this.setEntityName("Country");
        this.setAllResult(true);
        this.setFirstRegList(0);
        this.setMaxRegList(25);
        this.setOrderBy("countryName");
        this.setAscDesc("DESC");
        this.setEntityID("idcountry");
    }

    @Override
    public Country getObj() {
        return obj;
    }

    @Override
    public void setObj(Object obj) {
        this.obj = (Country) obj;
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
        this.obj = facade.find(facesUtil.getFacesParamInteger("idcountry_"));
        return "";
    }

    public String recuperaById(Integer idcountry) {
        this.obj = facade.find(idcountry);
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
            msgErr = "FALLA, creando País !" + e;
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
