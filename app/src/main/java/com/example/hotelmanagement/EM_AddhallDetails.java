package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    String id;

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

        id = CommonConstants.EM_PREFIX + CommonConstants.EH_ID;
        ++CommonConstants.EH_ID;


        dbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em = new EM_HallManagement();
                em.setId(id);
                em.setName(hallName.getText().toString());
                em.setPrice(Float.parseFloat(hallPrice.getText().toString()));
                em.setDescription(hallType.getText().toString());
                em.setEvents(weddingbtn.isChecked());
                em.setWedding(eventbtn.isChecked());


                dbf.child(em.getId()).setValue(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EM_AddhallDetails.this, EM_Addhalls.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Data Not Inserted Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EM_AddhallDetails.this, EM_Addhalls.class);
                            startActivity(intent);
                        }

                    }
                });;




            }
        });









    }
}

