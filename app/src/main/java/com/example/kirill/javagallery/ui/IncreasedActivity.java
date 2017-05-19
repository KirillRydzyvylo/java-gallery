package com.example.kirill.javagallery.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.kirill.javagallery.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;



public class IncreasedActivity extends AppCompatActivity {
    private PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//убирает статус бар
        setContentView(R.layout.increased_activity);
        Uri uri = Uri.fromFile(new File(getIntent().getStringExtra("Uri").substring(6)));
        Log.e("increasedActivity",uri.toString());
        photoView = (PhotoView)findViewById(R.id.photo_view);
        photoView.setImageURI(uri);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.increased_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.item1 : photoView.setRotation(photoView.getRotation() + 90);
                     break;
            case  R.id.item2 : photoView.setRotation(photoView.getRotation() - 90);
                    break;
            default: Log.e("menuItem","OutItemIndex");
                break;
        }
        return true;
    }
}

