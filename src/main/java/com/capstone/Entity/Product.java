package com.capstone.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "product_table")
@Inheritance(strategy = InheritanceType.JOINED)

public class Product implements Serializable 
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue
    protected Long PID;
    @Column(nullable = false)
    protected String title;
    @Column(nullable = false)
    protected String category;
    @Column(nullable = false)
    protected String description;
    @Column(nullable = false)
    protected String image;
    @Column(nullable = false)
    protected String format;
    @Column(nullable = false)
    protected String language;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date releasedDate;
    @Column(nullable = false)
    protected String productLink;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;
    

    @OneToOne
    @JoinColumn(name = "businessProduct_fk", nullable = false)
    private Business business;
    @OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.REMOVE)
    @JoinColumn(name = "salesProduct_fk", nullable = true)
    private SalesProduct salesProduct;
    @OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.REMOVE)
    @JoinColumn(name = "leasesProduct_fk", nullable = true)
    private LeasesProduct leasesProduct;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Product()
    {
        this.createdDate = new Date();
    }
    
    public Product(String title, String category, String description, String image, String format, String language, String productLink)
    {
        this.title = title;
        this.category = category;
        this.description = description;
        this.image = image;
        this.format = format;
        this.language = language;
        this.productLink = productLink;
        this.createdDate = new Date();
    }

    public Product(String title, String category, String description, String image, String format, String language, Date releasedDate, String productLink)
    {
        this.title = title;
        this.category = category;
        this.description = description;
        this.image = image;
        this.format = format;
        this.language = language;
        this.releasedDate = releasedDate;
        this.productLink = productLink;
        this.createdDate = new Date();
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public Long getPID()
    {
        return PID;
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

    public String getFormat() 
    {
        return format;
    }

    public void setFormat(String format) 
    {
        this.format = format;
    }

    public String getLanguage() 
    {
	return language;
    }

    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public Date getReleasedDate() 
    {
	return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) 
    {
        this.releasedDate = releasedDate;
    }
    
    public String getProductLink() 
    {
	return productLink;
    }

    public void setProductLink(String productLink) 
    {
        this.productLink = productLink;
    }

    public SalesProduct getSalesProduct()
    {
	return salesProduct;
    }

    public void setSalesProduct(SalesProduct salesProduct)
    {
    	this.salesProduct = salesProduct;
    }

    public LeasesProduct getLeasesProduct()
    {
    	return leasesProduct;
    }

    public void setLeasesProduct(LeasesProduct leasesProduct)
    {
	this.leasesProduct = leasesProduct;
    }
    
    public Business getBusiness()
    {
    	return business;
    }

    public void setBusiness(Business business)
    {
	this.business = business;
    }
    
    public Date getCreatedDate()
    {
    	return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
	this.createdDate = createdDate;
    }

}