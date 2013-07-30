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
public class GameClassification 
{
    private List<String> gameClassifications = new ArrayList<String>();
    
    public GameClassification()
    {
        gameClassifications.add("U");
        gameClassifications.add("PG");
        gameClassifications.add("12A");
        gameClassifications.add("12");
        gameClassifications.add("15");
        gameClassifications.add("18");
        
    }
    
    public List<String> getLsGameClassification()
    {
        return gameClassifications;
    }
}
