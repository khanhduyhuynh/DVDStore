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
public class GameCategory 
{
    private List<String> gameCategories = new ArrayList<String>();
    
    public GameCategory()
    {
        gameCategories.add("Action & Adventure");
        gameCategories.add("Animation");
        gameCategories.add("Comedy");
        gameCategories.add("Documentary");
        gameCategories.add("Drama");
        gameCategories.add("Family & Kids");
        gameCategories.add("Foreign");
        gameCategories.add("Horror");
        gameCategories.add("Music & Performing Arts");
        gameCategories.add("Mystery & Suspense");
        gameCategories.add("Romance");
        gameCategories.add("Sci-Fi & Fantasy");
        gameCategories.add("Sports & Fitness");
        gameCategories.add("War");
        gameCategories.add("Western");
       
    }    
    public List<String> getLsGameCategory()
    {
        return gameCategories;
    }
}
