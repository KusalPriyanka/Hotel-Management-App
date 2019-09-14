package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Modal.Customer;
import Modal.MainMeals;
import Modal.MealList;
import ViewHolder.MainMealsViewHolder;


public class MM_Main_Meals extends AppCompatActivity {

    CardView cv1,cv2,cv3,cv4,cv5,cv6;
    TextView name,price,text1, text2;
    ImageView im;
    Button mainMeals, pastryShop;
    private RecyclerView mainMeal;
    private DatabaseReference df;
    List<MainMeals> mealsLists;
    RecyclerView mainMealsRV;
    FirebaseRecyclerAdapter<MainMeals, MainMealsViewHolder> adapter;
    //FirebaseRecyclerOptions <MainMeals> mainMealsop;

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__main__meals);

        mealsLists = new ArrayList<MainMeals>();

       /* mainMealsRV= findViewById(R.id.mainMealRecycleView);
        mainMealsRV.hasFixedSize();

        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");

        mainMealsop = new FirebaseRecyclerOptions.Builder<MainMeals>().setQuery(df, MainMeals.class).build();

        adapter = new FirebaseRecyclerAdapter<MainMeals, MainMealsViewHolder>(mainMealsop ) {
            @Override
            protected void onBindViewHolder(@NonNull MainMealsViewHolder mainMealsViewHolder, int i, @NonNull MainMeals mainMeals) {
                Picasso.get().load(mainMeals.getImageName()).into(mainMealsViewHolder.iv, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "Err Image", Toast.LENGTH_LONG).show();
                    }


                });

                mainMealsViewHolder.t1.setText(mainMeals.getMealName());
            }

            @NonNull
            @Override
            public MainMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mm_main_meal_recyclerview, parent, false);
                return new MainMealsViewHolder(view);
            }
        };


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        mainMealsRV.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        mainMealsRV.setAdapter(adapter);
*/



        lv = (ListView) findViewById(R.id.mmList);


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


   /* @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter.startListening();
    }*/


    @Override
    protected void onStart() {
        super.onStart();
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

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MainMeals mainMeals =(MainMeals)adapterView.getAdapter().getItem(i);
                        /*Intent intent =  new Intent(MM_MealManagement.this,  MM_View_Meal_View.class);
                        intent.putExtra("MainMeals", mainMeals);
                        startActivity(intent);*/

                        Toast.makeText(getApplicationContext(), mainMeals.getMealName()+"", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




