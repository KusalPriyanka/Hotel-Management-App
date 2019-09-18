package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Classes.Validations;
import Modal.Place;
import Modal.Vehicle;

public class travel_admin extends AppCompatActivity {

    EditText et_price, et_name,et_lname,et_ldetails;
    Button btn_add, btn_view,btn_ladd,btn_lview;
    DatabaseReference mdb,mdb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_admin);

        init();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_name.getText().toString().equals("") || et_price.getText().toString().equals("")) {
                    Toast.makeText(travel_admin.this, "Some details are empty.", Toast.LENGTH_SHORT).show();
                } else {

                    if (Validations.isDouble(et_price.getText().toString())) {

                        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                System.out.println(dataSnapshot.getChildrenCount());
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                boolean isFound = false;
                                Vehicle vehicle;

                                for (DataSnapshot ds : children) {

                                    vehicle = ds.getValue(Vehicle.class);

                                    if (et_name.getText().toString().toLowerCase().equals(vehicle.getName().toLowerCase())) {
                                        isFound = true;
                                        break;
                                    }

                                }

                                if (isFound) {
                                    Toast.makeText(travel_admin.this, "Vehicle Type Already Added.", Toast.LENGTH_SHORT).show();
                                } else {

                                    mdb.push().setValue(new Vehicle(Double.parseDouble(et_price.getText().toString()), et_name.getText().toString()));

                                    et_name.setText("");
                                    et_price.setText("");

                                    Toast.makeText(travel_admin.this, "Vehicle Type Added Successfully.", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } else {

                        Toast.makeText(travel_admin.this, "Invalid Price.", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });


        btn_ladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_lname.getText().toString().equals("") || et_ldetails.getText().toString().equals("")) {
                    Toast.makeText(travel_admin.this, "Some details are empty.", Toast.LENGTH_SHORT).show();
                } else {

                        mdb2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                System.out.println(dataSnapshot.getChildrenCount());
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                boolean isFound = false;
                                Place place;

                                for (DataSnapshot ds : children) {

                                    place = ds.getValue(Place.class);

                                    if (et_name.getText().toString().toLowerCase().equals(place.getName().toLowerCase())) {
                                        isFound = true;
                                        break;
                                    }

                                }

                                if (isFound) {
                                    Toast.makeText(travel_admin.this, "Location Already Added.", Toast.LENGTH_SHORT).show();
                                } else {

                                    mdb2.push().setValue(new Place(et_lname.getText().toString(), et_ldetails.getText().toString()));

                                    et_lname.setText("");
                                    et_ldetails.setText("");

                                    Toast.makeText(travel_admin.this, "Location Added Successfully.", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                }

            }
        });


        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogVehicleView dialogVehicleView = new DialogVehicleView();
                dialogVehicleView.show(getSupportFragmentManager(),null);

            }
        });

        btn_lview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogLocationView dialogLocationView= new DialogLocationView();
                dialogLocationView.show(getSupportFragmentManager(),null);

            }
        });

    }

    private void init() {

        et_price = findViewById(R.id.et_advehprice);
        et_name = findViewById(R.id.et_advehname);

        btn_add = findViewById(R.id.btn_addveh);
        btn_view = findViewById(R.id.btn_showveh);

        mdb = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("vehicle");

        et_lname = findViewById(R.id.et_adlocname);
        et_ldetails = findViewById(R.id.et_adlocdetails);

        btn_ladd = findViewById(R.id.btn_addloc);
        btn_lview = findViewById(R.id.btn_showloc);

        mdb2 = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("place");

    }
}
