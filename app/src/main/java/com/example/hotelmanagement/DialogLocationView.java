package com.example.hotelmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Modal.Place;
import Modal.Vehicle;

public class DialogLocationView extends AppCompatDialogFragment {



    RecyclerView recyclerView;
    ArrayList<Place> list_place;
    DatabaseReference mdb;

    LinearLayoutManager linearLayoutManager;
    LocationViewAdapter locationViewAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_place_view, null);

        init(view);

        builder.setView(view)
                .setTitle("All Locations")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DialogLocationView.this.dismiss();
                    }
                });

//        loadVehicleTypes(view);

        addlistener();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(locationViewAdapter);

        return builder.create();

    }

    private void addlistener() {
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                Place place;
                list_place.removeAll(list_place);

                for (DataSnapshot ds : children) {
                    System.out.println(ds.getKey()+" <<<<<<<<<<<<< list");
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


    private void init(View view) {

        recyclerView = view.findViewById(R.id.rv_adlocview);

        list_place = new ArrayList<>();

        mdb = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("place");

        linearLayoutManager = new LinearLayoutManager(getActivity());
        locationViewAdapter = new LocationViewAdapter(list_place,getActivity());

    }

}


class LocationViewAdapter extends RecyclerView.Adapter {

    ArrayList<Place> list_place;
    Activity activity;

    public LocationViewAdapter(ArrayList<Place> list_place, Activity activity) {
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Place place = list_place.get(position);

        VehicleViewHolder vehicleViewHolder = (VehicleViewHolder) holder;

        vehicleViewHolder.imageView.setVisibility(View.INVISIBLE);
//        vehicleViewHolder.imageView.setBackgroundResource(R.drawable.travel2_place);

        vehicleViewHolder.tv_type.setText(place.getName().toUpperCase());
        vehicleViewHolder.tv_price.setText(place.getDetails());

        vehicleViewHolder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(place.getId()+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                FirebaseDatabase.getInstance().getReference().child("PayDetails").child("place").child(place.getId()).removeValue();

                Toast.makeText(activity, "Location Removed.", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list_place.size();
    }
}
