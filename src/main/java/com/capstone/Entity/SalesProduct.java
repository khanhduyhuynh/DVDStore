
package com.capstone.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salesProduct_table")

public class SalesProduct implements Serializable 
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue
    protected Long SPID;
    @Column(nullable = false)
    private Double salesPrice;
    @Column(nullable = false)
    private boolean available;

    // ======================================
    // =            Constructors            =
    // ======================================

    public SalesProduct()
    {
        this.available = false;
    }
    
    public SalesProduct(Double salesPrice) 
    {
        this.salesPrice = salesPrice;
        this.available = false;
    }

    public SalesProduct(Double salesPrice, boolean available) 
    {
        this.salesPrice = salesPrice;
        this.available = available;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public Double getSalesPrice() 
    {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) 
    {
        this.salesPrice = salesPrice;
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