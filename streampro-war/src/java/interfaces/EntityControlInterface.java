package interfaces;

import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public interface EntityControlInterface {
    
    public Object getObj();
    public void setObj(Object obj);
    public String getAllObjects();
    public int getCountAllObjects();
    public String getObjectById();
    public String edit();
    public String create();
    public int getCountQueryObj();
    public String getQueryObjects();
    public SelectItem[] getAllSelect();
    public List autoComplete(String query); 
    
}
