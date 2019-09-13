package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modal.MainMeals;
import Util.CommonConstants;

public class MM_MealManagement extends AppCompatActivity {

    private EditText mealName, foodType, normalPrice, largePrice;
    private CheckBox breakfast, lunch, dinner;
    private DatabaseReference fb;
    Dialog myDialog, myDialog2, myDialog3, myDialog4, myDialog5, myDialog6;
    Button addButton, deleteAll, addMeal, deleteAllfromDb, canselDAll, editDetails;
    ImageView edit, view, delete , search, upload, uplodedImage;
    private DatabaseReference df;
    String primaryKey;
    ListView listView;
    ArrayList<MainMeals> list, mList;
    TextView header;
    TextView mealNameText, nPrice, lPrice, tFood, d1;
    private static final int PICK_FROM_GALLARY = 2;
    private Uri imageUri;

    CardView serch;

    List<MainMeals> mealsLists = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__meal_management);

        listView = findViewById(R.id.list);

        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        final FirebaseListAdapter<MainMeals> adapter = new FirebaseListAdapter<MainMeals>(
                this,
                MainMeals.class,
                android.R.layout.simple_list_item_1,
                df

        ) {
            @Override
            protected void populateView(View v, MainMeals model, int position) {
                TextView textView = v.findViewById(android.R.id.text1);
                textView.setText(model.toString());
            }
        };

        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainMeals mainMeals =(MainMeals)adapterView.getAdapter().getItem(i);
                Intent intent =  new Intent(MM_MealManagement.this,  MM_View_Meal_View.class);
                intent.putExtra("MainMeals", mainMeals);
                startActivity(intent);
            }
        });




/*
        myDialog6 = new Dialog(this);
        search = findViewById(R.id.imageView5);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog6.setContentView(R.layout.activity_mm__edit__meal__pu);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });*/








        myDialog = new Dialog(this);
        addMeal = findViewById(R.id.addMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.activity_mm__add__main__meal__pu);
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
                        //insetDataToDb();

                        mealName = myDialog.findViewById(R.id.mealName);
                        foodType = myDialog.findViewById(R.id.mealType);
                        normalPrice = myDialog.findViewById(R.id.normalPrice);
                        largePrice = myDialog.findViewById(R.id.largePrice);
                        breakfast = myDialog.findViewById(R.id.brakfast);
                        lunch = myDialog.findViewById(R.id.lunch);
                        dinner = myDialog.findViewById(R.id.dinner);
                        insetDataToDb();

                    }
                });


            }
        });

        myDialog2 = new Dialog(this);
        edit = findViewById(R.id.imageView2);
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


                df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child("MM-01");
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            mealName.setText(dataSnapshot.child("mealName").getValue().toString());
                            foodType.setText(dataSnapshot.child("type").getValue().toString());
                            normalPrice.setText(dataSnapshot.child("normalPrice").getValue().toString());
                            largePrice.setText(dataSnapshot.child("largePrice").getValue().toString());
                            if((Boolean) dataSnapshot.child("brakfast").getValue() == true){
                                breakfast.setChecked(true);
                            }
                            if((Boolean)dataSnapshot.child("lunch").getValue() == true){
                                lunch.setChecked(true);
                            }
                            if((Boolean) dataSnapshot.child("dinner").getValue() == true){
                                dinner.setChecked(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                editDetails =(Button) myDialog2.findViewById(R.id.updateDetails);
                editDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainMeals mainMeals = new MainMeals();
                        mainMeals.setId("MM-01");
                        mainMeals.setMealName(mealName.getText().toString());
                        mainMeals.setType(foodType.getText().toString());
                        mainMeals.setNormalPrice(Float.parseFloat(normalPrice.getText().toString()));
                        mainMeals.setLargePrice(Float.parseFloat(largePrice.getText().toString()));
                        mainMeals.setBrakfast(breakfast.isChecked());
                        mainMeals.setLunch(lunch.isChecked());
                        mainMeals.setDinner(dinner.isChecked());

                        df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child("MM-01");
                        df.setValue(mainMeals).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Data Not Updated Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                                    startActivity(intent);
                                }

                            }
                        });
                    }
                });

            }
        });

        deleteAll = findViewById(R.id.deleteAll);
        myDialog3 = new Dialog(this);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog3.setContentView(R.layout.activity_mm__delete__all__pu);
                TextView txtclose;
                myDialog3.setContentView(R.layout.activity_mm__delete__all__pu);
                txtclose =(TextView) myDialog3.findViewById(R.id.txtclose3);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog3.dismiss();
                    }
                });
                myDialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog3.show();


                deleteAllfromDb = myDialog3.findViewById(R.id.button5);
                deleteAllfromDb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteAllMainMeals();
                    }
                });

                canselDAll = myDialog3.findViewById(R.id.button4);
                canselDAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog3.dismiss();
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
                header = myDialog4.findViewById(R.id.header);
                header.setText("Are You Want To delete");
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
                        DeleteMainMeal();
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

                mealNameText = myDialog5.findViewById(R.id.mealName);
                nPrice = myDialog5.findViewById(R.id.regularPrice);
                lPrice = myDialog5.findViewById(R.id.largePrice);
                d1 = myDialog5.findViewById(R.id.txt3);
                tFood = myDialog5.findViewById(R.id.foodType);

                df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child("MM-01");
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            StringBuilder available = new StringBuilder();
                            mealNameText.setText(dataSnapshot.child("mealName").getValue().toString());
                            tFood.setText(dataSnapshot.child("type").getValue().toString());
                            nPrice.setText(dataSnapshot.child("normalPrice").getValue().toString() + "/-");
                            lPrice.setText(dataSnapshot.child("largePrice").getValue().toString() + "/-");
                            if((Boolean) dataSnapshot.child("brakfast").getValue() == true){
                                available.append("Breakfast | ");
                            }
                            if((Boolean)dataSnapshot.child("lunch").getValue() == true){
                                available.append("Lunch | ");
                            }
                            if((Boolean) dataSnapshot.child("dinner").getValue() == true){
                                available.append("Dinner");
                            }
                            d1.setText(available);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });








            }
        });
/*
        list = readAllMainMeal();
        System.out.println("====================================================upper" + list.size());

        for(MainMeals M : list){
            System.out.println(M);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);*/

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
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child("MM-01");
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


    public void insetDataToDb(){


        fb = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        primaryKey = CommonConstants.MAIN_MEALS_PREFIX + CommonConstants.MAIN_MEALS_ID;
        ++CommonConstants.MAIN_MEALS_ID;


        MainMeals mainMeals = new MainMeals();
        mainMeals.setId(primaryKey);
        mainMeals.setMealName(mealName.getText().toString());
        mainMeals.setType(mealName.getText().toString());
        mainMeals.setNormalPrice(Float.parseFloat(normalPrice.getText().toString()));
        mainMeals.setLargePrice(Float.parseFloat(largePrice.getText().toString()));
        mainMeals.setBrakfast(breakfast.isChecked());
        mainMeals.setLunch(lunch.isChecked());
        mainMeals.setDinner(dinner.isChecked());

        System.out.println(mainMeals.getMealName());
        System.out.println(mainMeals);



        fb.child(mainMeals.getId()).setValue(mainMeals)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Data Not Inserted Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MM_MealManagement.this, MM_MealManagement.class);
                            startActivity(intent);
                        }

                    }
                });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FROM_GALLARY && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            uplodedImage.setImageURI(imageUri);
        }
    }

    /*public void read(){
        final ArrayList<MainMeals> mL = new ArrayList<MainMeals>();
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    MainMeals mm = ds.getValue(MainMeals.class);
                    mL.add(mm);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for (MainMeals mainMeals : mL){
            System.out.println("#########################################");
            System.out.println(mainMeals);
            System.out.println("#########################################");
        }

        Toast.makeText(getApplicationContext(), mealsLists.size()+"", Toast.LENGTH_LONG).show();
    }*/








}
