package com.example.android;
//Blaise Willson bw328, Vignesh Venkat vvv11
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Photo> {

    ListViewAdapter(Context context, ArrayList<Photo> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Photo currentItem = getItem(position);

        ImageView picture = listItem.findViewById(R.id.IvPicture);
        picture.setImageURI(Uri.parse(currentItem.URI));

        return listItem;
    }

}
