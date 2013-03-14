package com.binns.flagsoftheworld;

import android.graphics.drawable.Drawable;

public class Country {
	
    private String countryName;
    private Drawable countryFlag;

    public Country(String countryName, Drawable countryFlag){
    	this.countryName = countryName;
    	this.countryFlag = countryFlag;
    }
    
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public Drawable getCountryFlag() {
        return countryFlag;
    }
    public void setCountryFlag(Drawable countryFlag) {
        this.countryFlag = countryFlag;
    }
    
    @Override
    public boolean equals(Object obj){
    	
    	if(obj instanceof Country){
    		return this.countryName.equals(((Country) obj).getCountryName());
    	} else {
    		return false;
    	}
    	
    }
}
