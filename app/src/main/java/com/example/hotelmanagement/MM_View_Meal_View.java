package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import Modal.MainMeals;


public class MM_View_Meal_View extends AppCompatActivity {
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__view__meal__view);
        test = findViewById(R.id.test);

        Intent intent = getIntent();
        MainMeals mainMeals = (MainMeals)intent.getSerializableExtra("MainMeals");

        test.setText(mainMeals.getId());

    }
}
