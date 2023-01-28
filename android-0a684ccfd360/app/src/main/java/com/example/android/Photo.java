package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Photo implements Serializable {


    public List<personTag> persontags = new ArrayList<personTag>();
    public List<locationTag> locationTags = new ArrayList<locationTag>();
    public List<String> allTags = new ArrayList<String>();

    public String URI;

    public Photo(String URI){
        this.URI = URI;
    }


    public void addPersonTag(String tag){
        persontags.add(new personTag(tag));
    }

    public void addLocationTag(String tag){
        locationTags.add(new locationTag(tag));
    }

    public String getURI(){
        return URI;
    }

    public List getPersonTags(){
        return persontags;
    }

    public List getLocationTags(){
        return locationTags;
    }

}
