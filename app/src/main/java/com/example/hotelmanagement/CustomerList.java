package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modal.Customer;

public class CustomerList extends AppCompatActivity {

    private ListView cusList;
    private DatabaseReference fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        cusList = findViewById(R.id.cusList);
        fb = FirebaseDatabase.getInstance().getReference().child("Customers");

/*        FirebaseListAdapter<Customer> adapter = new FirebaseListAdapter<Customer>(
                this,
                Customer.class,
                android.R.layout.simple_list_item_1,
                fb
        ) {
            @Override
            protected void populateView(View v, Customer model, int position) {
                TextView textView = v.findViewById(android.R.id.text1);
                textView.setText(model.toString());
            }
        };

        cusList.setAdapter(adapter);

        */
    }

}
