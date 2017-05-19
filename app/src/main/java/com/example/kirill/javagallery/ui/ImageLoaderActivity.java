package com.example.kirill.javagallery.ui;


import android.content.DialogInterface;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kirill.javagallery.R;
import com.example.kirill.javagallery.logic.imageload.ImageLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



public class ImageLoaderActivity extends AppCompatActivity {
    private ImageView imageView;
    private Toast internetError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//убирает статус бар
        LinearLayout mainLayout  = new LinearLayout(getApplication());
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setBackgroundResource(R.drawable.fon_gradient);
        setContentView(mainLayout);
        internetError = Toast.makeText(this,"Отсутствует подключение к серверю, проверьте интернет соединение!!!",Toast.LENGTH_LONG);
        imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.planet);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
        imageView.setPadding((int)(16*density),(int)(16*density),(int)(16*density),(int)(16*density));

        mainLayout.addView(imageView);
        ImageLoader imageLoader = new ImageLoader(imageView, internetError);
        imageLoader.execute(getIntent().getStringExtra("URL"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_loader_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final EditText editText = new EditText(getApplicationContext());
        editText.setTextColor(getResources().getColor(R.color.colorBlack));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (item.getItemId()){
            case  R.id.item2 :
                builder.setTitle("Введите адрес(URL) изображения.")
                        .setIcon(R.drawable.planet)
                        .setView(editText)
                        .setCancelable(false)
                        .setPositiveButton("Загрузить изображение", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ImageLoader imageLoader = new ImageLoader(imageView, internetError);
                                imageLoader.execute(editText.getText().toString());
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case  R.id.item1 :
                builder.setTitle("Введите название файла")
                        .setIcon(R.drawable.save_image)
                        .setView(editText)
                        .setCancelable(false)
                        .setPositiveButton("Загрузить изображение", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(saveImage(editText.getText().toString()) != true){
                                    Toast toast = Toast.makeText(getApplicationContext(), "Веден неправилный формат изображения ", Toast.LENGTH_LONG);
                                    toast.show();
                                }

                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog save = builder.create();
                save.show();
                break;

            default: Log.e("menuItem","OutItemIndex");
                break;
        }
        return true;
    }


    private  boolean saveImage(String name) {
        OutputStream os = null;
        try {
            String format = getIntent().getStringExtra("URL").substring(getIntent().getStringExtra("URL").lastIndexOf("."));
            File image = new File(Environment.getExternalStorageDirectory(),"/Download/".concat(name.concat(format)));
            os = new BufferedOutputStream(new FileOutputStream(image));
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            Log.e("format",format);
            switch (format) {
                case ".jpg":
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    Log.e("CompressFormat","JPEG");
                    break;
                case ".png":
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, os);
                    Log.e("CompressFormat","png");
                    break;
                default:
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    Log.e("CompressFormat","default jpg");
                    break;
            }
            os .close();
            Toast toast = Toast.makeText(getApplicationContext(),"Изображение загружено", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();

            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
