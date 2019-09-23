package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Adapter.ViewHolderReservation;
import Modal.Packages;
import Modal.Reservation;

public class reservationlist extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationlist);

        recyclerView = findViewById(R.id.list);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        fetch();
    }

    private void fetch() {

        Query query = FirebaseDatabase.getInstance().getReference("Reservation")
                .orderByChild("cusID")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseRecyclerOptions<Reservation> options =
                new FirebaseRecyclerOptions.Builder<Reservation>()
                        .setQuery(query, new SnapshotParser<Reservation>() {
                            @NonNull
                            @Override
                            public Reservation parseSnapshot(@NonNull DataSnapshot snapshot) {

                                Reservation reservation = snapshot.getValue(Reservation.class);
                                reservation.setId(snapshot.getKey());

                                return reservation;
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Reservation, ViewHolderReservation>(options) {
            @Override
            public ViewHolderReservation onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.reservationitem, parent, false);

                return new ViewHolderReservation(view);
            }


            @Override
            protected void onBindViewHolder(final ViewHolderReservation holder, final int position, final Reservation model) {

                Query packName = FirebaseDatabase.getInstance().getReference("RoomPackages").child(model.getPackageId());

                packName.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Packages packages = dataSnapshot.getValue(Packages.class);
                        holder.setTxtTitle(packages.getName());
                        holder.setImageView(packages.getUrl());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                holder.setCheckin(model.getCheckInDate(),model.getCheckInTime());
                holder.setCheckout(model.getCheckOutDate(),model.getCheckOutTime());
                holder.setNoofperson(model.getNoOfAdults()+model.getNoOfChild());

                holder.root.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View view) {

                        new AlertDialog.Builder(reservationlist.this)
                                .setTitle("Delete Reservation")
                                .setMessage("Are you sure you want to delete this Reservation?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        FirebaseDatabase.getInstance().getReference("Reservation").child(model.getId()).removeValue();

                                    }
                                })
                                .setNegativeButton("No",null)
                                .setIcon(R.drawable.delete).show();

                        return true;
                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
