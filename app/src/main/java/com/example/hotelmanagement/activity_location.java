package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Modal.Vehicle;

public class activity_location extends AppCompatActivity {

    DatabaseReference dbref;
    RecyclerView recyclerView;
    PayDetails vehicle;

    LinearLayoutManager linearLayoutManager;
    VehicleViewAdapter2 vehicleViewAdapter;

    ArrayList<Vehicle> list_vehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        vehicle = new PayDetails();

        init();

        addlistener();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(vehicleViewAdapter);

//        cardView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");
//                vehicle.setVehicle(Mcar.getText().toString().trim());
//                dbref.child("vehicle").setValue(vehicle);
//
//                Intent intent01 = new Intent(activity_location.this,activity_payment_details.class);
//                intent01.putExtra("third",0);
//                startActivity(intent01);
//
//            }
//
//        });


    }

    private void addlistener() {


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                Vehicle vehicle;
                list_vehicle.removeAll(list_vehicle);

                for (DataSnapshot ds : children) {
                    System.out.println(ds.getKey() + " <<<<<<<<<<<<< list");
                    vehicle = ds.getValue(Vehicle.class);
                    vehicle.setId(ds.getKey());
                    list_vehicle.add(vehicle);

                }

                vehicleViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {

        recyclerView = findViewById(R.id.rv_vtypeviewc);

        list_vehicle = new ArrayList<>();

        dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("vehicle");

        linearLayoutManager = new LinearLayoutManager(activity_location.this);
        vehicleViewAdapter = new VehicleViewAdapter2(list_vehicle,activity_location.this);

    }
}

class VehicleViewAdapter2 extends RecyclerView.Adapter {

    ArrayList<Vehicle> list_vehicles;
    Activity activity;

    public VehicleViewAdapter2(ArrayList<Vehicle> list_vehicles, Activity activity) {
        this.list_vehicles = list_vehicles;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.vehicle_view, parent, false);
        VehicleViewHolder vehicleViewHolder = new VehicleViewHolder(view);
        return vehicleViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Vehicle vehicle = list_vehicles.get(position);

        VehicleViewHolder vehicleViewHolder = (VehicleViewHolder) holder;

        vehicleViewHolder.tv_type.setText(vehicle.getName().toUpperCase());
        vehicleViewHolder.tv_price.setText(vehicle.getPrice() + " per KM");

        vehicleViewHolder.btn_remove.setVisibility(View.INVISIBLE);
//        vehicleViewHolder.btn_remove.setBackgroundColor(R.color.colorPrimary);
//
        vehicleViewHolder.cons_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent01 = new Intent(activity,activity_payment_details.class);
                intent01.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent01);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list_vehicles.size();
    }
}