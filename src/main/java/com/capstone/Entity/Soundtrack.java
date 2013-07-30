
package com.capstone.Entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "soundtrack_table")

public class Soundtrack extends Product 
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(nullable = false)
    private String album;
    @Column(nullable = false)
    private String artist;
    @Column(nullable = false)
    private Integer nbrOfTracks;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Soundtrack()
    {

    }
    
    public Soundtrack(String title, String category, String description, String image, String format, String language, String productLink, String album, String artist, Integer nbrOfTracks) 
    {
        super(title, category, description, image, format, language, productLink);
        this.album = album;
        this.artist = artist;
        this.nbrOfTracks = nbrOfTracks;
    }

    public Soundtrack(String title, String category, String description, String image, String format, String language, Date releasedDate, String productLink, String album, String artist, Integer nbrOfTracks) 
    {
        super(title, category, description, image, format, language, releasedDate, productLink);
        this.album = album;
        this.artist = artist;
        this.nbrOfTracks = nbrOfTracks;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

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

}