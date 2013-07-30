package com.capstone.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.JOINED)

public class User implements Serializable
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @Column(nullable = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = true)
    private String nameTitle;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String suburb;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String postCode;
    @Column(nullable = false)
    private String contactNumber;
    @Column(nullable = false)
    private String email;
    

    // ======================================
    // =            Constructors            =
    // ======================================

    public User()
    {

    }

    public User(String username, String password, String firstName, String lastName, String address, String suburb, String state,String postCode, String contactNumber, String email)
    {
        this.username = username;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.suburb = suburb;
	this.state = state;
	this.postCode = postCode;
	this.contactNumber = contactNumber;
	this.email = email;   
    }

    public User(String username, String password, String nameTitle, String firstName, String lastName, String address, String suburb, String state,String postCode, String contactNumber, String email)
    {
        this.username = username;
	this.password = password;
        this.nameTitle = nameTitle;
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.suburb = suburb;
	this.state = state;
	this.postCode = postCode;
	this.contactNumber = contactNumber;
	this.email = email;   
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

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
    
    public String getNameTitle()
    {
	return nameTitle;
    }

    public void setNameTitle(String nameTitle)
    {
    	this.nameTitle = nameTitle;
    }

    public String getFirstName()
    {
	return firstName;
    }

    public void setFirstName(String firstName)
    {
    	this.firstName = firstName;
    }

    public String getLastName()
    {
	return lastName;
    }

    public void setLastName(String lastName)
    {
    	this.lastName = lastName;
    }

    public String getAddress()
    {
	return address;
    }

    public void setAddress(String address)
    {
    	this.address = address;
    }

    public String getSuburb()
    {
	return suburb;
    }

    public void setSuburb(String suburb)
    {
    	this.suburb = suburb;
    }

    public String getState()
    {
	return state;
    }

    public void setState(String state)
    {
    	this.state = state;
    }

    public String getPostCode()
    {
	return postCode;
    }

    public void setPostCode(String postCode)
    {
    	this.postCode = postCode;
    }

    public String getContactNumber()
    {
	return contactNumber;
    }

    public void setContactNumber(String contactNumber)
    {
    	this.contactNumber = contactNumber;
    }

    public String getEmail()
    {
	return email;
    }

    public void setEmail(String email)
    {
    	this.email = email;
    }

}