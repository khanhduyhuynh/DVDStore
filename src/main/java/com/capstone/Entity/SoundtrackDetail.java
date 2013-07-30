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
public class SoundtrackDetail extends Product
{
    private Long STDID;
    private String album;
    private String artist;
    private Integer nbrOfTracks;
    private Double salesPrice;
    private String salesAvailable;
    private Double leasesPrice;
    private String leasesAvailable;
    
    public SoundtrackDetail(Long PID, String title, String category, String description, String image, String format, String language, Date releasedDate, String productLink, String album, String artist, Integer nbrOfTracks, Double salesPrice, String salesAvailable, Double leasesPrice, String leasesAvailable)
    {
        super(title, category, description, image, format, language, releasedDate, productLink);
        this.STDID = PID;
        this.album = album;
        this.artist = artist;
        this.nbrOfTracks = nbrOfTracks;
        this.salesPrice = salesPrice;
        this.salesAvailable = salesAvailable;
        this.leasesPrice = leasesPrice;
        this.leasesAvailable = leasesAvailable;
    }
    
    public Long getSTDID()
    {
        return STDID;
    }
    
    public void setSTDID(Long STDID)
    {
        this.STDID = STDID;
    }
    
    public String getAlbum() 
    {
        return album;
    }

    public void setAlbum(String album) 
    {
        this.album = album;
    }

    public String getArtist() 
    {
        return artist;
    }

    public void setArtist(String artist) 
    {
	this.artist = artist;
    }

    public Integer getNbrOfTracks() 
    {
	return nbrOfTracks;
    }

    public void setNbrOfTracks(Integer nbrOfTracks) 
    {
        this.nbrOfTracks = nbrOfTracks;
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
