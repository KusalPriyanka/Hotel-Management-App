package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EM_SelectionPage extends AppCompatActivity {
    Button weddingbtn;
    Button eventbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__selection_page);

        weddingbtn = findViewById(R.id.wedding);
        weddingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_SelectionPage.this,EM_EventManagementHome.class);
                startActivity(intent);
            }
        });

        eventbtn = findViewById(R.id.buttonevent);
        eventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_SelectionPage.this,EM_EventView.class);
                startActivity(intent);
            }
        });

    }
}
