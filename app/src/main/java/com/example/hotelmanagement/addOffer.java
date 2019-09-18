package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class addOffer extends AppCompatActivity {

    private EditText offerName, offerDes, offerPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        offerName = findViewById(R.id.offerName);
        offerDes = findViewById(R.id.offerdescription);
        offerPrice = findViewById(R.id.price);

    }
}
