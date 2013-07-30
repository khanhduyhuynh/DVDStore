package com.capstone.Controller;

import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;


public class ThemesController 
{    
    private Map<String, String> themes;
    private String theme;
    
    private GuestPreferences gp;

    public void setGp(GuestPreferences gp) 
    {
        this.gp = gp;
    }
    
    @PostConstruct
    public void init() 
    {
        theme = gp.getTheme();
        themes = new TreeMap<String, String>();
        themes.put("Aristo", "aristo");
        themes.put("Black-Tie", "black-tie");
        themes.put("Bluesky", "bluesky");
        themes.put("Casablanca", "casablanca");
        themes.put("Cupertino", "cupertino");
        themes.put("Glass-X", "glass-x");
        themes.put("Humanity", "humanity");
        themes.put("Redmond", "redmond");
        themes.put("Start", "start");
    }
    
    public void saveTheme() 
    {
        gp.setTheme(theme);
    }

    public Map<String, String> getThemes() 
    {
        return themes;
    }

    public String getTheme() 
    {
        return theme;
    }

    public void setTheme(String theme) 
    {
        this.theme = theme;
    }

}
                    