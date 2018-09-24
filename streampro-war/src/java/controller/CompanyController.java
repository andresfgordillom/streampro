/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Company;
import facade.CompanyFacade;
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
            msgErr = "FALLA, creando Compañía !" + e;
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
        List<Company> l = facade.findAllOrderDesc("fechaActa");
        return getSelectItem(l).toArray(new SelectItem[0]);
    }

    private List<SelectItem> getSelectItem(List<Company> list) {
        SelectItem sel = new SelectItem(null, "----------");

        List lstC = new ArrayList();
        lstC.add(sel);

        for (Company c : list) {
            sel = new SelectItem(c, c.getCompName());
            lstC.add(sel);
        }
        return lstC;
    }

    @Override
    public List autoComplete(String query) {
        return facade.searchFullTextList(query, false, 0, this.maxRegList);
    }

}
