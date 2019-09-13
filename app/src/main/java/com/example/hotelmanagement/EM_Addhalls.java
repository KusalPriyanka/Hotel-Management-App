package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EM_Addhalls extends AppCompatActivity {
Button button;
ImageView updatebtn;
Button deleteAllbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__addhalls);

        button = findViewById(R.id.addHall);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_Addhalls.this, EM_AddhallDetails.class);
                startActivity(intent);
            }
        });

        updatebtn = findViewById(R.id.buttonemupdate);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_Addhalls.this, EM_EventDetails.class);
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

