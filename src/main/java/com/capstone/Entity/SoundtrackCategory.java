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
public class SoundtrackCategory 
{
    private List<String> soundtrackCategories = new ArrayList<String>();
    
    public SoundtrackCategory()
    {
        soundtrackCategories.add("Alternative");
        soundtrackCategories.add("Anime");
        soundtrackCategories.add("Blues");
        soundtrackCategories.add("Children's Music");
        soundtrackCategories.add("Classical");
        soundtrackCategories.add("Comedy");
        soundtrackCategories.add("Country");
        soundtrackCategories.add("Dance");
        soundtrackCategories.add("Disney");
        soundtrackCategories.add("Easy Listening");
        soundtrackCategories.add("Electronic");
        soundtrackCategories.add("Enka");
        soundtrackCategories.add("French Pop");
        soundtrackCategories.add("German Folk");
        soundtrackCategories.add("German Pop");
        soundtrackCategories.add("HipHop/Rap");
        soundtrackCategories.add("Indie");
        soundtrackCategories.add("Christian/Gospel");
        soundtrackCategories.add("Instrument");
        soundtrackCategories.add("J-POP");
        soundtrackCategories.add("Jazz");
        soundtrackCategories.add("K-POP");
        soundtrackCategories.add("Latino");
        soundtrackCategories.add("New Age");
        soundtrackCategories.add("Opera");
        soundtrackCategories.add("Pop");
        soundtrackCategories.add("R&B");
        soundtrackCategories.add("Reggae");
        soundtrackCategories.add("Rock");
    }    
    public List<String> getLsSoundtrackCategory()
    {
        return soundtrackCategories;
    }
}
