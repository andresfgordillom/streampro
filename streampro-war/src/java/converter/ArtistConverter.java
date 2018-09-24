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

@FacesConverter(forClass = Artist.class)
public class ArtistConverter implements Converter {

    ArtistFacade bean = (ArtistFacade) BeanUtil.lookupFacadeBean(ArtistFacade.class);

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
        Artist o = (Artist) object;
        return o.getIdartist().toString();
    }
}
