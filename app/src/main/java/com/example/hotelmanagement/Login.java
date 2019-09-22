package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button login;
    private TextView signup;
    private EditText username, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.addoffers);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){

                    startActivity(new Intent(Login.this,MainActivity.class));

                }

            }
        };


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,signup.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void LoginUser(){

            String un = username.getText().toString().trim();
            String pwd = password.getText().toString().trim();

        if(TextUtils.isEmpty(un) || TextUtils.isEmpty(pwd)){

            Toast.makeText(getApplicationContext(), "TextField Empty!" ,Toast.LENGTH_LONG).show();

        }
        else if(un.equals("admin") && pwd.equals("admin")){

            startActivity(new Intent(Login.this,adminDashboard.class));

        }
        else{
            mAuth.signInWithEmailAndPassword(un, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), task.getException().getMessage()  ,Toast.LENGTH_LONG).show();
                            }

                            else {
                                startActivity(new Intent(Login.this,MainActivity.class));
                            }
                        }
                    });
        }
    }

}
