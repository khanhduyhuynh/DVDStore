package com.capstone.Controller;

import com.capstone.EJB.CommonEJB;
import com.capstone.EJB.EmailEJB;
import com.capstone.Entity.Business;
import com.capstone.Entity.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

public class LoginController
{
    @EJB
    private CommonEJB commonEJB;
    @EJB
    private EmailEJB emailEJB;

    private ImageUploadController upload = new ImageUploadController();

    private User user = new User();
    private Business business = new Business();
    private String username;
    private String password;

    private UploadedFile file;

    public void login()
    {
        String strNavigate = "";

        RequestContext context = RequestContext.getCurrentInstance();

        boolean checkBusiness = commonEJB.checkExisted("select b from Business b where b.username = '" + username + "' and b.password = '" + password + "'");
        if(checkBusiness == true)
        {
            strNavigate = "no error";
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", "business");
        }
        else
        {
            showMessage("Invalid username or password", null, "error");
        }

       context.addCallbackParam("loggedIn", strNavigate);
    }

    public String doCreateBusiness()
    {
        String strNavigate = "";

        boolean checkBusinessNumber = commonEJB.checkExisted("select b from Business b where b.businessNumber = '" + business.getBusinessNumber().trim() + "'");
        if(checkBusinessNumber == true)
        {
            showMessage("This business number is already taken, please check it again", "Error Message", "error");
        }
        boolean checkBusinessName = commonEJB.checkExisted("select b from Business b where b.businessName = '" + business.getBusinessName().trim() + "'");
        if(checkBusinessName == true)
        {
            showMessage("This business name is already taken, please check it again", "errorMessage", "error");
        }
        boolean checkUsername = commonEJB.checkExisted("select b from Business b where b.username = '" + business.getUsername().trim() + "'");
        if(checkUsername == true)
        {
            showMessage("This username is already taken, please choose another one", "Error Message", "error");
        }

        if(checkBusinessNumber == false && checkBusinessName == false && checkUsername == false)
        {
            if(getFile() != null)
            {
                String fileName = getFile().getFileName();
                boolean checkExtension = upload.checkExtension(fileName);
                if(checkExtension == true)
                {
                    String businessLogo;
                    try
                    {
                        businessLogo = upload.copyImage(fileName, getFile().getInputstream(), "logo");
                        if(!businessLogo.equals(""))
                        {
                            business.setBusinessLogo(businessLogo);
                        }
                        else
                        {
                            showMessage("Logo can not upload to server", "Error Messages", "error");
                        }
                    }
                    catch (IOException ex)
                    {
                        showMessage("Logo can not upload to server", "Error Messages", "error");
                    }

                }
            }
            boolean checkPersist = commonEJB.persistData(business);
            if(checkPersist == true)
            {
                FacesContext context = FacesContext.getCurrentInstance();
				    Flash flash = context.getExternalContext().getFlash();
				    flash.setKeepMessages(true);
				    FacesMessage msg = new FacesMessage("Congratulation","A new account has been created successful");
				    context.addMessage(null, msg);
                    strNavigate = "home?faces-redirect=true";
            }
            else
            {
                showMessage("Data cannot persist to database", "Error Message", "error");
            }
        }
        return strNavigate;
    }

    public void returnPass()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        String strReturnedPass = "";
        boolean checkUsername = commonEJB.checkExisted("select u from User u where u.username = '" + username + "'");
        if(checkUsername == true)
        {
            User u = (User)commonEJB.getSingleInfo("select u from User u where u.username = '" + username + "'");
            try
            {
                emailEJB.sendMessage(u.getEmail().toString().trim(), "Password return from DVDStore", "The password to login to DVDStore for " + username + " is " + u.getPassword().toString().trim());
                showMessage("Your password has sent back to your email: " + u.getEmail().toString().trim(), "Success", "info");
                strReturnedPass = "no error";
            }
            catch(Exception e)
            {
                showMessage("Email Error", "Email cannot be sent", "error");
            }
        }
        else
        {
            showMessage("This username does not exist, please check again", "Error Message", "error");
        }
        context.addCallbackParam("returnedPass", strReturnedPass);

    }

    public String signUpNavigave()
    {
        return "/signUp.xhtml?faces-redirect=true";
    }

    public String cancelNavigate()
    {
        return "/home.xhtml?faces-redirect=true";
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

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Business getBusiness()
    {
        return business;
    }

    public void setBusiness(Business business)
    {
        this.business = business;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ImageUploadController getUpload()
    {
        return upload;
    }

    public void setUpload(ImageUploadController upload)
    {
        this.upload = upload;
    }

    public UploadedFile getFile()
    {
        return file;
    }

    public void setFile(UploadedFile file)
    {
        this.file = file;
    }
}
