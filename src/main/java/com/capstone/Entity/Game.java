package com.capstone.Entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "game_table")

public class Game extends Product 
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(nullable = false)
    private String classification;
    @Column(nullable = false)
    private String manufacturer;
    @Column(nullable = false)
    private Integer nbrOfPlayers;
    @OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.REMOVE)
    @JoinColumn(name = "systemRequirement_fk", nullable = false)
    private SystemRequirement systemRequirement;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Game()
    {

    }

    public Game(String title, String category, String description, String image, String format, String language, String productLink, String classification, String manufacturer, int nbrOfPlayers) 
    {
        super(title, category, description, image, format, language, productLink);
        this.classification = classification;
        this.manufacturer = manufacturer;
        this.nbrOfPlayers = nbrOfPlayers;
    }
    
    public Game(String title, String category, String description, String image, String format, String language, Date releasedDate, String productLink, String classification, String manufacturer, int nbrOfPlayers) 
    {
        super(title, category, description, image, format, language, releasedDate, productLink);
        this.classification = classification;
        this.manufacturer = manufacturer;
        this.nbrOfPlayers = nbrOfPlayers;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public String getClassification() 
    {
        return classification;
    }

    public void setClassification(String classification) 
    {
        this.classification = classification;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) 
    {
	this.manufacturer = manufacturer;
    }

    public Integer getNbrOfPlayers() 
    {
	return nbrOfPlayers;
    }

    public void setNbrOfPlayers(Integer nbrOfPlayers) 
    {
        this.nbrOfPlayers = nbrOfPlayers;
    }

    public SystemRequirement getSystemRequirement() 
    {
	return systemRequirement;
    }

    public void setSystemRequirement(SystemRequirement systemRequirement) 
    {
        this.systemRequirement = systemRequirement;
    }

}