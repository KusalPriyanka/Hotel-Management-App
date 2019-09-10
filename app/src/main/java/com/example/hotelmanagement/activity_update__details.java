package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.YuvImage;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class activity_update__details extends AppCompatActivity {

    Spinner sp1;
    TextView ptime, pdate;
    DatePickerDialog.OnDateSetListener setListener;
    PayDetails pd2,vehicle,place;
    Button update, delete;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__details);

        sp1 = (Spinner)findViewById(R.id.spinner1);
        ptime = findViewById(R.id.ptime);
        pdate = findViewById(R.id.pdate);
        update = findViewById(R.id.Update);
        delete = findViewById(R.id.Delete);

        pd2 = new PayDetails();
        vehicle = new PayDetails();
        place = new PayDetails();

        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("pd2");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    ptime.setText(dataSnapshot.child("ptime").getValue().toString());
                    pdate.setText(dataSnapshot.child("pdate").getValue().toString());
                    pd2.setSpin(sp1.getSelectedItem().toString().trim());
                }
                else
                    Toast.makeText(getApplicationContext(), "No Sourse To Display", Toast.LENGTH_SHORT);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("pd2");
                            dbRef.removeValue();
                            dbRef = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("vehicle");
                            dbRef.removeValue();
                            dbRef = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("place");
                            dbRef.removeValue();

                            Toast.makeText(getApplicationContext(), "Deleted SUccessfully", Toast.LENGTH_SHORT).show();
                            clearControls();

                            Intent a = new Intent(activity_update__details.this, MainActivity.class);
                            startActivity(a);

                        }
                        else
                            Toast.makeText(getApplicationContext(), "Invalid Detials", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("PayDetails");
                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("pd2"));
                        try {
                            pd2.setPtime(ptime.getText().toString().trim());
                            pd2.setPdate(pdate.getText().toString().trim());
                            pd2.setSpin(sp1.getSelectedItem().toString().trim());

                            dbRef = FirebaseDatabase.getInstance().getReference().child("PayDetails").child("pd2");
                            dbRef.setValue(pd2);

                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();


                            Intent a = new Intent(activity_update__details.this, activity_payment_account.class);
                            startActivity(a);
                        }
                        catch (NumberFormatException e){
                            Toast.makeText(getApplicationContext(), "InValid Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1.setAdapter(adapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        pdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_update__details.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" +month + "/" +year;
                pdate.setText(date);
            }
        };


        ptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int hourOfDay = 0;
                final int minute;
                int is24HourView;

                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_update__details.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        ptime.setText(hourOfDay + ":" + minute);
                    }

                },hourOfDay,0,false);

                timePickerDialog.show();
            }
        });


    }

    public void clearControls(){
        pdate.setText("");
        ptime.setText("");

    }
}
