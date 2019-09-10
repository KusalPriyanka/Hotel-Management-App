package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MM_Main_Meals extends AppCompatActivity {

    Button mainMeals, pastryShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__main__meals);

        mainMeals = findViewById(R.id.mainMeal);
        mainMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_Main_Meals.this, MM_Main_Meals.class);
                startActivity(intent);
            }
        });

        pastryShop = findViewById(R.id.pastryShop);
        pastryShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_Main_Meals.this, MM_Pastry_Shop.class);
                startActivity(intent);
            }
        });


    }


}
