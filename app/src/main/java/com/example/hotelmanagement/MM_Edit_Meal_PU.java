package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modal.MainMeals;


public class MM_Edit_Meal_PU extends AppCompatActivity {
    private DatabaseReference df;
    private EditText mealName, foodType, normalPrice, largePrice;
    private CheckBox breakfast, lunch, dinner;
    private Button editDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__edit__meal__pu);






    }

    public void updateMainMealDetails(){
        MainMeals mainMeals = new MainMeals();
        mainMeals.setId("MM-01");
        mainMeals.setMealName(mealName.getText().toString());
        mainMeals.setType(foodType.getText().toString());
        mainMeals.setNormalPrice(Float.parseFloat(normalPrice.getText().toString()));
        mainMeals.setLargePrice(Float.parseFloat(largePrice.getText().toString()));
        mainMeals.setBrakfast(breakfast.isChecked());
        mainMeals.setLunch(lunch.isChecked());
        mainMeals.setDinner(dinner.isChecked());

        df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child("MM-01");
        df.setValue(mainMeals).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_Edit_Meal_PU.this, MM_MealManagement.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Data Not Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_Edit_Meal_PU.this, MM_MealManagement.class);
                    startActivity(intent);
                }

            }
        });


    }
}
