/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Country;
import facade.CountryFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.BeanUtil;

@FacesConverter(forClass = Country.class)
public class CountryConverter implements Converter {

    CountryFacade bean = (CountryFacade) BeanUtil.lookupFacadeBean(CountryFacade.class);

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
        Country o = (Country) object;
        return o.getIdcountry().toString();
    }
}
