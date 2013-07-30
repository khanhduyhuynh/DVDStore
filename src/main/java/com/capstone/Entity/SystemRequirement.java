package com.capstone.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "systemRequirement_table")

public class SystemRequirement implements Serializable
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue
    protected Long SRID;
    @Column(nullable = false)
    private String CPU;
    @Column(nullable = false)
    private String RAM;
    @Column(nullable = false)
    private String VGA;
    @Column(nullable = false)
    private String HDD;
    @Column(nullable = false)
    private String OS;
    @Column(nullable = true)
    private String sound;
    @Column(nullable = true)
    private String note;

    // ======================================
    // =            Constructors            =
    // ======================================

    public SystemRequirement()
    {

    }
    
    public SystemRequirement(String CPU, String RAM, String VGA, String HDD, String OS) 
    {
        this.CPU = CPU;
        this.RAM = RAM;
        this.VGA = VGA;
        this.HDD = HDD;
	this.OS = OS;
    }
    
    public SystemRequirement(String CPU, String RAM, String VGA, String HDD, String OS, String sound) 
    {
        this.CPU = CPU;
        this.RAM = RAM;
        this.VGA = VGA;
        this.HDD = HDD;
	this.OS = OS;
        this.sound = sound;
    }

    public SystemRequirement(String CPU, String RAM, String VGA, String HDD, String OS, String sound, String note) 
    {
        this.CPU = CPU;
        this.RAM = RAM;
        this.VGA = VGA;
        this.HDD = HDD;
	this.OS = OS;
        this.sound = sound;
        this.note = note;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public Long getSRID() 
    {
        return SRID;
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

}