/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capstone.Controller;
import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;

public class GuestPreferences implements Serializable 
{
    private String theme = "humanity"; //default

    public String getTheme() 
    {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if(params.containsKey("theme")) 
        {
            theme = params.get("theme");
        }
        return theme;
    }

    public void setTheme(String theme) 
    {
        this.theme = theme;
    }
}
