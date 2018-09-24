/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Company;
import facade.CompanyFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.BeanUtil;

@FacesConverter(forClass = Company.class)
public class CompanyConverter implements Converter {

    CompanyFacade bean = (CompanyFacade) BeanUtil.lookupFacadeBean(CompanyFacade.class);

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        return bean.find(Integer.parseInt(string.trim()));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        Company o = (Company) object;
        return o.getIdcompany().toString();
    }
}
