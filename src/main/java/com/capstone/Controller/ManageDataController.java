package com.capstone.Controller;

import com.capstone.EJB.CommonEJB;
import com.capstone.Entity.Business;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

public class ManageDataController 
{
    @EJB
    private CommonEJB commonEJB;
    
    private ImageUploadController upload = new ImageUploadController();
    private Business business = new Business();
    
    private List<String> options = new ArrayList<String>();
    private String username;
    private String oldPassword;
    private String newPassword;
    
    private String imageLogo;
    
    @PostConstruct
    public void init()
    {
        username = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username").toString();
        options.add("Available");
        options.add("Not Available");
        business = (Business)commonEJB.getSingleInfo("select b from Business b where b.username = '" + username + "'");
        imageLogo = business.getBusinessLogo().toString().trim();
    }
    
    public String navigationMovie()
    {
        return "/manageMovie.xhtml?faces-redirect=true";
    }
    
    public String navigationGame()
    {
        return "/manageGame.xhtml?faces-redirect=true";
    }
    
    public String navigationSoundtrack()
    {
        return "/manageSoundtrack.xhtml?faces-redirect=true";
    }
    
    public String logout()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "home.xhtml?faces-redirect=true";
    }
    
    public void changePassword()
    {
        RequestContext context = RequestContext.getCurrentInstance();

        Business b = (Business)commonEJB.getSingleInfo("select b from Business b where b.username = '" + username + "'");
        if(b.getPassword().toString().trim().equals(oldPassword.trim()))
        {
            b.setPassword(newPassword);
            boolean checkChangePass = commonEJB.updateData(b);
            if(checkChangePass == true)
            {
                showMessage("Success", "Password has changed successful", "info");
            }
            else
            {
                showMessage("Fail", "Cannot persist to database", "error");
            }
            context.addCallbackParam("changedPass", "yes");
        }
        else
        {
            showMessage("Old password is incorrect", null, "error");
            context.addCallbackParam("changedPass", "");
        }
  
    }
    
    public void uploadImage(FileUploadEvent event) throws IOException
    {
        boolean checkImage = true;
        
        if(business.getBusinessLogo() != null)
        {
            if(!imageLogo.equalsIgnoreCase(business.getBusinessLogo().toString().trim()))
            {
                checkImage = upload.deleteImage(imageLogo);
            }
        }
        else
        {
            if(!imageLogo.equalsIgnoreCase(""))
            {
                checkImage = upload.deleteImage(imageLogo);
            }
        }
        if(checkImage == true)
        {
            String fileName = event.getFile().getFileName();
            boolean checkExtension = upload.checkExtension(fileName);
            if(checkExtension == true)
            {
                imageLogo = upload.copyImage(fileName, event.getFile().getInputstream(), "logo");
            }
        }
        else
        {
            showMessage("Error Message", "Cannot upload image", "error");
        }
    }
    
    public void editBusinessInformation()
    {
        RequestContext context = RequestContext.getCurrentInstance();

        boolean checkImage = true;
        if(!imageLogo.equalsIgnoreCase(business.getBusinessLogo().toString().trim()))
        {
            checkImage = upload.deleteImage(business.getBusinessLogo().toString().trim());
            business.setBusinessLogo(imageLogo);
        }
        
        boolean checkUpdate = commonEJB.updateData(business);
        if(checkUpdate == true)
        {
            showMessage("Success", "Information details have changed successful", "info");
            context.addCallbackParam("editBusiness", "yes");
        }
        else
        {
            showMessage("Fail", "Cannot persist to database", "error");
            context.addCallbackParam("editBusiness", "");
        }
       
    }
    
    public void cancelEditBusiness()
    {
        if(!imageLogo.equalsIgnoreCase(business.getBusinessLogo().toString().trim()))
        {
            boolean checkImage = upload.deleteImage(imageLogo);
            if(checkImage == true)
            {
                imageLogo = business.getBusinessLogo().toString().trim();
            }
        }
    }
    
    public void showMessage(String str1, String str2, String type)
    {
        FacesMessage msg = null;
        if(type.equals("info"))
        {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, str1, str2);
        }
        if(type.equals("error"))
        {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, str1, str2);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<String> getOptions()
    {
        return options;
    }
    
    public void setOptions (List<String> options)
    {
        this.options = options;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getOldPassword()
    {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword()
    {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }
    
    public Business getBusiness()
    {
        return business;
    }
   
    public void setBusiness(Business business)
    {
        this.business = business;
    }
    
    public String getImageLogo()
    {
        return imageLogo;
    }
    
    public void setImageLogo(String imageLogo)
    {
        this.imageLogo = imageLogo;
    }
    
    public ImageUploadController getUpload()
    {
        return upload;
    }
 
    public void setUpload(ImageUploadController upload) 
    {
        this.upload = upload;
    }
}