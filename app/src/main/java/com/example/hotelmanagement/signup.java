package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import Modal.Customer;

public class signup extends AppCompatActivity {

    private EditText userName, mobileNo, email, password;
    private Button signup;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.userNameUp);
        mobileNo = findViewById(R.id.mobileNoUp);
        email = findViewById(R.id.emailUp);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.addoffers);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser(){

        progressBar.setVisibility(View.VISIBLE);

        final String userEmail = email.getText().toString().trim();
        final String userPass = password.getText().toString().trim();
        final String userMobile = mobileNo.getText().toString().trim();
        final String username = userName.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/hotel-management-app-bdc4c.appspot.com/o/user.png?alt=media&token=e4ae0d43-961a-4643-8b4d-89cdbe006ddc"))
                                    .build();

                            user.updateProfile(profileUpdates);

                            Customer customer = new Customer(username, userMobile, userEmail);

                            FirebaseDatabase.getInstance().getReference("Customers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customer)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Registered Successfully!" ,Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(signup.this,Login.class));
                                            }
                                            else{

                                                Toast.makeText(getApplicationContext(),task.getException().getMessage() ,Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}