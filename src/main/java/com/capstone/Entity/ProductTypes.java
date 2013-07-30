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
public class ProductTypes 
{
    List<String> lsTypes = new ArrayList<String>();
    public ProductTypes()
    {
        lsTypes.add("Movie");
        lsTypes.add("Game");
        lsTypes.add("Soundtrack");
    }
    
    public List<String> getLsTypes()
    {
        return lsTypes;
    }
    
    public void setLsTypes(List<String> lsTypes)
    {
        this.lsTypes = lsTypes;
    }
}
