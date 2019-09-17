package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView edit, view, delete;
    private EditText mealName, foodType, normalPrice, largePrice;
    private CheckBox breakfast, lunch, dinner;

    CheckedTextView br,lu, dn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__view__meal__view);


        Intent intent = getIntent();
        final MainMeals mainMeals = (MainMeals)intent.getSerializableExtra("MainMeals");

        ID = findViewById(R.id.textView4);
        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        nprice = findViewById(R.id.normalPrice);
        lprice = findViewById(R.id.largePrice);
        br = findViewById(R.id.brakfastCh);
        lu = findViewById(R.id.lunchCh);
        dn = findViewById(R.id.dinnerCh);


        ID.setText(mainMeals.getId());
        name.setText(mainMeals.getMealName());
        type.setText(mainMeals.getType());
        nprice.setText("NP : Rs " + mainMeals.getNormalPrice() + "/-");
        lprice.setText("LP : Rs " + mainMeals.getLargePrice() + "/-");

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
                normalPrice.setText(mainMeals.getNormalPrice() + "");
                largePrice.setText(mainMeals.getLargePrice() + "");
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
