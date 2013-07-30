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
public class MovieCategory 
{
    private List<String> movieCategories = new ArrayList<String>();
    
    public MovieCategory()
    {
        movieCategories.add("Action");
        movieCategories.add("Adult");
        movieCategories.add("Adventure");
        movieCategories.add("Animation");
        movieCategories.add("Black Cast");
        movieCategories.add("Comedy");
        movieCategories.add("Crime");
        movieCategories.add("Documentary");
        movieCategories.add("Drama");
        movieCategories.add("Family");
        movieCategories.add("Fantasy");
        movieCategories.add("Film-Noir");
        movieCategories.add("Horror");
        movieCategories.add("Martial Arts");
        movieCategories.add("Musical");
        movieCategories.add("Mystery");
        movieCategories.add("Religious");
        movieCategories.add("Romance");
        movieCategories.add("Serial");
        movieCategories.add("Short");
        movieCategories.add("Silent");
        movieCategories.add("Sports");
        movieCategories.add("Thriller");
        movieCategories.add("War");
        movieCategories.add("Western");
    }    
    public List<String> getLsMovieCategory()
    {
        return movieCategories;
    }
}
