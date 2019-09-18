package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TravelHome extends AppCompatActivity {

    Button btn_admin,btn_thome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_home);

        init();

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TravelHome.this,travel_admin.class);
                startActivity(intent);

            }
        });

        btn_thome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TravelHome.this,travel_option.class);
                startActivity(intent);

            }
        });

    }

    private void init() {

        btn_admin = findViewById(R.id.btn_adminh);
        btn_thome = findViewById(R.id.btn_toption);

    }
}
