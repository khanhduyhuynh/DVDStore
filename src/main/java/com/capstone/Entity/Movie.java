package com.capstone.Entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "movie_table")

public class Movie extends Product 
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(nullable = false)
    private String classification;
    @Column(nullable = false)
    private String castName;
    @Column(nullable = false)
    private String director;
    @Column(nullable = false)
    private String runtime;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Movie()
    {

    }

    public Movie(String title, String category, String description, String image, String format, String language, String productLink, String classification, String castName, String director, String runtime) 
    {
        super(title, category, description, image, format, language, productLink);
        this.classification = classification;
        this.castName = castName;
        this.director = director;
        this.runtime = runtime;
    }
    
    public Movie(String title, String category, String description, String image, String format, String language, Date releasedDate, String productLink, String classification, String castName, String director, String runtime) 
    {
        super(title, category, description, image, format, language, releasedDate, productLink);
        this.classification = classification;
        this.castName = castName;
        this.director = director;
        this.runtime = runtime;
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

    public String getCastName() 
    {
        return castName;
    }

    public void setCastName(String castName) 
    {
	this.castName = castName;                
    }

    public String getDirector() 
    {
        return director;
    }

    public void setDirector(String director) 
    {
        this.director = director;
    }

    public String getRuntime() 
    {
	return runtime;
    }

    public void setRuntime(String runtime) 
    {
        this.runtime = runtime;
    }

}