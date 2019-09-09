package com.example.hotelmanagement;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CardView reservation;
    CardView mainMeals;

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

    }
}
