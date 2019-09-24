package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Modal.Customer;
import de.hdodenhof.circleimageview.CircleImageView;

public class userinfo extends AppCompatActivity {

    private EditText userName, mobileNo;
    private Button updateInfo, reservationList, logout;
    private TextView userNameLabel;
    private CircleImageView userImg;
    private FirebaseUser firebaseUser;
    private DatabaseReference fb;
    private ProgressDialog progressDialog;
    private Uri uploadImgUri;
    private StorageReference st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        userName = findViewById(R.id.userNameUp);
        mobileNo = findViewById(R.id.mobileNoUp);
        userImg = findViewById(R.id.userImageUpdate);
        userNameLabel = findViewById(R.id.userNameLabel);

        updateInfo = findViewById(R.id.UserinfoUpdate);
        reservationList = findViewById(R.id.reservationlist);
        logout = findViewById(R.id.logout);

        progressDialog = new ProgressDialog(userinfo.this);

        progressDialog.setTitle("UserInfo Page");
        progressDialog.setMessage("User Information Loading... Wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fb = FirebaseDatabase.getInstance().getReference("Customers").child(firebaseUser.getUid());
        st = FirebaseStorage.getInstance().getReference("CusImg");

        fb.addValueEventListener(readCusDetails);

        userName.setText(firebaseUser.getDisplayName());
        userNameLabel.setText(firebaseUser.getDisplayName());

        Glide.with(userinfo.this)
                .load(firebaseUser.getPhotoUrl())
                .into(userImg);


        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpImage();
            }
        });

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo();
            }
        });

        reservationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userinfo.this,reservationlist.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"Logout!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(userinfo.this,Login.class));
            }
        });
    }

    ValueEventListener readCusDetails = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Customer customer = dataSnapshot.getValue(Customer.class);
            mobileNo.setText(customer.getMobileNo());
            progressDialog.dismiss();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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
            userImg.setImageURI(uploadImgUri);

        }

    }

    private void updateInfo(){

        progressDialog.setTitle("UserInfo Page");
        progressDialog.setMessage("User Information Updating... Wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String name = userName.getText().toString();
        final String mobile = mobileNo.getText().toString();

        final StorageReference storeRef = st.child("Cus" + uploadImgUri.getLastPathSegment());

        storeRef.putFile(uploadImgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .setPhotoUri(uri)
                                        .build();

                                firebaseUser.updateProfile(profileUpdates);

                                Customer customer = new Customer(name,mobile,firebaseUser.getEmail());

                                FirebaseDatabase.getInstance().getReference("Customers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(customer)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "User Details Update Successfully!" ,Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(userinfo.this,MainActivity.class));
                                                }
                                                else{

                                                    Toast.makeText(getApplicationContext(),task.getException().getMessage() ,Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });

                            }
                        });

                    }
                });

    }
}
