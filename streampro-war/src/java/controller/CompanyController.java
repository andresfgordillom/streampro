/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Company;
import facade.CompanyFacade;
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
public class CompanyController extends EntityControl implements EntityControlInterface {

    @EJB
    private CompanyFacade facade;
    Company obj = new Company();

    public CompanyController() {
        this.setEntityName("Company");
        this.setAllResult(true);
        this.setFirstRegList(0);
        this.setMaxRegList(25);
        this.setOrderBy("compName");
        this.setAscDesc("DESC");
        this.setEntityID("idcompany");
    }

    @Override
    public Company getObj() {
        return obj;
    }

    @Override
    public void setObj(Object obj) {
        this.obj = (Company) obj;
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
        this.obj = facade.find(facesUtil.getFacesParamInteger("idcompany_"));
        return "";
    }

    public String recuperaById(Integer idcompany) {
        this.obj = facade.find(idcompany);
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
            facesUtil.redirect("detail/company.xhtml?" + this.getEntityID() + "_=" + obj.getIdcompany());
            msg = "Registro realizado correctamente!";
        } catch (Exception e) {
            msgErr = "FALLA, creando Compañía !" + e;
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