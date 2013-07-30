package com.capstone.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "leasesProduct_table")

public class LeasesProduct implements Serializable 
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue
    protected Long LPID;
    @Column(nullable = false)
    private Double leasesPrice;
    @Column(nullable = false)
    private boolean available;

    // ======================================
    // =            Constructors            =
    // ======================================

    public LeasesProduct()
    {
        this.available = false;
    }

    public LeasesProduct(Double leasesPrice) 
    {
        this.leasesPrice = leasesPrice;
        this.available = false;
    }
    
    public LeasesProduct(Double leasesPrice, boolean available) 
    {
        this.leasesPrice = leasesPrice;
        this.available = available;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public Long getLPID()
    {
        return LPID;
    }

    public Double getLeasesPrice() 
    {
        return leasesPrice;
    }

    public void setLeasesPrice(Double leasesPrice) 
    {
        this.leasesPrice = leasesPrice;
    }
    
    public boolean getAvailable() 
    {
        return available;
    }

    public void setAvailable(boolean available) 
    {
        this.available = available;
    }

}