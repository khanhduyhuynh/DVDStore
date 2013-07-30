package com.capstone.Security;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthorizationListener implements PhaseListener 
{
    @Override
    public void afterPhase(PhaseEvent event) 
    {

        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf("home.xhtml") > -1 || currentPage.lastIndexOf("signUp.xhtml") > -1 || currentPage.lastIndexOf("productDetails.xhtml") > -1);
        if(!isLoginPage)
        {
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

            if(session==null)
            {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");
            }

            else
            {
                Object currentUser = session.getAttribute("username");

                if (!isLoginPage && (currentUser == null || currentUser == "")) 
                {
                    NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                    nh.handleNavigation(facesContext, null, "loginPage");
                }
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) 
    {

    }

    @Override
    public PhaseId getPhaseId() 
    {
        return PhaseId.RESTORE_VIEW;
    }
}

