package com.example.hotelmanagement;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;




import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;

import android.widget.CheckedTextView;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import Modal.MainMeals;
import Modal.MealList;



public class MM_Main_Meals extends AppCompatActivity {



    private Button mainMeals, pastryShop;
    private DatabaseReference df;
    private List<MainMeals> mealsLists;
    private Dialog myDialog5;
    private TextView  name, type, lprice, nprice;
    private CheckedTextView br,lu, dn;
    private ListView lv;
    private ImageView image, back;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__main__meals);

        mealsLists = new ArrayList<MainMeals>();
        myDialog5 = new Dialog(this);

        lv = (ListView) findViewById(R.id.mmList);


        pastryShop = findViewById(R.id.pastryShop);
        pastryShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_Main_Meals.this, MM_Pastry_Shop.class);
                startActivity(intent);
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MM_Main_Meals.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //creating the adapter view in on create
        pd = new ProgressDialog(MM_Main_Meals.this);
        pd.setTitle("Welcome to Blue Dragon Dining");
        pd.setMessage("Feel The Taste Of Heaven");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealsLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    MainMeals mainMeals = ds.getValue(MainMeals.class);
                    mealsLists.add(mainMeals);
                }

                MealList mealList = new MealList(MM_Main_Meals.this, mealsLists);

                lv.setAdapter(mealList);
                pd.dismiss();

                //creating the view popup in here
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MainMeals mainMeals =(MainMeals)adapterView.getAdapter().getItem(i);

                        myDialog5.setContentView(R.layout.activity_mm__main__meal__view__pu);
                        myDialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        myDialog5.show();

                        name = myDialog5.findViewById(R.id.mealName);
                        nprice = myDialog5.findViewById(R.id.regularPrice);
                        lprice = myDialog5.findViewById(R.id.largePrice);
                        br = myDialog5.findViewById(R.id.brakfast);
                        lu = myDialog5.findViewById(R.id.lunch);
                        dn = myDialog5.findViewById(R.id.dinner);
                        type = myDialog5.findViewById(R.id.foodType);
                        image = myDialog5.findViewById(R.id.image1);

                        name.setText(mainMeals.getMealName());
                        type.setText(mainMeals.getType());
                        nprice.setText("RS - " +mainMeals.getNormalPrice() + "0");
                        lprice.setText("RS - " + mainMeals.getLargePrice() + "0");
                        if(mainMeals.isBrakfast() == true){
                            br.setCheckMarkDrawable(R.drawable.check_view);
                        }
                        if(mainMeals.isLunch() == true){
                            lu.setCheckMarkDrawable(R.drawable.check_view);
                        }
                        if(mainMeals.isDinner() == true){
                            dn.setCheckMarkDrawable(R.drawable.check_view);
                        }
                        Glide.with(MM_Main_Meals.this).load(mainMeals.getImageName()).into(image);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




