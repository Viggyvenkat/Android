package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import java.io.Serializable;

public class locationTag implements Serializable {
    private String location;

    public locationTag( String location){
        super();
        this.location = location;
    }

    public String returnlocation(){
        return location;
    }

    public void setlocation(String location){
        this.location = location;
    }
}
