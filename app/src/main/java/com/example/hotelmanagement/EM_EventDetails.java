package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import Modal.EM_HallManagement;

public class EM_EventDetails extends AppCompatActivity {

    EditText updhallName,updhallPrice,updhallType;
    CheckBox updweddingbtn,updeventbtn;
    DatabaseReference dbf;
    EM_HallManagement em;
    Button update;
    Button delete;
    EM_HallManagement em_hallManagementl;
    ImageView backToView;
    String imagePath;
    private StorageReference sr;
    private static final int PICK_FROM_GALLARY = 2;
    private Uri hallImageUri;
    private String ImagePath;
    private ProgressBar addHallPro;
    ImageView hallImage,backtoupdatedview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__event_details);

        Intent intent = getIntent();
        em_hallManagementl = (EM_HallManagement) intent.getSerializableExtra("em_hallManagement");



        update = findViewById(R.id.udatepagebtn);
        updhallName = findViewById(R.id.hallnameupdate);
        updhallPrice = findViewById(R.id.hallpriceupdate);
        updhallType = findViewById(R.id.descripudate);
        updweddingbtn = findViewById(R.id.wedcheckupdate);
        updeventbtn = findViewById(R.id.eventcheckupdate);
        hallImage = findViewById(R.id.upload);
        imagePath = em_hallManagementl.getImageName();




        dbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement").child(em_hallManagementl.getId());
        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updhallName.setText(dataSnapshot.child("name").getValue().toString());
                updhallPrice.setText(dataSnapshot.child("price").getValue().toString());
                updhallType.setText(dataSnapshot.child("description").getValue().toString());

                if((Boolean) dataSnapshot.child("wedding").getValue() == true){
                    updweddingbtn.setChecked(true);
                }

                if((Boolean) dataSnapshot.child("events").getValue() == true){
                    updeventbtn.setChecked(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        hallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_FROM_GALLARY);
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(updhallName.getText().toString().isEmpty()){
                    updhallName.setError("Please Fill Hall Name");
                }else if(updhallPrice.getText().toString().isEmpty()){
                    updhallName.setError("Please Fill Hall Price");


                }else if(updhallType.getText().toString().isEmpty()){
                    updhallType.setError("Please Fill Description");


                }else{

                    EM_HallManagement em = new EM_HallManagement();
                    em.setId(em_hallManagementl.getId());
                    em.setName(updhallName.getText().toString());
                    em.setPrice(Float.parseFloat(updhallPrice.getText().toString()));
                    em.setDescription(updhallType.getText().toString());
                    em.setWedding(updweddingbtn.isChecked());
                    em.setEvents(updeventbtn.isChecked());
                    em.setImageName(imagePath);


                    dbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement").child(em_hallManagementl.getId());
                    dbf.setValue(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Data Updating is Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EM_EventDetails.this, EM_Addhalls.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), "Data Updating is Not Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EM_EventDetails.this, EM_Addhalls.class);
                                startActivity(intent);
                            }

                        }
                    });




                }







                //back icon image link
                backtoupdatedview = findViewById(R.id.backView);
                backtoupdatedview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EM_EventDetails.this, EM_UpdatedView.class);
                        startActivity(intent);
                    }
                });






            }
        });











    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FROM_GALLARY && resultCode == RESULT_OK && data != null && data.getData() != null){
            hallImageUri = data.getData();

        }

    }


}
