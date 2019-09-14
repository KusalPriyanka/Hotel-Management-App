package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EM_EventManagementHome extends AppCompatActivity {
    Button viewmorebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__event_management_home);
        viewmorebtn = findViewById(R.id.viewhalls);

        viewmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_EventManagementHome.this,EM_HallView.class);
                startActivity(intent);
            }
        });


    }
}

