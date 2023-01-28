package com.example.android;

//Blaise Willson bw328, Vignesh Venkat vvv11

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class SlideShow extends AppCompatActivity {
    private Button left;
    private Button right;
    private ImageView slide;

    Photo curim;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        left = (Button) findViewById(R.id.leftMove);
        right = (Button) findViewById(R.id.rightMove);

        slide = (ImageView) findViewById(R.id.slide);
        slide.getLayoutParams().height = 300;
        slide.requestLayout();

        ArrayList<Photo> cur = MainActivity.currentAlbum.photos;
        curim = cur.get(0);
        i = 0;
        slide.setImageURI(Uri.parse(curim.getURI()));

        //left
        left = (Button) findViewById(R.id.leftMove);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i > 0){
                    curim = cur.get(i-1);
                    slide.setImageURI(Uri.parse(curim.getURI()));
                    i--;
                }
            }
        });
        //right
        right = (Button) findViewById(R.id.rightMove);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i < cur.size()-1){
                    curim = cur.get(i+1);
                    slide.setImageURI(Uri.parse(curim.getURI()));
                    i++;
                }
            }
        });

    }
}