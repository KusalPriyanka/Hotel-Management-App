package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

public class MM_Main_Meal_View_PU extends AppCompatActivity {

    ImageView iv;
    TextView mealName, nPrice, lPrice, tFood, d1, d2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__main__meal__view__pu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String n_meal_price = intent.getStringExtra("normalMealPrice");
        String l_meal_price = intent.getStringExtra("Large_mealPrice");
        String foodType = intent.getStringExtra("foodType");
        String text1 = intent.getStringExtra("meal_txt1");
        String text2 = intent.getStringExtra("meal_txt2");



        Bundle bundle = this.getIntent().getExtras();
        int pic = bundle.getInt("image");
        iv = findViewById(R.id.image1);
        iv.setImageResource(pic);

        mealName = findViewById(R.id.mealName);
        nPrice = findViewById(R.id.regularPrice);
        lPrice = findViewById(R.id.largePrice);
        d1 = findViewById(R.id.txt3);
        d2 = findViewById(R.id.txt4);
        tFood = findViewById(R.id.foodType);

        mealName.setText(name);
        nPrice.setText(n_meal_price);
        lPrice.setText(l_meal_price);
        d1.setText(text1);
        d2.setText(text2);
        tFood.setText(foodType);
    }
}
