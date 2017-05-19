package com.example.kirill.javagallery.logic.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ImageLoader extends AsyncTask<String ,Void, Bitmap>{
    private ImageView imageView;
    private Toast internetError;

    public ImageLoader(ImageView imageView, Toast internetError) {
        this.imageView = imageView;
        this.internetError = internetError;
    }

    @Override
    protected Bitmap doInBackground(String... params) {;
        Bitmap bitmap = null;
        try{

            HttpURLConnection httpURLConnection= (HttpURLConnection) new URL(params[0]).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();

            bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());



        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Error","MalformedURLException");
            internetError.setText("Неправильный адрес (URL)");
            internetError.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e("Error","IOException");
            internetError.show();
        }
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

    }
}
