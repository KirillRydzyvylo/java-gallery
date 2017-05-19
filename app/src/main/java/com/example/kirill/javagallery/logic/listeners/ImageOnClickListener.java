package com.example.kirill.javagallery.logic.listeners;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.example.kirill.javagallery.ui.IncreasedActivity;



public class ImageOnClickListener implements View.OnClickListener {
    Uri uri;
    Activity activity;
    public ImageOnClickListener(Activity activity, Uri uri) {
        this.uri = uri;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Log.e("click",uri.toString());
        Intent intent = new Intent(activity , IncreasedActivity.class);
        intent.putExtra("Uri",uri.toString());
        Log.e("Uri",uri.toString());
        activity.startActivity(intent);
    }
}
