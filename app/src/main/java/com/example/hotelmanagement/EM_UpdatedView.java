package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
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
    TextView passedHallname,passedHallprice,passedHalltype,passedhallid;
    CheckedTextView passedWed,passedEvent;
    EM_HallManagement em_hallManagement1;
    Button update;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__updated_view);



        Intent intent = getIntent();
        em_hallManagement1 = (EM_HallManagement) intent.getSerializableExtra("em_hallManagement");
        update = findViewById(R.id.udateviewbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_UpdatedView.this, EM_EventDetails.class);
                intent.putExtra("em_hallManagement", em_hallManagement1);
                startActivity(intent);
            }
        });

        passedhallid = findViewById(R.id.textVieweid);
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

                    passedhallid.setText(dataSnapshot.child("id").getValue().toString());
                    passedHallname.setText(dataSnapshot.child("name").getValue().toString());
                    passedHallprice.setText(dataSnapshot.child("price").getValue().toString());
                    passedHalltype.setText(dataSnapshot.child("description").getValue().toString());

                    if((Boolean) dataSnapshot.child("wedding").getValue() == true){
                        passedWed.setCheckMarkDrawable(R.drawable.tick4);

                    }
                    if((Boolean) dataSnapshot.child("events").getValue() == true){
                        passedEvent.setCheckMarkDrawable(R.drawable.tick4);

                    }





                }








            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back = findViewById(R.id.backAdd);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_UpdatedView.this, EM_Addhalls.class);
                startActivity(intent);
            }
        });
















    }
}

