/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Deezifyuser;
import facade.UserFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.BeanUtil;

@FacesConverter(forClass = Deezifyuser.class)
public class UserConverter implements Converter {

    UserFacade bean = (UserFacade) BeanUtil.lookupFacadeBean(UserFacade.class);

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
            return String.valueOf(((Deezifyuser) object).getIduser());
        } else {
            return null;
        }
    }
}
