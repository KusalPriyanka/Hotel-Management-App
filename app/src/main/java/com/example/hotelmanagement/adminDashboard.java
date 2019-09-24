package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class adminDashboard extends AppCompatActivity {

    private CardView reservation;
    private CardView mainMeals;
    private CardView eventMH;
    private CardView tpart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        reservation = findViewById(R.id.reservation);
        mainMeals = findViewById(R.id.mainMeals);
        eventMH = findViewById(R.id.eventManagement);
        tpart = findViewById(R.id.tpart);

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminDashboard.this,offer_management.class));
            }
        });


    }
}
