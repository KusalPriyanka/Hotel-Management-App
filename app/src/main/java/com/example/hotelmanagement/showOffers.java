package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterForPackage;
import Modal.Packages;
import Modal.Reservation;

public class showOffers extends AppCompatActivity {

    private ViewPager viewPager;
    private AdapterForPackage adapterForPackage;
    private List<Packages> models;
    private DatabaseReference fb;

    private ProgressDialog progressDialog;

    private Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offers);

        reservation = (Reservation) getIntent().getSerializableExtra("Res");

        viewPager = findViewById(R.id.viewPager);
        progressDialog = new ProgressDialog(showOffers.this);

        readAll();

        adapterForPackage = new AdapterForPackage(models,showOffers.this);
        adapterForPackage.setIntent(getIntent());

        viewPager.setAdapter(adapterForPackage);
        viewPager.setPadding(130, 0, 130, 0);

    }

    private void readAll(){

        progressDialog.setTitle("Load Package Details");
        progressDialog.setMessage("Package Details Loading... Wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        models = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference("RoomPackages")
                .orderByChild("people")
                .startAt(reservation.getNoOfAdults());


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Packages packages = postSnapshot.getValue(Packages.class);
                    packages.setId(postSnapshot.getKey());
                    models.add(packages);
                }

                adapterForPackage.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
