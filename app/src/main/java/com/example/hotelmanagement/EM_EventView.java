package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class EM_EventView extends AppCompatActivity {

    ViewFlipper vievfliper;
    Button viewlight, viewCoffe,viewPeo,viewCalm;
    TextView txh11,txf11,txf22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__event_view);





        vievfliper = findViewById(R.id.file);
        int image[] = {R.drawable.emnewfiv, R.drawable.emnewone, R.drawable.emnewtwo,R.drawable.emfirstsix};
        for(int i = 0; i < image.length; i++){
            showSlider(image[i]);
        }


        viewlight = findViewById(R.id.viewLight);
        viewlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txh11 = findViewById(R.id.txh1);
                txf11 = findViewById(R.id.txf1);
                txf22 = findViewById(R.id.txf2);

                String first_head = txh11.getText().toString();
                String first_sone = txf11.getText().toString();
                String first_stwo = txf22.getText().toString();


                Intent intent = new Intent(EM_EventView.this, EM_EventDetails.class);

                intent.putExtra("first_head",first_head );
                intent.putExtra("first_sone",first_sone );
                intent.putExtra("first_stwo",first_stwo );

                Bundle bundle = new Bundle();
                bundle.putInt("image",R.drawable.emnewfiv);
                intent.putExtras(bundle);


                startActivity(intent);
            }
        });


        viewCoffe = findViewById(R.id.viewCoffee);
        viewCoffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txh11 = findViewById(R.id.txh2);
                txf11 = findViewById(R.id.txs1);
                txf22 = findViewById(R.id.txs2);

                String first_head = txh11.getText().toString();
                String first_sone = txf11.getText().toString();
                String first_stwo = txf22.getText().toString();


                Intent intent = new Intent(EM_EventView.this, EM_EventDetails.class);

                intent.putExtra("first_head",first_head );
                intent.putExtra("first_sone",first_sone );
                intent.putExtra("first_stwo",first_stwo );

                Bundle bundle = new Bundle();
                bundle.putInt("image",R.drawable.emnewone);
                intent.putExtras(bundle);


                startActivity(intent);
            }
        });

        viewPeo = findViewById(R.id.viewPeo);
        viewPeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txh11 = findViewById(R.id.th3);
                txf11 = findViewById(R.id.txt1);
                txf22 = findViewById(R.id.txt2);

                String first_head = txh11.getText().toString();
                String first_sone = txf11.getText().toString();
                String first_stwo = txf22.getText().toString();


                Intent intent = new Intent(EM_EventView.this, EM_EventDetails.class);

                intent.putExtra("first_head",first_head );
                intent.putExtra("first_sone",first_sone );
                intent.putExtra("first_stwo",first_stwo );

                Bundle bundle = new Bundle();
                bundle.putInt("image",R.drawable.emnewtwo);
                intent.putExtras(bundle);


                startActivity(intent);
            }
        });

        viewCalm = findViewById(R.id.viewCalm);
        viewCalm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txh11 = findViewById(R.id.txh3);
                txf11 = findViewById(R.id.txfo1);
                txf22 = findViewById(R.id.txfo2);

                String first_head = txh11.getText().toString();
                String first_sone = txf11.getText().toString();
                String first_stwo = txf22.getText().toString();


                Intent intent = new Intent(EM_EventView.this, EM_EventDetails.class);

                intent.putExtra("first_head",first_head );
                intent.putExtra("first_sone",first_sone );
                intent.putExtra("first_stwo",first_stwo );

                Bundle bundle = new Bundle();
                bundle.putInt("image",R.drawable.emfirstsix);
                intent.putExtras(bundle);


                startActivity(intent);
            }
        });


    }




    public void showSlider(int image){
        ImageView im = new ImageView(this);
        im.setBackgroundResource(image);
        vievfliper.setAutoStart(true);
        vievfliper.setFlipInterval(3000);
        vievfliper.addView(im);


        vievfliper.setInAnimation(this, android.R.anim.slide_in_left);
        vievfliper.setOutAnimation(this,android.R.anim.slide_out_right);
    }
}
