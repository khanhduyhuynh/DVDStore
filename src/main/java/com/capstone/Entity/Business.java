package com.capstone.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "business_table")

public class Business extends User implements Serializable
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(nullable = false, unique = true)
    private String businessNumber;
    @Column(nullable = false, unique = true)
    private String businessName;
    @Column(nullable = true)
    private String businessLogo;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Business()
    {

    }
    
    public Business(String username, String password, String firstName, String lastName, String address, String suburb, String state,String postCode, String contactNumber, String email, String businessNumber, String businessName)
    {
	super(username, password, firstName, lastName, address, suburb, state, postCode, contactNumber, email);
	this.businessNumber = businessNumber;
	this.businessName = businessName;
    }

    public Business(String username, String password, String nameTitle, String firstName, String lastName, String address, String suburb, String state,String postCode, String contactNumber, String email, String businessNumber, String businessName)
    {
	super(username, password, nameTitle, firstName, lastName, address, suburb, state, postCode, contactNumber, email);
	this.businessNumber = businessNumber;
	this.businessName = businessName;
    }

    public Business(String username, String password, String nameTitle, String firstName, String lastName, String address, String suburb, String state,String postCode, String contactNumber, String email, String businessNumber, String businessName, String businessLogo)
    {
	super(username, password, nameTitle, firstName, lastName, address, suburb, state, postCode, contactNumber, email);
	this.businessNumber = businessNumber;
	this.businessName = businessName;
        this.businessLogo = businessLogo;
    }


    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public String getBusinessNumber()
    {
	return businessNumber;
    }

    public void setBusinessNumber(String businessNumber)
    {
    	this.businessNumber = businessNumber;
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

}