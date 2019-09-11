package com.example.hotelmanagement;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CardView reservation;
    CardView mainMeals;
    CardView eventMH;
    CardView tpart;
    TextView textView18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reservation = findViewById(R.id.reservation);


        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,reservation_s1.class);
                startActivity(intent);
            }
        });

        mainMeals = findViewById(R.id.mainMeals);

        mainMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MM_Main_Meals.class);
                startActivity(intent);
            }
        });

        eventMH = findViewById(R.id.eventManagement);
        eventMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EM_SelectionPage.class);
                startActivity(intent);
            }
        });

        tpart = findViewById(R.id.tpart);
        tpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,travel_option.class);
                startActivity(intent);
            }
        });

            }


    }

