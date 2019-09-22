package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modal.EM_HallManagement;
import Modal.MainMeals;
import Modal.MealList;
import Modal.WedHallList;

public class EM_HallView extends AppCompatActivity {
    private ProgressBar hallViewPro;
    private DatabaseReference df;
    private List<EM_HallManagement> hallList = new ArrayList<>();
    private ListView hallistView;
    private Button wedding, events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__hall_view);
        hallistView = findViewById(R.id.hallList);
        hallViewPro = findViewById(R.id.hallViewPro);
        wedding = findViewById(R.id.wedding);
        events = findViewById(R.id.events);


        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_HallView.this,EM_EventManagementHome.class);
                startActivity(intent);
            }
        });


        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_HallView.this,EM_EventView.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        hallViewPro.setVisibility(View.VISIBLE);
        df = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hallList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    EM_HallManagement em_hallManagement = ds.getValue(EM_HallManagement.class);
                    if(em_hallManagement.isWedding() == true){
                        hallList.add(em_hallManagement);
                    }
                }

                WedHallList wedHallList = new WedHallList(EM_HallView.this, hallList);

                hallistView.setAdapter(wedHallList);
                hallViewPro.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}






