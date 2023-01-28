package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import java.io.Serializable;

public class personTag implements Serializable {
    private String person;

    public personTag( String person){
        super();
        this.person = person;
    }

    public String returnPerson(){
        return person;
    }

    public void setPerson(String person){
        this.person = person;
    }

}
