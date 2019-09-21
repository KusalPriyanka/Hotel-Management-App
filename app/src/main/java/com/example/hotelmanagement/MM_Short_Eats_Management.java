package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import Modal.MainMeals;
import Modal.ShortEats;
import Util.CommonConstants;
import Util.CommonFunctions;

public class MM_Short_Eats_Management extends AppCompatActivity {

    private EditText mealName, foodType, normalPrice, largePrice, SerchTag;
    private CheckBox breakfast, lunch, dinner;
    private Dialog myDialog, myDialog3, myDialog6;
    private Button addButton, deleteAll, addMeal, deleteAllfromDb, canselDAll;
    private ImageView view , upload, uplodedImage, serchIcon;
    private DatabaseReference df;
    private StorageReference storageReference;
    private String primaryKey;
    private static final int PICK_FROM_GALLARY = 2;
    private Uri imageUri;
    private CardView search;
    private List<ShortEats> mealsLists = new ArrayList<>();
    private List<String> mealID = new ArrayList<>();
    private ProgressBar progressBar, addImagePro;
    private ListView lv;
    private String ImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__short__eats__management);




        myDialog = new Dialog(this);
        addMeal = findViewById(R.id.addMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtclose;
                myDialog.setContentView(R.layout.mm_add_short_eats_pu);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                addImagePro = (ProgressBar) myDialog.findViewById(R.id.addPro);
                addImagePro.setVisibility(View.INVISIBLE);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.setCanceledOnTouchOutside(false);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                upload = myDialog.findViewById(R.id.upload);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =  new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, PICK_FROM_GALLARY);
                    }

                });

                uplodedImage = myDialog.findViewById(R.id.uplodedImage);


                addButton = (Button) myDialog.findViewById(R.id.addMealDB);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        mealName = myDialog.findViewById(R.id.mealName);
                        normalPrice = myDialog.findViewById(R.id.normalPrice);
                        if(mealName.getText().toString().isEmpty()) {

                            mealName.setError("Please Enter Meal Name!");

                        }else if(normalPrice.getText().toString().isEmpty()) {

                            normalPrice.setError("Please Enter Meal Normal Price!");

                        }else if(Float.parseFloat(normalPrice.getText().toString()) == 0) {

                            normalPrice.setError("Normal Price Can Not Be 0!");

                        }else if(Float.parseFloat(normalPrice.getText().toString()) < 0) {

                            normalPrice.setError("Normal Price Can Not Be Negative!");

                        }

                        else {
                           insetDataToDb();
                        }




                    }
                });


            }
        });
    }

    public void insetDataToDb(){

        addImagePro.setVisibility(View.VISIBLE);
        df = FirebaseDatabase.getInstance().getReference().child("ShortEats");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealsLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ShortEats shortEats = ds.getValue(ShortEats.class);
                    mealsLists.add(shortEats);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // primaryKey = CommonFunctions.get_id(CommonConstants.MAIN_MEALS_PREFIX, mealsLists);
        final MainMeals mainMeals = new MainMeals();

        mainMeals.setId(primaryKey);
        mainMeals.setMealName(mealName.getText().toString());
        mainMeals.setType(mealName.getText().toString());
        mainMeals.setNormalPrice(Float.parseFloat(normalPrice.getText().toString()));
        mainMeals.setLargePrice(Float.parseFloat(largePrice.getText().toString()));
        mainMeals.setBrakfast(breakfast.isChecked());
        mainMeals.setLunch(lunch.isChecked());
        mainMeals.setDinner(dinner.isChecked());
        mainMeals.setImageName(ImagePath);

        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        df.child(mainMeals.getId()).setValue(mainMeals)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MM_Short_Eats_Management.this, MM_Short_Eats_Management.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Data Not Inserted Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MM_Short_Eats_Management.this, MM_Short_Eats_Management.class);
                            startActivity(intent);
                        }

                    }
                });




    }

}
