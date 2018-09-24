/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Genre;
import facade.GenreFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.BeanUtil;

@FacesConverter(forClass = Genre.class)
public class GenreConverter implements Converter {

    GenreFacade bean = (GenreFacade) BeanUtil.lookupFacadeBean(GenreFacade.class);

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
        Genre o = (Genre) object;
        return o.getIdgenre().toString();
    }
}
