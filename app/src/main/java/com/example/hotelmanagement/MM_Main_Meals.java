package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Modal.Customer;


public class MM_Main_Meals extends AppCompatActivity {

    CardView cv1,cv2,cv3,cv4,cv5,cv6;
    TextView name,price,text1, text2;
    ImageView im;
    Button mainMeals, pastryShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__main__meals);

        /*mainMeals = findViewById(R.id.mainMeal);
        mainMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_Main_Meals.this, MM_Main_Meals.class);
                startActivity(intent);
            }
        });*/

        mainMeals = findViewById(R.id.mainMeal);
        mainMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_Main_Meals.this, MM_MealManagement.class);
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


        cv1 = findViewById(R.id.card1);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = findViewById(R.id.name1);
                price = findViewById(R.id.price1);
                text1 = findViewById(R.id.tb1);
                text2 = findViewById(R.id.tl1);
                String mealName = name.getText().toString();
                String nMealPrice = price.getText().toString();
                String meal_txt1 = text1.getText().toString();
                String meal_txt2 = text2.getText().toString();

                Intent intent = new Intent(MM_Main_Meals.this, MM_Main_Meal_View_PU.class);
                intent.putExtra("name", mealName);
                intent.putExtra("meal_txt1", meal_txt1);
                intent.putExtra("meal_txt2", meal_txt2);
                intent.putExtra("normalMealPrice", nMealPrice);
                intent.putExtra("Large_mealPrice", "350.00/-");
                intent.putExtra("foodType", "Chinese");


                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.mm_noodles);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });


    }


}
