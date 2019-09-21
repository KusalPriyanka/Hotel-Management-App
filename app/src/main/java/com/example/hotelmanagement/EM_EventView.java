package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modal.EM_HallManagement;
import Modal.WedHallList;

public class EM_EventView extends AppCompatActivity {

    ViewFlipper vievfliper;
    private ProgressBar eventViewPro;
    private DatabaseReference df;
    private List<EM_HallManagement> hallList = new ArrayList<>();
    private ListView hallistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__event_view);
        hallistView = findViewById(R.id.hallList);
        eventViewPro = findViewById(R.id.eventViewPro);
        hallistView = findViewById(R.id.eventList);

        vievfliper = findViewById(R.id.file);
        int image[] = {R.drawable.emnewfiv, R.drawable.emnewone, R.drawable.emnewtwo,R.drawable.emfirstsix};
        for(int i = 0; i < image.length; i++){
            showSlider(image[i]);
        }




    }




    public void showSlider(int image){
        ImageView im = new ImageView(this);
        im.setBackgroundResource(image);
        vievfliper.setAutoStart(true);
        vievfliper.setFlipInterval(3000);
        vievfliper.addView(im);


        vievfliper.setInAnimation(this, android.R.anim.slide_in_left);
        vievfliper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventViewPro.setVisibility(View.VISIBLE);
        df = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hallList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    EM_HallManagement em_hallManagement = ds.getValue(EM_HallManagement.class);
                    if(em_hallManagement.isEvents() == true){
                        hallList.add(em_hallManagement);
                    }
                }

                WedHallList wedHallList = new WedHallList(EM_EventView.this, hallList);

                hallistView.setAdapter(wedHallList);
                eventViewPro.setVisibility(View.GONE);

                /*hallistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MainMeals mainMeals = (MainMeals) adapterView.getAdapter().getItem(i);




                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
