package com.example.kirill.javagallery.ui;

import android.content.Context;

import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.kirill.javagallery.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ListAdapter extends ArrayAdapter<Integer> {
    private Context context;
    private Integer[] image;

    public ListAdapter(Context context, Integer[] image) {
        super(context, R.layout.list,image);
        this.context = context;
        this.image = image;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.list, parent, false);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.imageItem);
        imageView.setImageResource(image[position]);

        return rowView;
    }
}
