package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class EM_HallView extends AppCompatActivity {
    ViewFlipper vievfliper1, vievfliper2, vievfliper3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__hall_view);

        vievfliper1 = findViewById(R.id.blueRay);
        int image[] = {R.drawable.emblueone, R.drawable.emblutwo};
        for(int i = 0; i < image.length; i++){
            showSlider1(image[i]);
        }

        vievfliper2 = findViewById(R.id.redview);
        int image2[] = {R.drawable.emredone, R.drawable.emredtwo};
        for(int i = 0; i < image.length; i++){
            showSlider2(image2[i]);
        }

        vievfliper3 = findViewById(R.id.royal);
        int image3[] = {R.drawable.emluxone, R.drawable.emluxtwo};
        for(int i = 0; i < image.length; i++){
            showSlider3(image3[i]);
        }

    }

    public void showSlider1(int image){
        ImageView im = new ImageView(this);
        im.setBackgroundResource(image);
        vievfliper1.setAutoStart(true);
        vievfliper1.setFlipInterval(3000);
        vievfliper1.addView(im);


        vievfliper1.setInAnimation(this, android.R.anim.slide_in_left);
        vievfliper1.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    public void showSlider2(int image){
        ImageView im = new ImageView(this);
        im.setBackgroundResource(image);
        vievfliper2.setAutoStart(true);
        vievfliper2.setFlipInterval(3000);
        vievfliper2.addView(im);


        vievfliper2.setInAnimation(this, android.R.anim.slide_in_left);
        vievfliper2.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    public void showSlider3(int image){
        ImageView im = new ImageView(this);
        im.setBackgroundResource(image);
        vievfliper3.setAutoStart(true);
        vievfliper3.setFlipInterval(3000);
        vievfliper3.addView(im);


        vievfliper3.setInAnimation(this, android.R.anim.slide_in_left);
        vievfliper3.setOutAnimation(this,android.R.anim.slide_out_right);
    }
}






