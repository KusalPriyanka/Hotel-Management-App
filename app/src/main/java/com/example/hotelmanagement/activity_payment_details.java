package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class activity_payment_details extends AppCompatActivity {

    Button button1;
    Spinner spinner;
    TextView pdate,ptime,textView4;

    DatabaseReference dbref;
    PayDetails pd2;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        button1 = findViewById(R.id.button1);
        spinner = (Spinner)findViewById(R.id.spinner);
        ptime = findViewById(R.id.ptime);
        pdate = findViewById(R.id.pdate);
        // textView4 = findViewById(R.id.textView4);


        pd2 = new PayDetails();


        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("PayDetails");


                try {
                    if (TextUtils.isEmpty(ptime.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter time", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(ptime.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter date", Toast.LENGTH_SHORT).show();

                    else {


                        pd2.setPdate(pdate.getText().toString().trim());
                        pd2.setPtime(ptime.getText().toString().trim());
                        pd2.setSpin(spinner.getSelectedItem().toString().trim());
                        dbref.child("pd2").setValue(pd2);

                        Toast.makeText(getApplicationContext(), "Data Added Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();

                        Intent intent3 = new Intent(activity_payment_details.this,activity_payment_account.class);
                        startActivity(intent3);
                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Contact No", Toast.LENGTH_SHORT).show();

                }



            }
        });

        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        pdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_payment_details.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_payment_details.this, new TimePickerDialog.OnTimeSetListener() {
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
