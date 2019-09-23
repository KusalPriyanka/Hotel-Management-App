package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import Modal.EM_HallManagement;
import Modal.MainMeals;
import Util.CommonConstants;
import Util.CommonFunctions;

public class EM_AddhallDetails extends AppCompatActivity {
    EditText hallName,hallPrice,hallType;
    CheckBox weddingbtn,eventbtn;
    DatabaseReference dbf;
    EM_HallManagement em;
    ImageView hallImage;
    Button buttonAdd;
    String id;
    private StorageReference sr;
    private static final int PICK_FROM_GALLARY = 2;
    private Uri hallImageUri;
    private String ImagePath;
    private ProgressBar addHallPro;
    private List<EM_HallManagement> hallList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__addhall_details);
        addHallPro = findViewById(R.id.uploadImagePro);
        addHallPro.setVisibility(View.INVISIBLE);
        sr = FirebaseStorage.getInstance().getReference("EMHallImages");

        buttonAdd =findViewById(R.id.addbuttonhall);
        hallName = findViewById(R.id.hallname);
        hallPrice = findViewById(R.id.hallprice);
        hallType = findViewById(R.id.descrip);
        weddingbtn = findViewById(R.id.wedcheck);
        eventbtn = findViewById(R.id.eventcheck);
        hallImage = findViewById(R.id.upload);
        hallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_FROM_GALLARY);
            }
        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(hallName.getText().toString().isEmpty()){
                    hallName.setError("Please Enter Name");


                }
                else if(hallPrice.getText().toString().isEmpty()){
                    hallPrice.setError("Please Enter Price");


                }else if(hallType.getText().toString().isEmpty()){
                    hallType.setError("Please Enter Description");


                }else if(weddingbtn.isChecked() == false && eventbtn.isChecked() == false){
                    weddingbtn.setError("Please Fill One of the Boxes");
                    eventbtn.setError("Please Fill One of the Boxes");

                }else if(weddingbtn.isChecked() == true && eventbtn.isChecked() == true){
                    weddingbtn.setError("Please don't Fill Both");
                    eventbtn.setError("Please don't Fill Both");

                }

                else{
                        insertData();

                }





            }
        });

    }

    public void insertData(){


        dbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");
        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hallList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    EM_HallManagement em_hallManagement= ds.getValue(EM_HallManagement.class);
                    hallList.add(em_hallManagement);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        id =  CommonFunctions.get_EM_Hall_Id(CommonConstants.EM_PREFIX, hallList);


        final StorageReference sf = sr.child("EM" + hallImageUri.getLastPathSegment());
        sf.putFile(hallImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImagePath = uri.toString();

                        EM_HallManagement em = new EM_HallManagement();
                        em.setId(id);
                        em.setName(hallName.getText().toString());
                        em.setPrice(Float.parseFloat(hallPrice.getText().toString()));
                        em.setDescription(hallType.getText().toString());
                        em.setWedding(weddingbtn.isChecked());
                        em.setEvents(eventbtn.isChecked());
                        em.setImageName(ImagePath);


                        dbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement");

                        dbf.child(em.getId()).setValue(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(getApplicationContext(), "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EM_AddhallDetails.this, EM_Addhalls.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Data Not Inserted Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EM_AddhallDetails.this, EM_Addhalls.class);
                                    startActivity(intent);
                                }

                            }
                        });
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


