package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Modal.EM_HallManagement;

public class EM_UpdatedView extends AppCompatActivity {
    TextView passedHallname,passedHallprice,passedHalltype;
    CheckedTextView passedWed,passedEvent;
    EM_HallManagement em_hallManagement1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__updated_view);



        Intent intent = getIntent();
        em_hallManagement1 = (EM_HallManagement) intent.getSerializableExtra("em_hallManagement");

        passedHallname = findViewById(R.id.viewname);
        passedHallprice = findViewById(R.id.viewprice);
        passedHalltype = findViewById(R.id.viewdes);
        passedWed = findViewById(R.id.wed);
        passedEvent = findViewById(R.id.event);






        DatabaseReference dbpf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement").child(em_hallManagement1.getId());
        dbpf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){


                    passedHallname.setText(dataSnapshot.child("name").getValue().toString());
                    passedHallprice.setText(dataSnapshot.child("price").getValue().toString());
                    passedHalltype.setText(dataSnapshot.child("description").getValue().toString());

                    if((Boolean) dataSnapshot.child("wedding").getValue() == true){
                        passedWed.setCheckMarkDrawable(R.drawable.mm_ic_edit_black_24dp);

                    }
                    if((Boolean) dataSnapshot.child("events").getValue() == true){
                        passedEvent.setCheckMarkDrawable(R.drawable.mm_ic_edit_black_24dp);

                    }





                }








            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });















    }
}

