package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Modal.MainMeals;
import Modal.Meals;


public class MM_Edit_Meal_PU extends AppCompatActivity {
    private DatabaseReference df;
    private EditText mealName, foodType, normalPrice, largePrice;
    private CheckBox breakfast, lunch, dinner;
    private Button editDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__edit__meal__pu);

        mealName = findViewById(R.id.mealName);
        foodType = findViewById(R.id.mealType);
        normalPrice = findViewById(R.id.normalPrice);
        largePrice = findViewById(R.id.largePrice);
        breakfast = findViewById(R.id.b);
        lunch = findViewById(R.id.l);
        dinner = findViewById(R.id.d);





        df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child("MM-01");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    mealName.setText(dataSnapshot.child("mealName").getValue().toString());
                    foodType.setText(dataSnapshot.child("type").getValue().toString());
                    normalPrice.setText(dataSnapshot.child("normalPrice").getValue().toString());
                    largePrice.setText(dataSnapshot.child("largePrice").getValue().toString());
                    if((Boolean) dataSnapshot.child("brakfast").getValue() == true){
                        breakfast.setChecked(true);
                    }
                    if((Boolean)dataSnapshot.child("lunch").getValue() == true){
                        lunch.setChecked(true);
                    }
                    if((Boolean) dataSnapshot.child("dinner").getValue() == true){
                        dinner.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editDetails =(Button) findViewById(R.id.updateDetails);
        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMainMealDetails();
            }
        });


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
