package com.example.kirill.javagallery.ui.newview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kirill.javagallery.logic.listeners.ImageOnClickListener;

public class BitmapImageView extends ImageView{
    private Activity activity;
    private int disposition = 0 ;


    public BitmapImageView(Activity activity, Uri uri , int disposition) {
        super(activity.getApplicationContext());

        this.activity = activity;
        this.disposition = disposition;

        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        setPadding((int)(16 * density), (int)(16 * density), (int)(16 * density), 0);
        BitmapCreatorAsyncTask bitmapCreatorAsyncTask = new BitmapCreatorAsyncTask();
        bitmapCreatorAsyncTask.execute(uri);
    }



    private class BitmapCreatorAsyncTask extends AsyncTask<Uri,Void,Bitmap> {
        private Uri uri;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setLayoutParams(new LinearLayout.LayoutParams(300*(4-disposition),300*(4-disposition),1));
        }

        @Override
        protected Bitmap doInBackground(Uri... params) {
            uri = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap resultBitmap = BitmapFactory.decodeFile( uri.getPath(),options);
            return resultBitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            setImageBitmap(bitmap);
            setOnClickListener(new ImageOnClickListener(activity,uri));
        }
    }

}
