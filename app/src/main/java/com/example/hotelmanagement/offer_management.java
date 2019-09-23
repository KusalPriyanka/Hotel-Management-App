package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterForPackage;
import Modal.Packages;

public class offer_management extends AppCompatActivity {

    private Button addOffer;
    private ViewPager viewPager;
    private AdapterForPackage adapterForPackage;
    private List<Packages> models;
    private DatabaseReference fb;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_management);

        addOffer = findViewById(R.id.addPackage);
        viewPager = findViewById(R.id.viewPager);

        progressDialog = new ProgressDialog(offer_management.this);

        fb = FirebaseDatabase.getInstance().getReference("RoomPackages");

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(offer_management.this,addOffer.class);
                startActivity(intent);
            }
        });

        readAll();

        adapterForPackage = new AdapterForPackage(models,offer_management.this);
        viewPager.setAdapter(adapterForPackage);
        viewPager.setPadding(130, 0, 130, 0);
    }


    private void readAll(){

        progressDialog.setTitle("Load Package Details");
        progressDialog.setMessage("Package Details Loading... Wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        models = new ArrayList<>();

        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Packages packages = postSnapshot.getValue(Packages.class);
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
