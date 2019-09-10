package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_location extends AppCompatActivity {

    CardView cardView,cardView2,cardView3;
    DatabaseReference dbref;
    TextView Mcar,cur,txy;
    PayDetails vehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        cardView = findViewById(R.id.mcar);
        cardView2 = findViewById(R.id.taxi);
        cardView3 = findViewById(R.id.car);
        Mcar = findViewById(R.id.mbus) ;
        txy = findViewById(R.id.txy) ;
        cur = findViewById(R.id.cur) ;


        vehicle = new PayDetails();

        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                vehicle.setVehicle(Mcar.getText().toString().trim());
                dbref.child("vehicle").setValue(vehicle);

                Intent intent01 = new Intent(activity_location.this,activity_payment_details.class);
                intent01.putExtra("third",0);
                startActivity(intent01);

            }

        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                vehicle.setVehicle(txy.getText().toString().trim());
                dbref.child("vehicle").setValue(vehicle);

                Intent intent02 = new Intent(activity_location.this,activity_payment_details.class);
                intent02.putExtra("third",0);
                startActivity(intent02);

            }

        });


        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetail");
                vehicle.setVehicle(cur.getText().toString().trim());
                dbref.child("vehicle").setValue(vehicle);

                Intent intent03 = new Intent(activity_location.this,activity_payment_details.class);
                intent03.putExtra("third",0);
                startActivity(intent03);

            }

        });





    }
}
