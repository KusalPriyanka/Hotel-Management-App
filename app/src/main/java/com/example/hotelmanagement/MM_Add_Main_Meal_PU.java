package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modal.MainMeals;

public class MM_Add_Main_Meal_PU extends AppCompatActivity {
    private Button addButton;
    private EditText mealName, foodType, normalPrice, largePrice;
    private CheckBox breakfast, lunch, dinner;
    private DatabaseReference fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__add__main__meal__pu);
        addButton = (Button) findViewById(R.id.addMealDB);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insetDataToDb();
                /*Intent intent = new Intent(MM_Add_Main_Meal_PU.this, MM_MealManagement.class);
                startActivity(intent);*/
            }
        });


    }

    public void insetDataToDb(){
        mealName = findViewById(R.id.mealName);
        foodType = findViewById(R.id.mealType);
        normalPrice = findViewById(R.id.normalPrice);
        largePrice = findViewById(R.id.largePrice);
        breakfast = findViewById(R.id.brakfast);
        lunch = findViewById(R.id.lunch);
        dinner = findViewById(R.id.dinner);

        MainMeals mainMeals = new MainMeals();
        mainMeals.setMealName(mealName.getText().toString());
        mainMeals.setType(mealName.getText().toString());
        mainMeals.setNormalPrice(Float.parseFloat(normalPrice.getText().toString()));
        mainMeals.setLargePrice(Float.parseFloat(largePrice.getText().toString()));
        mainMeals.setBrakfast(breakfast.isChecked());
        mainMeals.setLunch(lunch.isChecked());
        mainMeals.setDinner(dinner.isChecked());

        System.out.println(mainMeals.getMealName());
        System.out.println(mainMeals);

        fb = FirebaseDatabase.getInstance().getReference().child("MainMeals");


    }
}
