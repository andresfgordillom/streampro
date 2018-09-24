/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author root
 */
public class BeanUtil {

    public static <T> T lookupFacadeBean(Class<T> clase) {
        try {
            Context c = new InitialContext();
            String appName = "streampro";//(String) c.lookup("java:app/AppName");

            return (T) c.lookup("java:global/" + appName + "/streampro-ejb/" + clase.getSimpleName() + "!" + clase.getName() + "");
        } catch (Exception e) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            System.out.println("FALLA, en LOOKUP llamando EJB -> " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
