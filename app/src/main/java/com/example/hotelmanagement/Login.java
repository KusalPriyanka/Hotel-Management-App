package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private Button login;
    private TextView signup;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        username = findViewById(R.id.username);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((username.getText().toString()).equalsIgnoreCase("admin")){
                    Intent intent = new Intent(Login.this,offer_management.class);
                    startActivity(intent);

                }
                else{
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }


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
}
