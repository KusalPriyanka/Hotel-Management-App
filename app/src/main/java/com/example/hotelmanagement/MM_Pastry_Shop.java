package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterForPackage;
import Modal.MainMeals;
import Modal.ShortEats;
import Util.DrinksAdapter;
import Util.PizzaAdapter;
import Util.ShortEatsAdapter;

public class MM_Pastry_Shop extends AppCompatActivity {
    private ViewPager pastryVP, pizzaVP, drinksVP;
    Button mainMeals, pastryShop;
    private DatabaseReference df;
    private List<ShortEats> mealsLists = new ArrayList<>();
    private List<ShortEats> pizzaList = new ArrayList<>();
    private List<ShortEats> drinkslist = new ArrayList<>();
    ViewFlipper vf;
    private ProgressDialog pd;
    private ShortEatsAdapter shortEatsAdapter;
    private PizzaAdapter pizzaAdapter;
    private DrinksAdapter drinksAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__pastry__shop);

        mainMeals = findViewById(R.id.mainMeal);
        mainMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_Pastry_Shop.this, MM_Main_Meals.class);
                startActivity(intent);
            }
        });

        pastryShop = findViewById(R.id.pastryShop);
        pastryShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_Pastry_Shop.this, MM_Pastry_Shop.class);
                startActivity(intent);
            }
        });

        pastryVP = findViewById(R.id.pastry);
        pizzaVP = findViewById(R.id.pizza);
        drinksVP = findViewById(R.id.drinks);
        pd = new ProgressDialog(MM_Pastry_Shop.this);
        pd.setTitle("Welcome to Blue Dragon Dining");
        pd.setMessage("Feel The Taste Of Heaven");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        df = FirebaseDatabase.getInstance().getReference().child("ShortEats");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealsLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ShortEats shortEats = ds.getValue(ShortEats.class);
                    if(shortEats.isPastry() == true){
                        mealsLists.add(shortEats);
                    }
                }

               shortEatsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        shortEatsAdapter = new ShortEatsAdapter(mealsLists,MM_Pastry_Shop.this);
        pastryVP.setAdapter(shortEatsAdapter);
        pastryVP.setPadding(130, 0, 130, 0);



        df = FirebaseDatabase.getInstance().getReference().child("ShortEats");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pizzaList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ShortEats shortEats = ds.getValue(ShortEats.class);
                    if(shortEats.isPizza() == true){
                        pizzaList.add(shortEats);
                    }
                }

                pizzaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pizzaAdapter = new PizzaAdapter(pizzaList,MM_Pastry_Shop.this);
        pizzaVP.setAdapter(pizzaAdapter);
        pizzaVP.setPadding(130, 0, 130, 0);



        df = FirebaseDatabase.getInstance().getReference().child("ShortEats");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drinkslist.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ShortEats shortEats = ds.getValue(ShortEats.class);
                    if(shortEats.isDrinks() == true){
                        drinkslist.add(shortEats);
                    }
                }

                drinksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        drinksAdapter = new DrinksAdapter(drinkslist,MM_Pastry_Shop.this);
        drinksVP.setAdapter(drinksAdapter);
        drinksVP.setPadding(130, 0, 130, 0);
        pd.dismiss();

    }



}
