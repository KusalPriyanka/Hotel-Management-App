package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EM_EventManagementHome extends AppCompatActivity {
    Button viewmorebtn;
    ImageView backTosel;
    Button wedding, events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__event_management_home);
        viewmorebtn = findViewById(R.id.viewhalls);

        wedding = findViewById(R.id.wedding);
        events = findViewById(R.id.events);


        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_EventManagementHome.this,EM_EventManagementHome.class);
                startActivity(intent);
            }
        });


        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_EventManagementHome.this,EM_EventView.class);
                startActivity(intent);
            }
        });



        viewmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_EventManagementHome.this,EM_HallView.class);
                startActivity(intent);
            }
        });

        backTosel = findViewById(R.id.emback1);
        backTosel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_EventManagementHome.this, EM_SelectionPage.class);
                startActivity(intent);
            }
        });


    }
}

