package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Modal.EM_HallManagement;
import de.hdodenhof.circleimageview.CircleImageView;

public class EM_UpdatedView extends AppCompatActivity {
    TextView passedHallname,passedHallprice,passedHalltype,passedhallid;
    CheckedTextView passedWed,passedEvent;
    EM_HallManagement em_hallManagement1;
    Button update, delete, Ddelete, Dcansel;
    ImageView back;
    CircleImageView emcircle;
    private Dialog deleteHallsDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em__updated_view);



        Intent intent = getIntent();
        em_hallManagement1 = (EM_HallManagement) intent.getSerializableExtra("em_hallManagement");
        update = findViewById(R.id.udateviewbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_UpdatedView.this, EM_EventDetails.class);
                intent.putExtra("em_hallManagement", em_hallManagement1);
                startActivity(intent);
            }
        });

        passedhallid = findViewById(R.id.textVieweid);
        passedHallname = findViewById(R.id.viewname);
        passedHallprice = findViewById(R.id.viewprice);
        passedHalltype = findViewById(R.id.viewdes);
        passedWed = findViewById(R.id.wed);
        passedEvent = findViewById(R.id.event);
        emcircle = findViewById(R.id.imageView3);






                    passedhallid.setText(em_hallManagement1.getId());
                    passedHallname.setText(em_hallManagement1.getName());
                    passedHallprice.setText(em_hallManagement1.getPrice()+"");
                    passedHalltype.setText(em_hallManagement1.getDescription());
                    if((Boolean) em_hallManagement1.isWedding()== true){
                        passedWed.setCheckMarkDrawable(R.drawable.tick4);

                    }
                    if((Boolean) em_hallManagement1.isEvents() == true){
                        passedEvent.setCheckMarkDrawable(R.drawable.tick4);

                    }

                    Glide.with(EM_UpdatedView.this).load(em_hallManagement1.getImageName()).into(emcircle);





        back = findViewById(R.id.backAdd);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EM_UpdatedView.this, EM_Addhalls.class);
                startActivity(intent);
            }
        });


        deleteHallsDialog = new Dialog(this);
        delete = findViewById(R.id.deletebtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteHallsDialog.setContentView(R.layout.em_popup_deleteall);
                deleteHallsDialog.setCanceledOnTouchOutside(true);
                deleteHallsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteHallsDialog.show();


                 Ddelete= deleteHallsDialog.findViewById(R.id.empopdel);
                Ddelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteHalls();
                    }
                });

                Dcansel = deleteHallsDialog.findViewById(R.id.empopcancel);
                Dcansel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteHallsDialog.dismiss();
                    }
                });
            }
        });













    }

    public void deleteHalls(){
        DatabaseReference deletehalldbf = FirebaseDatabase.getInstance().getReference().child("EM_HallManagement").child(em_hallManagement1.getId());
        deletehalldbf.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Selected Hall is Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EM_UpdatedView.this, EM_Addhalls.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EM_UpdatedView.this, EM_Addhalls.class);
                    startActivity(intent);
                }

            }
        });

    }
}

