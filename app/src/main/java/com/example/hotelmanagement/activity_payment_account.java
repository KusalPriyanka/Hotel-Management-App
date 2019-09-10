package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_payment_account extends AppCompatActivity {

    Button b1, b2;
    EditText cName, cNo;
    TextView editText4;
    DatabaseReference dbref;
    PayDetails finalPay,pd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_account);

        b1 = (Button)findViewById(R.id.payUpdate);
        b2 = findViewById(R.id.submit);
        editText4=findViewById(R.id.editText4);
        cName = findViewById(R.id.cName);
        cNo = findViewById(R.id.cNo);
        finalPay = new PayDetails();
        pd2 = new PayDetails();


        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("pd2");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    editText4.setText(dataSnapshot.child("spin").getValue().toString());

                }
                else
                    Toast.makeText(getApplicationContext(), "No Sourse To Display", Toast.LENGTH_SHORT);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity_payment_account.this, activity_update__details.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(activity_payment_account.this);

                builder.setMessage("Are You Sure?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");


                                try {
                                    if (TextUtils.isEmpty(cName.getText().toString()))
                                        Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_SHORT).show();

                                    else if (TextUtils.isEmpty(cNo.getText().toString()))
                                        Toast.makeText(getApplicationContext(), "Please enter Card", Toast.LENGTH_SHORT).show();

                                    else {

                                        finalPay.setcName(cName.getText().toString().trim());
                                        finalPay.setcNo(cNo.getText().toString().trim());


                                        dbref.child("p1").setValue(finalPay);

                                        Toast.makeText(getApplicationContext(), "Data Added Successfully", Toast.LENGTH_SHORT).show();
                                        clearControls();

                                        Intent intent3 = new Intent(activity_payment_account.this,MainActivity.class);
                                        startActivity(intent3);
                                    }
                                }
                                catch (NumberFormatException e){
                                    Toast.makeText(getApplicationContext(),"Invalid Contact No", Toast.LENGTH_SHORT).show();

                                }


                            }
                        }).setNegativeButton("Cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    public void clearControls(){
        cName.setText("");
        cNo.setText("");

    }

}
