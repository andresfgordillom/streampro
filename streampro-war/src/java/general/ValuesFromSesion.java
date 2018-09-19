/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.util.HashMap;
import javax.faces.context.FacesContext;

public class ValuesFromSesion {

    FacesContext facesContext;
    FacesUtil facesUtil;

    public ValuesFromSesion(FacesContext facesContext) {
        this.facesContext = facesContext;
        this.facesUtil = new FacesUtil(this.facesContext);
    }

    public boolean isModoPrueba() {
        Object mod = facesContext.getCurrentInstance().getExternalContext().getSessionMap().get("modprueba_");
        if (mod != null && mod.toString().trim().compareTo("true") == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setModoprueba(boolean mod) {
        if (mod) {
            facesContext.getCurrentInstance().getExternalContext().getSessionMap().put("modprueba_", "true");
        } else {
            facesContext.getCurrentInstance().getExternalContext().getSessionMap().put("modprueba_", "false");
        }
    }

    public HashMap<Integer, Integer> getMapFuncionalidades() {
        return (HashMap<Integer, Integer>) facesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapIdFunc_");
    }

}
