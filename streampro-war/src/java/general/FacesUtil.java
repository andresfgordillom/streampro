/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class FacesUtil {

    FacesContext facesContext;

    public FacesUtil(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public void redirect(String url) {
        try {
            //facesContext.getCurrentInstance().getExternalContext().redirect(url);
            HttpServletResponse res = (HttpServletResponse) facesContext.getCurrentInstance().getExternalContext().getResponse();
            res.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("FALLA, redireccionando a: " + url + " :: " + e);
            e.printStackTrace();
        }
    }

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public void redirectLoginEstudiante() {
        redirect("http://" + getHostPortContext() + "/app-estudiante/index.xhtml");
    }

    public void redirectLoginProfesor() {
        redirect("http://" + getHostPortContext() + "/app-profesor/index.xhtml");
    }

    public String getHostClient() {
        HttpServletRequest r_ = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        return r_.getRemoteAddr();
    }

    public String getHostServer() {
        HttpServletRequest r_ = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        return r_.getServerName();
    }

    public String getCurrentClient() {
        HttpServletRequest r_ = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        return "http://" + r_.getServerName() + ":" + r_.getServerPort() + r_.getRequestURI();
    }

    public String getHost() {
        HttpServletRequest r_ = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return r_.getServerName();
    }

    public Integer getPort() {
        HttpServletRequest r_ = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return r_.getServerPort();
    }

    public String getPathInfo() {
        HttpServletRequest r_ = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return r_.getPathInfo();
    }

    public String getContext() {
        HttpServletRequest r_ = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return r_.getContextPath() + "/faces";
    }

    public String getHostPortContext() {
        FacesContext fc = facesContext.getCurrentInstance();
        if (fc == null) {
            fc = facesContext;
        }
        HttpServletRequest r_ = (HttpServletRequest) fc.getExternalContext().getRequest();
        return r_.getServerName() + ":" + r_.getServerPort() + r_.getContextPath() + "/faces";
    }

    public String getHostPortPath() {
        FacesContext fc = facesContext.getCurrentInstance();
        if (fc == null) {
            fc = facesContext;
        }
        HttpServletRequest r_ = (HttpServletRequest) fc.getExternalContext().getRequest();
        return r_.getServerName() + ":" + r_.getServerPort() + r_.getContextPath();
    }

    public String getFacesParamValue(String nomParam) {
        FacesContext fc = facesContext.getCurrentInstance();
        if (fc == null) {
            fc = facesContext;
        }
        return fc.getExternalContext().getRequestParameterMap().get(nomParam);
    }

    public Integer getFacesParamInteger(String nomParam) {
        String value = this.getFacesParamValue(nomParam);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                return null;
            }

        } else {
            return null;
        }
    }

    public String getCookieValue(String nameCookie) {
        Cookie cook_ = (Cookie) facesContext.getExternalContext().getRequestCookieMap().get(nameCookie);
        if (cook_ != null) {
            return cook_.getValue();
        }
        return null;
    }

    public Object getSessionVar(String nombreVar) {
        return facesContext.getCurrentInstance().getExternalContext().getSessionMap().get(nombreVar);
    }

    public void setSessionVar(String nombreVar, Object valor) {
        facesContext.getCurrentInstance().getExternalContext().getSessionMap().put(nombreVar, valor);
    }

    public void destroySessionVar(String nomVar) {
        facesContext.getExternalContext().getSessionMap().remove(nomVar);
    }

    private void setMsg(String msg) {
        setSessionVar("msg_", msg);
    }

    private int msgCount() {
        if (getSessionVar("msgCount_") == null) {
            return 0;
        }
        return (Integer) getSessionVar("msgCount_");
    }

    private void msgCountRestart() {
        setSessionVar("msgCount_", 0);
    }

    private void msgCountAdd() {
        int n = msgCount() + 1;
        setSessionVar("msgCount_", n);
    }

    private String getMsg() {
        Object o = getSessionVar("msg_");
        if (o != null) {
            return o.toString();
        } else {
            return null;
        }
    }

    private void setMsgTitle(String msg) {
        setSessionVar("msgT_", msg);
    }

    private String getMsgTitle() {
        Object o = getSessionVar("msgT_");
        if (o != null) {
            return o.toString();
        } else {
            return null;
        }
    }

    private void setMsgStyle(String msg) {
        setSessionVar("msgS_", msg);
    }

    private String getMsgStyle() {
        Object o = getSessionVar("msgS_");
        if (o != null) {
            return o.toString();
        } else {
            return null;
        }
    }

    public String getMsgContent() {
        String t = getMsgTitle();
        String m = getMsg();
        if (t == null || t.trim().equals("")) {
            t = "";
        }
        if (m == null || m.trim().equals("")) {
            m = "";
        }
        if (t.isEmpty() && m.isEmpty()) {
            return "";
        }

        msgCountAdd();

        String s = "<div class='" + getMsgStyle() + "'><b>" + t + "</b> " + m + "</div>";

        if (msgCount() > 1) {
            setMsgTitle("");
            setMsg("");
            setMsgStyle("");
            msgCountRestart();
        }
        return s;
    }

    private void msg(String styleClass, String title, String msgContent) {
        setMsgTitle(title);
        setMsg(msgContent);
        setMsgStyle(styleClass);
    }

    public void msgInfo(String title, String msg) {
        msg("info-blue", title, msg);
    }

    public void msgWarning(String title, String msg) {
        msg("info-yellow", title, msg);
    }

    public void msgError(String title, String msg) {
        msg("info-red", title, msg);
    }

    public void msgOk(String title, String msg) {
        msg("info-green", title, msg);
    }

    public String getImgSeguridadHtml() {
        return "<img src='http://" + getHostPortContext() + "/img/peligro.gif'/>";
    }

}
