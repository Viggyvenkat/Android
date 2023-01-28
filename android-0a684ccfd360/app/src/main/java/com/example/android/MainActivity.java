package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    private Button openAlbumB;
    private Button deleteAlbumB;
    private Button renameAlbumB;
    private Button createAlbumB;
    private ListView listView;
    private EditText editTextPersonName;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    public static Album currentAlbum;
    public static ArrayList<Album> arrayAlbum = new ArrayList<>();

    Context obj = this;

    public static void serializeAlbums(Context obj){
        try{
            System.out.println("hi");
            File file = new File(obj.getFilesDir()+"user.ser");
            file.delete();
            FileOutputStream fop = new FileOutputStream(obj.getFilesDir()+"user.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fop);
            oos.writeObject(arrayAlbum);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeAlbumList(Context obj){
        System.out.println(arrayAlbum.toString());
        try {
            FileInputStream fis = new FileInputStream(obj.getFilesDir()+"user.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            arrayAlbum = (ArrayList<Album>) ois.readObject();
            Log.d("serial", String.valueOf(arrayAlbum.size()));


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(arrayAlbum.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(new File(obj.getFilesDir()+"user.ser").isFile()) {
            makeAlbumList(obj);
        }
        Log.d("serial", String.valueOf(arrayAlbum.size()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        list = new ArrayList<String>();

        for(Album a: arrayAlbum){
            list.add(a.name);
        }

        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Trying to create new Album
        createAlbumB = (Button) findViewById(R.id.createAlbumButton);
        createAlbumB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editTextPersonName.getText().toString();
                if(!list.contains(value)) {
                    list.add(value);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    arrayAlbum.add(new Album(value));
                    serializeAlbums(obj);
                }
            }
        });

        //listview stuff
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public String item;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = (String) adapterView.getItemAtPosition(i).toString();
                //Trying to delete selected Album
                deleteAlbumB = (Button) findViewById(R.id.deleteAlbumButton);
                currentAlbum = arrayAlbum.get(i);

                deleteAlbumB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < list.size(); i++){
                            String value = item;
                            if(list.get(i).equals(value)){
                                list.remove(i);
                                arrayAlbum.remove(i);
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                        serializeAlbums(obj);
                    }
                });
                //rename album code
                renameAlbumB = (Button) findViewById(R.id.renameAlbumButton);
                renameAlbumB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        String toChange = item;
                        String value = editTextPersonName.getText().toString();
                        list.set(i, value);
                        adapter.notifyDataSetChanged();
                        arrayAlbum.get(i).name = value;
                        serializeAlbums(obj);
                    }
                });
                //change to album

                openAlbumB = (Button) findViewById(R.id.openAlbumButton);
                openAlbumB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openAlbumView();
                    }
                });
            }
        });

    }

    public void openAlbumView(){
        Intent intent = new Intent(this, AlbumView.class);
        startActivity(intent);
    }

}