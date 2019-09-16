package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import Modal.Reservation;

public class showOffers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offers);

        Reservation reservation = (Reservation) getIntent().getSerializableExtra("Res");

        Toast.makeText(getApplicationContext(),reservation.toString() + "",Toast.LENGTH_LONG).show();

    }
}
