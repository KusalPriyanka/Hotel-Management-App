package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class reservation_s1 extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText checkin = findViewById(R.id.checkindate);
    EditText checkout = findViewById(R.id.checkoutdate);

    DatePickerDialog.OnDateSetListener checkInDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH,i2);
            updateCheckInDate(0);
        }
    };

    DatePickerDialog.OnDateSetListener checkOutDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH,i2);
            updateCheckInDate(1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_s1);


    }

    public void checkIn(View v){

        new DatePickerDialog(this,checkInDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void checkOut(View v){

        new DatePickerDialog(this,checkOutDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void updateCheckInDate(int status){

        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);

        if(status == 0){
            checkin.setText(sdf.format(myCalendar.getTime()));
        }
        else if(status == 1){
            checkout.setText(sdf.format(myCalendar.getTime()));
        }


    }
}
