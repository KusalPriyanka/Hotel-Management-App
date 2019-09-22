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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Modal.Place;
import Modal.Vehicle;

public class travel_option extends AppCompatActivity {

    DatabaseReference dbref;
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;
    LocationViewAdapter2 locationViewAdapter;

    ArrayList<Place> list_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traveloption);

//        dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");

init();

        addlistener();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(locationViewAdapter);

    }

    private void addlistener() {




        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                Place place;
                list_place.removeAll(list_place);

                for (DataSnapshot ds : children) {
                    System.out.println(ds.getKey() + " <<<<<<<<<<<<< list");
                    place = ds.getValue(Place.class);
                    place.setId(ds.getKey());
                    list_place.add(place);

                }

                locationViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        recyclerView = findViewById(R.id.rv_plviewcus);

        list_place = new ArrayList<>();

        dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("place");

        linearLayoutManager = new LinearLayoutManager(travel_option.this);
        locationViewAdapter = new LocationViewAdapter2(list_place,travel_option.this);
    }


}

class LocationViewAdapter2 extends RecyclerView.Adapter {

    ArrayList<Place> list_place;
    Activity activity;

    public LocationViewAdapter2(ArrayList<Place> list_place, Activity activity) {
        this.list_place = list_place;
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

        final Place place = list_place.get(position);

        VehicleViewHolder vehicleViewHolder = (VehicleViewHolder) holder;

        vehicleViewHolder.tv_type.setText(place.getName().toUpperCase());
        vehicleViewHolder.tv_price.setText(place.getDetails());

        vehicleViewHolder.btn_remove.setVisibility(View.INVISIBLE);

        vehicleViewHolder.imageView2.setVisibility(View.VISIBLE);
//
        vehicleViewHolder.cons_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent01 = new Intent(activity,activity_location.class);
                intent01.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent01);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list_place.size();
    }
}






