/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Artist;
import facade.ArtistFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.BeanUtil;

@FacesConverter("artistConverter")
public class ArtistConverter implements Converter {

    ArtistFacade bean = (ArtistFacade) BeanUtil.lookupFacadeBean(ArtistFacade.class);

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return bean.find(Integer.parseInt(value.trim()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object != null) {
            return String.valueOf(((Artist) object).getIdartist());
        } else {
            return null;
        }
    }
}
