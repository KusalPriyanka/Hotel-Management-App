package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modal.Customer;
import Modal.MainMeals;


public class MM_Main_Meals extends AppCompatActivity {

    CardView cv1,cv2,cv3,cv4,cv5,cv6;
    TextView name,price,text1, text2;
    ImageView im;
    Button mainMeals, pastryShop;
    private RecyclerView mainMeal;
    private DatabaseReference df;
    List<MainMeals> mealsLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__main__meals);


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





    }


}




