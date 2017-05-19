package com.example.kirill.javagallery.ui;



import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kirill.javagallery.R;
import com.example.kirill.javagallery.logic.SettingsStore;



public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//убирает статус бар
        setContentView(R.layout.activity_main);
        activity = this;
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorNull));
        Integer[] images = {R.drawable.horizontal1, R.drawable.horizontal2, R.drawable.horizontal3};
        listView = (ListView) findViewById(R.id.list);
        ListAdapter listAdapter = new ListAdapter(getApplicationContext(), images);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingsStore settingsStore = new SettingsStore(getApplicationContext());
                settingsStore.saveDisposition(position+1);
                Log.e("settingsStore", Integer.toString(position+1));
                activity.recreate();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final EditText editText = new EditText(getApplicationContext());
        editText.setTextColor(getResources().getColor(R.color.colorBlack));
        switch (item.getItemId()){
            case  R.id.item1 :
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Введите адрес(URL) изображения.")
                        .setIcon(R.drawable.planet)
                        .setView(editText)
                        .setCancelable(false)
                        .setPositiveButton("Загрузить изображение", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(MainActivity.this,ImageLoaderActivity.class);
                                Log.e("URL",editText.getText().toString());
                                intent.putExtra("URL",editText.getText().toString());
                                startActivity(intent);

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
            default: Log.e("menuItem","OutItemIndex");
                break;
        }
        return true;
    }


}