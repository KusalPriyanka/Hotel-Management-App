package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Modal.EM_HallManagement;
import Modal.MainMeals;

public class EM_Addhalls extends AppCompatActivity {
    Button button;

    Button deleteAllbtn;
    DatabaseReference df;

    ListView listView;
    ImageView emSearchIcon;
    EditText emSearchBar;
    ImageView backtoSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__addhalls);

        final ProgressBar proSerch = findViewById(R.id.emprogress);
        proSerch.setVisibility(View.VISIBLE);
        listView = findViewById(R.id.listView);
        emSearchIcon = findViewById(R.id.searchbtn);
        emSearchBar = findViewById(R.id.emsrchbar);

        emSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emSearchBar = findViewById(R.id.emsrchbar);
                String serchTag = emSearchBar.getText().toString();

                if(serchTag.isEmpty()){
                    emSearchBar.setError("Please Enter ");
                }else{
                    proSerch.setVisibility(View.VISIBLE);
                    df = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement").child(serchTag);
                    df.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            EM_HallManagement em_hallManagement = dataSnapshot.getValue(EM_HallManagement.class);

                            if(em_hallManagement != null){
                                proSerch.setVisibility(View.GONE);
                                Intent intent =  new Intent(EM_Addhalls.this,  EM_UpdatedView.class);
                                intent.putExtra("em_hallManagement", em_hallManagement);
                                startActivity(intent);
                            }else {
                                proSerch.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Please Enter Valid Id!", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });





        df = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");
        FirebaseListAdapter<EM_HallManagement> adapter = new FirebaseListAdapter<EM_HallManagement>(
                this,EM_HallManagement.class, android.R.layout.simple_list_item_1, df
        ) {
            @Override
            protected void populateView(View v, EM_HallManagement model, int position) {
                TextView textView = v.findViewById(android.R.id.text1);
                textView.setText(model.toString());
            }
        };
        listView.setAdapter(adapter);
        proSerch.setVisibility(View.INVISIBLE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EM_HallManagement em_hallManagement =(EM_HallManagement)adapterView.getAdapter().getItem(i);
                Intent intent =  new Intent(EM_Addhalls.this, EM_UpdatedView.class);
                intent.putExtra("em_hallManagement", em_hallManagement);
                startActivity(intent);
            }
        });



        backtoSel = findViewById(R.id.backtoselect);
        backtoSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_Addhalls.this, EM_SelectionPage.class);
                startActivity(intent);
            }
        });









        button = findViewById(R.id.addHall);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_Addhalls.this, EM_AddhallDetails.class);
                startActivity(intent);
            }
        });




        deleteAllbtn = findViewById(R.id.buttondelete);
        deleteAllbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAllHalls();
            }
        });



    }


    public void DeleteAllHalls(){
        DatabaseReference deletedbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");
        deletedbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");
        deletedbf.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "All Halls Are Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EM_Addhalls.this, EM_Addhalls.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EM_Addhalls.this, EM_Addhalls.class);
                    startActivity(intent);
                }

            }
        });

    }
}

