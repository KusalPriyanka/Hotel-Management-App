package com.example.hotelmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Modal.Vehicle;

public class DialogVehicleView extends AppCompatDialogFragment {

    RecyclerView recyclerView;
    ArrayList<Vehicle> list_vehicle;
    DatabaseReference mdb;

    LinearLayoutManager linearLayoutManager;
    VehicleViewAdapter vehicleViewAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_vehicle_view, null);

        init(view);

        builder.setView(view)
                .setTitle("All Vehicle Types")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DialogVehicleView.this.dismiss();
                    }
                });

//        loadVehicleTypes(view);

        addlistener();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(vehicleViewAdapter);

        return builder.create();

    }

    private void addlistener() {
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                Vehicle vehicle;
                list_vehicle.removeAll(list_vehicle);

                for (DataSnapshot ds : children) {
                    System.out.println(ds.getKey()+" <<<<<<<<<<<<< list");
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

    private void loadVehicleTypes(View view) {

        mdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                Vehicle vehicle;

                for (DataSnapshot ds : children) {
                    System.out.println(ds.getKey()+" <<<<<<<<<<<<<");
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

    private void init(View view) {

        recyclerView = view.findViewById(R.id.rv_veha);

        list_vehicle = new ArrayList<>();

        mdb = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("vehicle");

        linearLayoutManager = new LinearLayoutManager(getActivity());
        vehicleViewAdapter = new VehicleViewAdapter(list_vehicle,getActivity());

    }

}

class VehicleViewAdapter extends RecyclerView.Adapter {

    ArrayList<Vehicle> list_vehicles;
    Activity activity;

    public VehicleViewAdapter(ArrayList<Vehicle> list_vehicles, Activity activity) {
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Vehicle vehicle = list_vehicles.get(position);

        VehicleViewHolder vehicleViewHolder = (VehicleViewHolder) holder;

        vehicleViewHolder.imageView.setVisibility(View.VISIBLE);

        vehicleViewHolder.tv_type.setText(vehicle.getName().toUpperCase());
        vehicleViewHolder.tv_price.setText(vehicle.getPrice() + " per KM");

        vehicleViewHolder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(vehicle.getId()+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                FirebaseDatabase.getInstance().getReference().child("PayDetails").child("vehicle").child(vehicle.getId()).removeValue();

                Toast.makeText(activity, "Vehicle Type Removed.", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list_vehicles.size();
    }
}

class VehicleViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_type, tv_price;
    public Button btn_remove;
    public ConstraintLayout cons_lay;
    public ImageView imageView;

    public VehicleViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_price = itemView.findViewById(R.id.tv_vpriceview);
        tv_type = itemView.findViewById(R.id.tv_vtypeview);
        btn_remove = itemView.findViewById(R.id.btn_removevtype);
        cons_lay = itemView.findViewById(R.id.cons_lay_v_view);
        imageView = itemView.findViewById(R.id.imgview_vtype);

    }
}
