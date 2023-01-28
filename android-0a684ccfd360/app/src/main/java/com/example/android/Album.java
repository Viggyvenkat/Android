package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import java.io.*;
import java.util.ArrayList;

public class Album implements Serializable {
    public ArrayList<Photo> photos = new ArrayList<Photo>(); //list of picture objects in the album
    public static final String storeDir = "dat"; //tbh idk what this is

    public String name;

    public static int numberOfAlbums = 1;

    public Album(String name){
        numberOfAlbums++;
        this.name = name;
    }   

    public ArrayList<String> getPhotoURI(){
        ArrayList<String> names = new ArrayList<>();
        for(Photo photo : photos){
            names.add(photo.URI);
        }
        return names;
    }

    public void writeAlbum(Album albumA) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + "album" + numberOfAlbums +".dat"));
        oos.writeObject(albumA);
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }
}
