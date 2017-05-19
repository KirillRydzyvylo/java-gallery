package com.example.kirill.javagallery.ui.fragments;

import android.Manifest;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.kirill.javagallery.R;
import com.example.kirill.javagallery.logic.file.FileDirectoryArrayList;
import com.example.kirill.javagallery.logic.SettingsStore;
import com.example.kirill.javagallery.ui.newview.BitmapImageView;

import java.util.ArrayList;
import java.util.List;


public class VerticalMainFragment extends Fragment {
    private final int STORAGE_PERMISSION_CODE = 23;

    private final String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private View view;
    private int disposition = 0 ;
    private final String [] uris = {"/DCIM/Camera","/Download"};
    private ArrayList<Uri> uri = new ArrayList<>();
    private LinearLayout mainLinearLayout;
    private float density;


    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vertical_main_fragment, container, false);
        mainLinearLayout = (LinearLayout) view.findViewById(R.id.fragment_linear_layout);

        SettingsStore settingsStore = new SettingsStore(view.getContext());
        disposition = settingsStore.getDisposition();
        density = view.getContext().getResources().getDisplayMetrics().density;
        Log.e("MainFragment",Integer.toString(disposition));

        if(checkPermissions()){
            readImages();

            scrollView = (ScrollView)view.findViewById(R.id.scroll_view);
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

                int oldY = 0 ;
                int i = 5*(disposition);

                @Override
                public void onScrollChanged() {
                    Log.e("addImage",""+ uri.size()+" "+i);
                    if(oldY < scrollView.getScrollY()){

                        oldY +=(int)(density * (100*(4-disposition)));
                            LinearLayout linearLayout = new LinearLayout(view.getContext());
                            for(int l = 0 ; l < disposition ; l ++) {
                                if(i<uri.size()){

                                    BitmapImageView bitmapImageView = new BitmapImageView(getActivity(), uri.get(i), disposition);
                                    linearLayout.addView(bitmapImageView);
                                    i++;
                                }
                            }
                        mainLinearLayout.addView(linearLayout);
                    }

                }
            });
        }
        return view;
    }



    private void readImages(){
        uri = new FileDirectoryArrayList().getUriList(uris);

        LinearLayout linearLayout = new LinearLayout(view.getContext());

        for(int i = 0 ; i < (5*(disposition))  ; i++){
            Log.e("i%(disposition)",""+(i % (disposition)));
            if(i % (disposition) == 0){
                mainLinearLayout.addView(linearLayout);
                linearLayout = new LinearLayout(view.getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                Log.e("newLinearLayout",""+i);
            }

            BitmapImageView bitmapImageView = new BitmapImageView(getActivity(),uri.get(i),disposition);
            linearLayout.addView(bitmapImageView);
        }
        mainLinearLayout.addView(linearLayout);
    }




    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(view.getContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), STORAGE_PERMISSION_CODE);
            return false;
        }
        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.e("Permissions","Permissons");
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                    readImages();
                    getActivity().recreate();
                    Log.e("Permissions","YES");
                } else {
                    // no permissions granted.
                    Log.e("Permissions","NO");
                    getActivity().recreate();
                }
                return;
            }
        }


    }
}
