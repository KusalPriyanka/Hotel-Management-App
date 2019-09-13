package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

public class EM_Addhalls extends AppCompatActivity {
Button button;
ImageView updatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__addhalls);

        button = findViewById(R.id.addHall);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_Addhalls.this, EM_AddhallDetails.class);
                startActivity(intent);
            }
        });

        updatebtn = findViewById(R.id.buttonemupdate);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_Addhalls.this, EM_EventDetails.class);
                startActivity(intent);
            }
        });



    }
}

