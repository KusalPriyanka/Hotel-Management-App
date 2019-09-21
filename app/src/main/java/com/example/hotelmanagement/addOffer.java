package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import Modal.Packages;

public class addOffer extends AppCompatActivity {

    private EditText offerName, offerDes, offerPrice, noOfBeds, noOfPeople;
    private CheckBox wifi, tv, sp, ac;
    Button addPackage;
    ImageView packImg;

    private Uri uploadImgUri, downloadImgUri;

    DatabaseReference fb;
    StorageReference st;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        offerName = findViewById(R.id.offerName);
        offerDes = findViewById(R.id.des);
        offerPrice = findViewById(R.id.price);
        noOfBeds = findViewById(R.id.beds);
        noOfPeople = findViewById(R.id.people);
        packImg = findViewById(R.id.img);

        wifi = findViewById(R.id.wifi);
        tv = findViewById(R.id.tv);
        sp = findViewById(R.id.sp);
        ac = findViewById(R.id.ac);

        addPackage = findViewById(R.id.addoffers);

        fb = FirebaseDatabase.getInstance().getReference("RoomPackages");
        st = FirebaseStorage.getInstance().getReference("RoomPackages");

        progressDialog = new ProgressDialog(addOffer.this);

        packImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpImage();
            }
        });

        addPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPackage();
            }
        });
    }

    private void pickUpImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){

            uploadImgUri = data.getData();
            packImg.setImageURI(uploadImgUri);

        }

    }

    private void uploadImg(){

        final StorageReference storeRef = st.child("Package" + uploadImgUri.getLastPathSegment());

        storeRef.putFile(uploadImgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storeRef.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        downloadImgUri = uri;
                                        progressDialog.setMessage("Package Details Adding To The App... Wait!");
                                    }
                                });

                    }
                });

    }

    private void addPackage(){


        progressDialog.setTitle("Add Package To The App");
        progressDialog.setMessage("Image Uploading.... Wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        uploadImg();

        Packages packages = new Packages();
        packages.setName(offerName.getText().toString());
        packages.setDes(offerDes.getText().toString());
        packages.setBeds(Integer.parseInt(noOfBeds.getText().toString()));
        packages.setPeople(Integer.parseInt(noOfPeople.getText().toString()));
        packages.setPrice(Float.parseFloat(offerPrice.getText().toString()));

        if(wifi.isChecked()){
            packages.setWf(true);
        }
        if(ac.isChecked()){
            packages.setAc(true);
        }
        if(sp.isChecked()){
            packages.setSp(true);
        }
        if(tv.isChecked()){
            packages.setTv(true);
        }

        packages.setUrl(downloadImgUri + " ");

        fb.setValue(packages)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(),"Package Added Succesfully!",Toast.LENGTH_SHORT).show();

                    }
                });

    }

}

