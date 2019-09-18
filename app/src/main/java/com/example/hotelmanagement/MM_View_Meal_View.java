package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import Modal.MainMeals;


public class MM_View_Meal_View extends AppCompatActivity {
    TextView ID, name, type, lprice, nprice, headerDeletePU;
    DatabaseReference df;
    Dialog myDialog2, myDialog4, myDialog5;
    Button editDetails, deleteAllfromDb, canselDAll;
    ImageView edit, view, delete, image, serchIcon;
    private EditText mealName, foodType, normalPrice, largePrice;
    private CheckBox breakfast, lunch, dinner;
    ImageView mealimage, back;
    CardView search;
    Dialog myDialog6;
    private EditText SerchTag;
    CheckedTextView br,lu, dn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__view__meal__view);


        back = (ImageView) findViewById(R.id.backToMa);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MM_View_Meal_View.this, MM_MealManagement.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        final MainMeals mainMeals = (MainMeals)intent.getSerializableExtra("MainMeals");

        mealimage = (ImageView) findViewById(R.id.meallImage);
        Glide.with(MM_View_Meal_View.this).load(mainMeals.getImageName()).into(mealimage);

        ID = findViewById(R.id.textView4);
        name = findViewById(R.id.name1);
        nprice = findViewById(R.id.price1);
        br = findViewById(R.id.brakfast);
        lu = findViewById(R.id.lunch);
        dn = findViewById(R.id.dinner);


        ID.setText(mainMeals.getId());
        name.setText(mainMeals.getMealName());
        nprice.setText("Rs " + mainMeals.getNormalPrice() + "0/-");

        if (mainMeals.isBrakfast() == true){
            br.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
        }
        if (mainMeals.isLunch() == true){
            lu.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
        }
        if (mainMeals.isDinner() == true){
            dn.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
        }


        myDialog2 = new Dialog(this);
        edit = findViewById(R.id.back);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                mealName = myDialog2.findViewById(R.id.mealName);
                foodType = myDialog2.findViewById(R.id.mealType);
                normalPrice = myDialog2.findViewById(R.id.normalPrice);
                largePrice = myDialog2.findViewById(R.id.largePrice);
                breakfast = myDialog2.findViewById(R.id.b);
                lunch = myDialog2.findViewById(R.id.l);
                dinner = myDialog2.findViewById(R.id.d);

                mealName.setText(mainMeals.getMealName());
                foodType.setText(mainMeals.getType());
                normalPrice.setText(mainMeals.getNormalPrice() + "0");
                largePrice.setText(mainMeals.getLargePrice() + "0");
                if(mainMeals.isBrakfast() == true){
                    breakfast.setChecked(true);
                }
                if(mainMeals.isLunch() == true){
                    lunch.setChecked(true);
                }
                if(mainMeals.isDinner() == true){
                    dinner.setChecked(true);
                }

                editDetails =(Button) myDialog2.findViewById(R.id.updateDetails);
                editDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(mealName.getText().toString().isEmpty()) {

                            mealName.setError("Please Enter Meal Name!");
                            mealName.clearFocus();

                        }else if(foodType.getText().toString().isEmpty()) {

                            foodType.setError("Please Enter Meal Type!");

                        }else if(normalPrice.getText().toString().isEmpty()) {

                            normalPrice.setError("Please Enter Meal Normal Price!");

                        }else if(Float.parseFloat(normalPrice.getText().toString()) == 0) {

                            normalPrice.setError("Normal Price Can Not Be 0!");

                        }else if(Float.parseFloat(normalPrice.getText().toString()) < 0) {

                            normalPrice.setError("Normal Price Can Not Be Negative!");

                        }else if(largePrice.getText().toString().isEmpty()) {

                            largePrice.setError("Please Enter Meal largePrice Price!");

                        }

                        else if(Float.parseFloat(largePrice.getText().toString()) == 0) {

                            largePrice.setError("Large Price Can Not Be 0!");

                        }else if(Float.parseFloat(largePrice.getText().toString()) < 0) {

                            largePrice.setError("Large Price Can Not Be Negative!");

                        }


                        else if(largePrice.getText().toString().isEmpty()) {

                            largePrice.setError("Please Enter Meal Large Price!");

                        }else if (breakfast.isChecked() == false && lunch.isChecked() == false && dinner.isChecked() == false){
                            Toast.makeText(getApplicationContext(), "Please Enter Meal Time!", Toast.LENGTH_LONG).show();
                            breakfast.setError("!");
                            lunch.setError("!");
                            dinner.setError("!");
                        }
                        else {
                            MainMeals mm = new MainMeals();
                            mm.setId(mainMeals.getId());
                            mm.setMealName(mealName.getText().toString());
                            mm.setType(foodType.getText().toString());
                            mm.setNormalPrice(Float.parseFloat(normalPrice.getText().toString()));
                            mm.setLargePrice(Float.parseFloat(largePrice.getText().toString()));
                            mm.setBrakfast(breakfast.isChecked());
                            mm.setLunch(lunch.isChecked());
                            mm.setDinner(dinner.isChecked());

                            df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child(mm.getId());
                            df.setValue(mm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MM_View_Meal_View.this, MM_MealManagement.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Data Not Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MM_View_Meal_View.this, MM_MealManagement.class);
                                        startActivity(intent);
                                    }

                                }
                            });
                        }
                    }
                });

            }
        });


        delete = findViewById(R.id.imageView);
        myDialog4 = new Dialog(this);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView txtclose;
                myDialog4.setContentView(R.layout.activity_mm__delete__all__pu);
                headerDeletePU = myDialog4.findViewById(R.id.header);
                headerDeletePU.setText("Are You Want To delete");
                txtclose =(TextView) myDialog4.findViewById(R.id.txtclose3);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog4.dismiss();
                    }
                });
                myDialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog4.show();


                deleteAllfromDb = myDialog4.findViewById(R.id.button5);
                deleteAllfromDb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteMainMeal(mainMeals.getId());
                    }
                });

                canselDAll = myDialog4.findViewById(R.id.button4);
                canselDAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog4.dismiss();
                    }
                });






            }
        });

        view = findViewById(R.id.imageView3);
        myDialog5 = new Dialog(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog5.setContentView(R.layout.activity_mm__main__meal__view__pu);
                myDialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog5.show();

                name = myDialog5.findViewById(R.id.mealName);
                nprice = myDialog5.findViewById(R.id.regularPrice);
                lprice = myDialog5.findViewById(R.id.largePrice);
                br = myDialog5.findViewById(R.id.brakfast);
                lu = myDialog5.findViewById(R.id.lunch);
                dn = myDialog5.findViewById(R.id.dinner);
                type = myDialog5.findViewById(R.id.foodType);
                image = myDialog5.findViewById(R.id.image1);

                name.setText(mainMeals.getMealName());
                type.setText(mainMeals.getType());
                nprice.setText("RS - " + mainMeals.getNormalPrice() + "0");
                lprice.setText("RS - " + mainMeals.getLargePrice() + "0");
                if(mainMeals.isBrakfast() == true){
                    br.setCheckMarkDrawable(R.drawable.ic_check_circle_gold_24dp);
                }
                if(mainMeals.isLunch() == true){
                    lu.setCheckMarkDrawable(R.drawable.ic_check_circle_gold_24dp);
                }
                if(mainMeals.isDinner() == true){
                    dn.setCheckMarkDrawable(R.drawable.ic_check_circle_gold_24dp);
                }
                Glide.with(MM_View_Meal_View.this).load(mainMeals.getImageName()).into(mealimage);

            }
        });



        myDialog6 = new Dialog(this);
        search = findViewById(R.id.searchCard);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myDialog6.setContentView(R.layout.activity_mm__search__bar);
                myDialog6.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog6.show();
                final ProgressBar proSerch = myDialog6.findViewById(R.id.pro);
                proSerch.setVisibility(View.INVISIBLE);

                serchIcon = myDialog6.findViewById(R.id.imageView5);


                serchIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        SerchTag = myDialog6.findViewById(R.id.offerName);


                        String id =  SerchTag.getText().toString();
                        if(id.isEmpty()){
                            SerchTag.setError("");
                            Toast.makeText(getApplicationContext(), "Please Enter Key For Search", Toast.LENGTH_LONG).show();
                        }
                        else {
                            proSerch.setVisibility(View.VISIBLE);
                            df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child(id);
                            df.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    MainMeals mainMeals = dataSnapshot.getValue(MainMeals.class);

                                    if(mainMeals != null){
                                        proSerch.setVisibility(View.GONE);
                                        Intent intent =  new Intent(MM_View_Meal_View.this,  MM_View_Meal_View.class);
                                        intent.putExtra("MainMeals", mainMeals);
                                        startActivity(intent);
                                    }else {
                                        proSerch.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Please Enter Valid Id!", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }





                    }



                });





            }
        });



    }

    public void DeleteMainMeal(String id){
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child(id);
        df.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_View_Meal_View.this, MM_MealManagement.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_View_Meal_View.this, MM_MealManagement.class);
                    startActivity(intent);
                }

            }
        });
    }
}
