/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capstone.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duy
 */
public class MovieClassification 
{
    private List<String> movieClassifications = new ArrayList<String>();
    
    public MovieClassification()
    {
        movieClassifications.add("G");
        movieClassifications.add("PG");
        movieClassifications.add("M");
        movieClassifications.add("MA15+");
        movieClassifications.add("R18+");
    }
    
    public List<String> getLsMovieClassification()
    {
        return movieClassifications;
    }
}
