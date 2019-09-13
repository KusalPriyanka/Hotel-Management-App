package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Modal.EM_HallManagement;
import Util.CommonConstants;

public class EM_AddhallDetails extends AppCompatActivity {
    EditText hallName,hallPrice,hallType;
    CheckBox weddingbtn,eventbtn;
    DatabaseReference dbf;
     EM_HallManagement em;

    Button buttonAdd;
    Button viewdetails;
    CheckBox updwed,updevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__addhall_details);
       /* DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
*/
/*
        getWindow().setLayout((int)(width*.8),(int)(height*.7));
*/
        buttonAdd =findViewById(R.id.addbuttonhall);
        hallName = findViewById(R.id.hallname);
        hallPrice = findViewById(R.id.hallprice);
        hallType = findViewById(R.id.descrip);
        weddingbtn = findViewById(R.id.wedcheck);
        eventbtn = findViewById(R.id.eventcheck);




        dbf = FirebaseDatabase.getInstance().getReference("EM_HallManagement");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em = new EM_HallManagement();

                em.setName(hallName.getText().toString());
                em.setPrice(Float.parseFloat(hallPrice.getText().toString()));
                em.setDescription(hallType.getText().toString());
                em.setEvents(weddingbtn.isChecked());
                em.setWedding(eventbtn.isChecked());


                dbf.child(em.getName()).setValue(em);



                Toast.makeText(getApplicationContext(),"Data Has Been Added",Toast.LENGTH_SHORT).show();
            }
        });


        /*viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//STUDENT IS THE TABLE NAME
                String enterdValue = hallName.getText().toString();
                DatabaseReference readref = FirebaseDatabase.getInstance().getReference().child("Student").child(enterdValue);


                readref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChildren()){
                            txtid.setText(dataSnapshot.child("id").getValue().toString());
                            txtname.setText(dataSnapshot.child("name").getValue().toString());
                            txtaddress.setText(dataSnapshot.child("address").getValue().toString());


                        }else{

                            Toast.makeText(getApplicationContext(),"no source to display",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
*/







    }
}

