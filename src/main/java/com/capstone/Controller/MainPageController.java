/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capstone.Controller;

import com.capstone.EJB.CommonEJB;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author duy
 */

public class MainPageController 
{
    @EJB
    private CommonEJB commonEJB;
    
    private String status = "login";
    private CategoryMenuModel menuModel = new CategoryMenuModel();
   
    @PostConstruct
    public void init() throws IOException
    {

        menuModel.setMenuModel();
    }

 
    public CategoryMenuModel getMenuModel() 
    {
        return menuModel;
    }
 
    public void setMenuModel(CategoryMenuModel menuModel) 
    {
        this.menuModel = menuModel;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
   
    
}
