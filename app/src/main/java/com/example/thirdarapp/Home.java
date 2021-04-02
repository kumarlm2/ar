package com.example.thirdarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        listView = findViewById(R.id.list);
        String[] list={"videomodel","website render","image renderer"};
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.text,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:startActivity(new Intent(Home.this,MainActivity.class));
                    break;
                    case 1: startActivity(new Intent(Home.this,website_renderer.class));
                    break;
                    case 2: startActivity(new Intent(Home.this,image_renderer.class));
                        break;
                }
            }
        });

    }
}
