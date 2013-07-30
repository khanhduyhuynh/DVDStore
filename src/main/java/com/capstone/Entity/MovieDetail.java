/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capstone.Entity;

import java.util.Date;

/**
 *
 * @author duy
 */
public class MovieDetail extends Product
{
    private Long MDID;
    private String classification;
    private String castName;
    private String director;
    private String runtime;
    private Double salesPrice;
    private String salesAvailable;
    private Double leasesPrice;
    private String leasesAvailable;
  
    public MovieDetail(Long PID, String title, String category, String description, String image, String format, String language, Date releasedDate, String productLink, String classification, String castName, String director, String runtime, Double salesPrice, String salesAvailable, Double leasesPrice, String leasesAvailable)
    {
        super(title, category, description, image, format, language, releasedDate, productLink);
        this.MDID = PID;
        this.classification = classification;
        this.castName = castName;
        this.director = director;
        this.runtime = runtime;
        this.salesPrice = salesPrice;
        this.salesAvailable = salesAvailable;
        this.leasesPrice = leasesPrice;
        this.leasesAvailable = leasesAvailable;
    }
    
    public Long getMDID()
    {
        return MDID;
    }
    
    public void setMDID(Long MDID)
    {
        this.MDID = MDID;
    }
    public String getClassification() 
    {
        return classification;
    }

    public void setClassification(String classification) 
    {
        this.classification = classification;
    }

    public String getCastName() 
    {
        return castName;
    }

    public void setCastName(String castName) 
    {
	this.castName = castName;                
    }

    public String getDirector() 
    {
        return director;
    }

    public void setDirector(String director) 
    {
        this.director = director;
    }

    public String getRuntime() 
    {
	return runtime;
    }

    public void setRuntime(String runtime) 
    {
        this.runtime = runtime;
    }
    
    public Double getSalesPrice()
    {
        return salesPrice;
    }
    
    public void setSalesPrice(Double salesPrice)
    {
        this.salesPrice = salesPrice;
    }
    
    public String getSalesAvailable()
    {
        return salesAvailable;
    }
    
    public void setSalesAvailable(String salesAvailable)
    {
        this.salesAvailable = salesAvailable;
    }
    
    public Double getLeasesPrice()
    {
        return leasesPrice;
    }
    
    public void setLeasesPrice(Double leasesPrice)
    {
        this.leasesPrice = leasesPrice;
    }
    
    public String getLeasesAvailable()
    {
        return leasesAvailable;
    }
    
    public void setLeasesAvailable(String leasesAvailable)
    {
        this.leasesAvailable = leasesAvailable;
    }
}
