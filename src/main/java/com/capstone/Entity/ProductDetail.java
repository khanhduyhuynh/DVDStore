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
public class ProductDetail 
{
    private Long PID;
    private String title;
    private String category;
    private String description;
    private String image;
    private String productLink;
    private String businessName;
    private String businessLogo;
    private String salesPrice;
    private String salesAvailable;
    private String leasesPrice;
    private String leasesAvailable;
    private Integer nbrOfBrands;
    
    public ProductDetail(Long PID, String title, String image, String salesPrice, String salesAvailable, String leasesPrice, String leasesAvailable, String category, Integer nbrOfBrands)
    {
        this.PID = PID;
        this.title = title;
        this.image = image;
        this.salesPrice = salesPrice;
        this.salesAvailable = salesAvailable;
        this.leasesPrice = leasesPrice;
        this.leasesAvailable = leasesAvailable;
        this.category = category;
        this.nbrOfBrands = nbrOfBrands;
    }
    
    public ProductDetail(Long PID, String title, String category, String description, String image, String productLink, String businessName, String businessLogo, String salesPrice, String salesAvailable, String leasesPrice, String leasesAvailable)
    {
        this.PID = PID;
        this.title = title;
        this.category = category;
        this.description = description;
        this.image = image;
        this.productLink = productLink;
        this.businessName = businessName;
        this.businessLogo = businessLogo;
        this.salesPrice = salesPrice;
        this.salesAvailable = salesAvailable;
        this.leasesPrice = leasesPrice;
        this.leasesAvailable = leasesAvailable;
    }
    
    public Long getPID()
    {
        return PID;
    }
    
    public void setPID(Long PID)
    {
        this.PID = PID;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    public String getBusinessName()
    {
        return businessName;
    }
    
    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }
    
    public String getBusinessLogo()
    {
        return businessLogo;
    }
    
    public void setBusinessLogo(String businessLogo)
    {
        this.businessLogo = businessLogo;
    }
    
    public String getProductLink()
    {
        return productLink;
    }
    
    public void setProductLink(String productLink)
    {
        this.productLink = productLink;
    }
    
    public String getSalesPrice()
    {
        return salesPrice;
    }
    
    public void setSalesPrice(String salesPrice)
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
    
    public String getLeasesPrice()
    {
        return leasesPrice;
    }
    
    public void setLeasesPrice(String leasesPrice)
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
    
    public Integer getNbrOfBrands()
    {
        return nbrOfBrands;
    }
    
    public void setNbrOfBrands(Integer nbrOfBrands)
    {
        this.nbrOfBrands = nbrOfBrands;
    }
}
