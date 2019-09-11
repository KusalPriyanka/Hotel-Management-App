package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modal.MainMeals;

public class MM_MealManagement extends AppCompatActivity {

    Dialog myDialog, myDialog2, myDialog3;
    Button addButton, deleteAll;
    ImageView imageView, view, delete;
    private DatabaseReference df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__meal_management);

        myDialog = new Dialog(this);
        addButton = findViewById(R.id.addMeal);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*myDialog.setContentView(R.layout.activity_mm__add__main__meal__pu);
                TextView txtclose;
                myDialog.setContentView(R.layout.activity_mm__add__main__meal__pu);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();*/

                Intent intent = new Intent(MM_MealManagement.this, MM_Add_Main_Meal_PU.class);
                startActivity(intent);

            }
        });

        myDialog2 = new Dialog(this);
        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog2.setContentView(R.layout.activity_mm__edit__meal__pu);
                TextView txtclose1;
                myDialog2.setContentView(R.layout.activity_mm__edit__meal__pu);
                txtclose1 =(TextView) myDialog2.findViewById(R.id.txtclose1);
                txtclose1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog2.dismiss();
                    }
                });
                myDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog2.show();

            }
        });

        /*
        delete = findViewById(R.id.button3);
        myDialog3 = new Dialog(this);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog3.setContentView(R.layout.activity_delete__all);
                TextView txtclose;
                myDialog3.setContentView(R.layout.activity_delete__all);
                txtclose =(TextView) myDialog3.findViewById(R.id.txtclose3);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog3.dismiss();
                    }
                });
                myDialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog3.show();

            }
        });

        view = findViewById(R.id.imageView3);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Meal_Management.this, View_Popup.class);
                startActivity(intent);
            }
        });*/




        deleteAll = findViewById(R.id.deleteAll);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAllMainMeals();
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteMainMeal();
            }
        });


    }


    public void DeleteAllMainMeals(){
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        df.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "All Meals are Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void DeleteMainMeal(){
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child("1");
        df.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                    startActivity(intent);
                }

            }
        });
    }


}
