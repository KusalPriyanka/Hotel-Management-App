package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class travel_option extends AppCompatActivity {

    TextView jaffna,colombo,sigirya,kandy;
    CardView Jaffna,Colombo,Kandy,Sigiriya;
    DatabaseReference dbref;
    PayDetails place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traveloption);

        //Text View
        jaffna=findViewById(R.id.jaffna);
        sigirya=findViewById(R.id.sigiriya);
        colombo=findViewById(R.id.colombo);
        kandy=findViewById(R.id.kandy);

        //Card View
        Jaffna=findViewById(R.id.Jaffna);
        Sigiriya=findViewById(R.id.Sigiriya);
        Colombo=findViewById(R.id.Colombo);
        Kandy=findViewById(R.id.Kandy);



        place = new PayDetails();

        Jaffna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                place.setPlace(jaffna.getText().toString().trim());
                dbref.child("place").setValue(place);

                Intent intent1 = new Intent(travel_option.this, activity_location.class);
                startActivity(intent1);


            }
        });

        Sigiriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                place.setPlace(sigirya.getText().toString().trim());
                dbref.child("place").setValue(place);

                Intent intent1 = new Intent(travel_option.this, activity_location.class);
                startActivity(intent1);
            }
        });


        Colombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                place.setPlace(colombo.getText().toString().trim());
                dbref.child("place").setValue(place);


                Intent intent1 = new Intent(travel_option.this, activity_location.class);
                startActivity(intent1);
            }
        });

        Kandy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                place.setPlace(kandy.getText().toString().trim());
                dbref.child("place").setValue(place);

                Intent intent1 = new Intent(travel_option.this, activity_location.class);
                startActivity(intent1);
            }
        });
    }


}



