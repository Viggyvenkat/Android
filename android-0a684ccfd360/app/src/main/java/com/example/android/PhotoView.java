package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoView extends AppCompatActivity implements Serializable {
    private Button addPersonB;
    private Button addLocationB;
    private Button deleteTagB;
    private EditText tagText;
    private ImageView imageDisplay;
    private ListView lisTags;
    private Button moveP;
    private EditText movinText;

    Context obj = this;

    ArrayAdapter<String> adapter;

    Photo currphoto = AlbumView.currentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        imageDisplay = (ImageView) findViewById(R.id.imageViewDisplayPhoto);
        imageDisplay.getLayoutParams().height = 300;
        imageDisplay.requestLayout();

        tagText = (EditText) findViewById(R.id.tagText);
        movinText = (EditText) findViewById(R.id.albumToBeMoved);
        lisTags = (ListView) findViewById(R.id.listViewTags);

        imageDisplay.setImageURI(Uri.parse(currphoto.getURI()));

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, currphoto.allTags);
        lisTags.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //creating location tag
        addLocationB = (Button) findViewById(R.id.addLocationTagB);
        addLocationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = tagText.getText().toString();
                currphoto.allTags.add(value);
                currphoto.addLocationTag(value);
                lisTags.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                MainActivity.serializeAlbums(obj);
            }
        });
        //creating person tag
        addPersonB = (Button) findViewById(R.id.addPersonTagB);
        addPersonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = tagText.getText().toString();
                currphoto.allTags.add(value);
                currphoto.addPersonTag(value);
                lisTags.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                MainActivity.serializeAlbums(obj);
            }
        });
        //movin thing
        moveP = (Button) findViewById(R.id.moveButton);
        moveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = movinText.getText().toString();
                for(Album a : MainActivity.arrayAlbum){
                    if(a.name.equals(value)){
                        a.photos.add(currphoto);
                        MainActivity.currentAlbum.photos.remove(currphoto);
                    }
                }
                MainActivity.serializeAlbums(obj);
            }
        });
        //creating a listener for the listview
        lisTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public String item;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = (String) adapterView.getItemAtPosition(i).toString();
                //Trying to delete selected Album
                deleteTagB = (Button) findViewById(R.id.deleteTagButton);

                deleteTagB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < currphoto.allTags.size(); i++){
                            String value = item;
                            if(currphoto.allTags.get(i).equals(value)){
                                currphoto.allTags.remove(i);
                                String remove = currphoto.allTags.get(i);
                                if(currphoto.locationTags.contains(remove)){
                                    currphoto.locationTags.remove(remove);
                                }
                                if(currphoto.persontags.contains(remove)){
                                    currphoto.persontags.remove(remove);
                                }
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                        MainActivity.serializeAlbums(obj);
                    }
                });
            }
        });
    }
}