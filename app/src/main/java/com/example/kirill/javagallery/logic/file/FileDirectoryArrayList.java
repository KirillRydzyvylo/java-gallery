package com.example.kirill.javagallery.logic.file;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;



public class FileDirectoryArrayList {

    public ArrayList<Uri> getUriList(String [] uris) {
        ArrayList<Uri> uriList  = new ArrayList<>();
        File dir = Environment.getExternalStorageDirectory();
        for(String uri: uris){
            File  directory  = new File(dir,uri);
            File [] imageFiles = directory.listFiles(new ImageFileFilter());

            for(int i = 0 ; i < imageFiles.length; i++){
                uriList.add(Uri.fromFile(imageFiles[i]));
            }
        }
        Log.e("file",uriList.toString());
        return uriList;
    }





    private class ImageFileFilter implements FileFilter{
        private final String[] okFileExtensions = new String[] {"jpg", "png", "gif"};


        @Override
        public boolean accept(File pathname) {
            for (String extension : okFileExtensions)
            {
                if (pathname.getName().toLowerCase().endsWith(extension))
                {
                    return true;
                }
            }
            return false;
        }
    }


}
