package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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


import java.util.ArrayList;
import java.util.List;

import Modal.Customer;
import Modal.MainMeals;
import Modal.MealList;
import ViewHolder.MainMealsViewHolder;


public class MM_Main_Meals extends AppCompatActivity {


    ImageView im;
    Button mainMeals, pastryShop;
    private RecyclerView mainMeal;
    private DatabaseReference df;
    List<MainMeals> mealsLists;
    RecyclerView mainMealsRV;
    FirebaseRecyclerAdapter<MainMeals, MainMealsViewHolder> adapter;
    private ProgressBar progressBar;
    Dialog myDialog5;
    private CheckBox breakfast, lunch, dinner;
    TextView ID, name, type, lprice, nprice, headerDeletePU;
    CheckedTextView br,lu, dn;

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__main__meals);

        mealsLists = new ArrayList<MainMeals>();
        myDialog5 = new Dialog(this);

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
        progressBar = findViewById(R.id.pro);
        progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.GONE);

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



                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




