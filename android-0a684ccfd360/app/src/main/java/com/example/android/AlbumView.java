package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumView extends AppCompatActivity implements Serializable {
    private Button displayPhotoB;
    private Button addPhotoB;
    private Button deletePhotoB;
    private Button slideShowB;
    private ListView listView;
    private SearchView searchView;
    private ImageView imageView;
    private ImageView imageView2;
    private Button searchPeople;
    private Button searchLocation;
    private EditText search;
    private ListView searchResults;
    private Button showSlide;

    private ArrayList<Photo> photos = MainActivity.currentAlbum.photos;
    ListViewAdapter arrayAdapter;
    ListViewAdapter arrayAdapter1;
    ListViewAdapter arrayAdapter2;

    Context obj = this;

    public static Photo currentPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        searchResults = (ListView) findViewById(R.id.searchResults);

        search = (EditText) findViewById(R.id.search);

        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ListViewAdapter(getApplicationContext(), photos);
        listView.setAdapter(arrayAdapter);


        //Adding Photos Subsection
        addPhotoB = (Button) findViewById(R.id.addPhotoButton);
        addPhotoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                arrayAdapter.notifyDataSetChanged();
                MainActivity.serializeAlbums(obj);
            }
        });

        //Displaying slideshow
        showSlide = (Button) findViewById(R.id.showSlide);
        showSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySlideShowView();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public Photo item;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayAdapter.notifyDataSetChanged();

                item = (Photo) adapterView.getItemAtPosition(i);
                //Trying to delete selected Album
                currentPhoto = photos.get(i);
                arrayAdapter.notifyDataSetChanged();

                //Deleting Photos Subsection
                deletePhotoB = (Button) findViewById(R.id.removePhotoButton);
                deletePhotoB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < photos.size(); i++){
                            Photo value = item;
                            if(photos.get(i).URI.equals(value.URI)){
                                Log.d("error", "whatever");
                                photos.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                                MainActivity.serializeAlbums(obj);
                                break;
                            }
                        }

                    }
                });
                //search location
                searchLocation = (Button) findViewById(R.id.searchLocation) ;
                searchLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = search.getText().toString();
                        Log.d("tags", value);
                        ArrayList<Photo> locationResults = locationSearch(value);
                        Log.d("location", String.valueOf(locationResults.size()));
                        arrayAdapter1 = new ListViewAdapter(getApplicationContext(), locationResults);
                        searchResults.setAdapter(arrayAdapter1);
                        arrayAdapter1.notifyDataSetChanged();

                    }
                });
                //search people
                searchPeople = (Button) findViewById(R.id.searchPeople) ;
                searchPeople.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = search.getText().toString();
                        Log.d("tags", value);
                        ArrayList<Photo> peopleResults = personSearch(value);
                        Log.d("location", String.valueOf(peopleResults.size()));
                        arrayAdapter2 = new ListViewAdapter(getApplicationContext(), peopleResults);
                        searchResults.setAdapter(arrayAdapter2);
                        arrayAdapter2.notifyDataSetChanged();
                    }
                });

                //Displaying Photos Subsection
                displayPhotoB = (Button) findViewById(R.id.displayPhotoButton);
                displayPhotoB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        displayPhotoView();
                    }
                });

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        listView = (ListView) findViewById(R.id.listView);
//        arrayAdapter = new ListViewAdapter(getApplicationContext(), photos);
//        listView.setAdapter(arrayAdapter);

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
//            imageView.setImageURI(selectedImage);
            photos.add(new Photo(selectedImage.toString()));
//            listView = (ListView) findViewById(R.id.listView);
//            arrayAdapter = new ListViewAdapter(getApplicationContext(), photos);
           listView.setAdapter(arrayAdapter);
           MainActivity.serializeAlbums(obj);
        }
    }

    public void displayPhotoView(){
        Intent intent = new Intent(this, PhotoView.class);
        startActivity(intent);
    }

    public void displaySlideShowView(){
        Intent intent = new Intent(this, SlideShow.class);
        startActivity(intent);
    }

    public ArrayList<Photo> search(String search){
        ArrayList<Photo> searchHits = new ArrayList<Photo>();
        for(Album a: MainActivity.arrayAlbum){
            for(Photo p: a.photos){
                for(locationTag lt: p.locationTags){
                    if(search.equals(lt.returnlocation())){
                        searchHits.add(p);
                    }
                }
                for(personTag pt: p.persontags){
                    if(search.equals(pt.returnPerson())){
                        searchHits.add(p);
                    }
                }
            }
        }
        return searchHits;
    }

    public ArrayList<Photo> personSearch(String search){
        ArrayList<Photo> searchHits = new ArrayList<Photo>();
        for(Album a: MainActivity.arrayAlbum){
            for(Photo p: a.photos){
                for(personTag pt: p.persontags){
                    if(search.equals(pt.returnPerson())){
                        searchHits.add(p);
                    }
                }
            }
        }
        return searchHits;
    }

    public ArrayList<Photo> locationSearch(String search){
        ArrayList<Photo> searchHits = new ArrayList<Photo>();
        for(Album a: MainActivity.arrayAlbum){
            for(Photo p: a.photos){
                for(locationTag lt: p.locationTags){
                    if(search.equals(lt.returnlocation())){
                        searchHits.add(p);
                    }
                }
            }
        }
        return searchHits;
    }

}