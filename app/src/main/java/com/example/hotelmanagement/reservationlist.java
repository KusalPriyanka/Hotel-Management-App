package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Adapter.ViewHolderReservation;
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

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Reservation");

        FirebaseRecyclerOptions<Reservation> options =
                new FirebaseRecyclerOptions.Builder<Reservation>()
                        .setQuery(query, new SnapshotParser<Reservation>() {
                            @NonNull
                            @Override
                            public Reservation parseSnapshot(@NonNull DataSnapshot snapshot) {

                                Reservation reservation = snapshot.getValue(Reservation.class);

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
            protected void onBindViewHolder(ViewHolderReservation holder, final int position, final Reservation model) {
                holder.setTxtTitle(model.getCheckInDate());
                holder.setTxtDesc(model.getPackageId());
                holder.setImageView("https://firebasestorage.googleapis.com/v0/b/hotel-management-app-bdc4c.appspot.com/o/RoomPackages%2FPackage125161869?alt=media&token=44244cfa-6609-4b4c-9ba7-fe15f9c5019f");

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), model.getCusID(), Toast.LENGTH_SHORT).show();
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
