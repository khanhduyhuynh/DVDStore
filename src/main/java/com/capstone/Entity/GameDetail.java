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
public class GameDetail extends Product
{
    private Long GDID;
    private String classification;
    private String manufacturer;
    private Integer nbrOfPlayers;
    private String CPU;
    private String RAM;
    private String VGA;
    private String HDD;
    private String OS;
    private String sound;
    private String note;
    private Double salesPrice;
    private String salesAvailable;
    private Double leasesPrice;
    private String leasesAvailable;
    
    public GameDetail(Long PID, String title, String category, String description, String image, String format, String language, Date releasedDate, String productLink, String classification, String manufacturer, Integer nbrOfPlayers, String CPU, String RAM, String VGA, String HDD, String OS, String sound, String note, Double salesPrice, String salesAvailable, Double leasesPrice, String leasesAvailable)
    {
        super(title, category, description, image, format, language, releasedDate, productLink);
        this.GDID = PID;
        this.classification = classification;
        this.manufacturer = manufacturer;
        this.nbrOfPlayers = nbrOfPlayers;
        this.CPU = CPU;
        this.RAM = RAM;
        this.VGA = VGA;
        this.HDD = HDD;
        this.OS = OS;
        this.sound = sound;
        this.note = note;
        this.salesPrice = salesPrice;
        this.salesAvailable = salesAvailable;
        this.leasesPrice = leasesPrice;
        this.leasesAvailable = leasesAvailable;
    }
    
    public Long getGDID()
    {
        return GDID;
    }
    
    public void setGDID(Long GDID)
    {
        this.GDID = GDID;
    }
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
    
    public String getCPU() 
    {
        return CPU;
    }

    public void setCPU(String CPU)
    {
	this.CPU = CPU;
    }

    public String getRAM() 
    {
        return RAM;
    }

    public void setRAM(String RAM) 
    {
        this.RAM = RAM;
    }

    public String getVGA() 
    {
        return VGA;
    }

    public void setVGA(String VGA) 
    {
	this.VGA = VGA;
    }

    public String getHDD() 
    {
	return HDD;
    }

    public void setHDD(String HDD) 
    {
        this.HDD = HDD;
    }

    public String getOS() 
    {
        return OS;
    }

    public void setOS(String OS) 
    {
	this.OS = OS;
    }

    public String getSound() 
    {
	return sound;
    }

    public void setSound(String sound) 
    {
        this.sound = sound;
    }

    public String getNote() 
    {
	return note;
    }

    public void setNote(String note) 
    {
        this.note = note;
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