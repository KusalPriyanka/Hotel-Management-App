package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EM_AddhallDetails extends AppCompatActivity {
    EditText hallName,hallPrice,hallType;
    CheckBox weddingbtn,eventbtn;
    DatabaseReference dbf;
    EM_HallManagement em;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__addhall_details);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));
        button =findViewById(R.id.addbuttonhall);
        hallName = findViewById(R.id.hallname);
        hallPrice = findViewById(R.id.hallprice);
        hallType = findViewById(R.id.descrip);
        weddingbtn = findViewById(R.id.wedcheck);
        eventbtn = findViewById(R.id.eventcheck);


        dbf = FirebaseDatabase.getInstance().getReference("EM_HallManagement");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em = new EM_HallManagement();
                em.setName(hallName.getText().toString());
                em.setPrice(Float.parseFloat(hallPrice.getText().toString()));
                em.setDescription(hallType.getText().toString());
                em.setEvents(weddingbtn.isChecked());
                em.setEvents(eventbtn.isChecked());


                dbf.child(em.getName()).setValue(em);



                Toast.makeText(getApplicationContext(),"Data Has Been Added",Toast.LENGTH_SHORT).show();
            }
        });



    }
}

