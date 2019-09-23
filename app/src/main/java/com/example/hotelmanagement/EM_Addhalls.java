package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.ArrayList;
import java.util.List;

import Modal.EM_HallManagement;
import Modal.MainMeals;
import Modal.WedHallList;

public class EM_Addhalls extends AppCompatActivity {
    Button button;

    Button deleteAllbtn,popupdelete,popupcancel;
    DatabaseReference df;

    ListView listView;
    ImageView emSearchIcon;
    EditText emSearchBar;
    ImageView backtoSel;
    ProgressBar proSerch;
    private Dialog deleteAllDialog;
    private List<EM_HallManagement> hallList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__addhalls);

        proSerch = findViewById(R.id.emprogress);
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
        deleteAllDialog = new Dialog(this);
        deleteAllbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAllDialog.setContentView(R.layout.em_popup_deleteall);
                deleteAllDialog.setCanceledOnTouchOutside(true);
                deleteAllDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteAllDialog.show();


                popupdelete = deleteAllDialog.findViewById(R.id.empopdel);
                popupdelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteAllHalls();
                    }
                });

                popupcancel = deleteAllDialog.findViewById(R.id.empopcancel);
                popupcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteAllDialog.dismiss();
                    }
                });

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


    @Override
    protected void onStart() {
        super.onStart();
        proSerch.setVisibility(View.VISIBLE);
        df = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hallList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    EM_HallManagement em_hallManagement = ds.getValue(EM_HallManagement.class);
                    hallList.add(em_hallManagement);

                }

                WedHallList wedHallList = new WedHallList(EM_Addhalls.this, hallList);

                listView.setAdapter(wedHallList);
                proSerch.setVisibility(View.GONE);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        EM_HallManagement em_hallManagement =(EM_HallManagement)adapterView.getAdapter().getItem(i);
                        Intent intent =  new Intent(EM_Addhalls.this, EM_UpdatedView.class);
                        intent.putExtra("em_hallManagement", em_hallManagement);
                        startActivity(intent);



                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}

