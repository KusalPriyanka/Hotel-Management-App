package com.example.hotelmanagement;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Modal.Customer;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CardView reservation;
    private CardView mainMeals;
    private CardView eventMH;
    private CardView tpart;
    private TextView username;
    private CircleImageView userImg;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reservation = findViewById(R.id.reservation);
        username = findViewById(R.id.username);
        userImg = findViewById(R.id.profile_image);

        username.setText(currentUser.getDisplayName());

        Glide.with(MainActivity.this)
                .load(currentUser.getPhotoUrl())
                .into(userImg);


        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,reservation_s1.class);
                startActivity(intent);
            }
        });

        mainMeals = findViewById(R.id.mainMeals);

        mainMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MM_Main_Meals.class);
                startActivity(intent);
            }
        });

        eventMH = findViewById(R.id.eventManagement);
        eventMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EM_SelectionPage.class);
                startActivity(intent);
            }
        });

        tpart = findViewById(R.id.tpart);
        tpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("AWAAAAAAAAA >>");
                Intent intent = new Intent(MainActivity.this,TravelHome.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null){
            startActivity(new Intent(MainActivity.this,Login.class));
        }

    }
}

