package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentResolver;
import android.os.Bundle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.Validator;

import Modal.MainMeals;
import Modal.MealList;
import Util.CommonConstants;
import Util.CommonFunctions;

public class MM_MealManagement extends AppCompatActivity {

    private EditText mealName, foodType, normalPrice, largePrice, SerchTag;
    private CheckBox breakfast, lunch, dinner;
    private DatabaseReference fb;
    Dialog myDialog, myDialog2, myDialog3, myDialog4, myDialog5, myDialog6;
    Button addButton, deleteAll, addMeal, deleteAllfromDb, canselDAll, editDetails;
    ImageView edit, view, delete , upload, uplodedImage, serchIcon;
    private DatabaseReference df;
    private StorageReference storageReference;
    String primaryKey;
    ListView listView;
    ArrayList<MainMeals> list, mList;
    TextView header;
    TextView mealNameText, nPrice, lPrice, tFood, d1;
    private static final int PICK_FROM_GALLARY = 2;
    private Uri imageUri;
    private Validator validator;
    CardView search;
    List<MainMeals> mealsLists = new ArrayList<>();
    List<String> mealID = new ArrayList<>();
    MainMeals mmSer;
    TextView ID, name, type, lprice, nprice, headerDeletePU;
    CheckedTextView br,lu, dn;
    private ProgressBar progressBar, addImagePro;
    private ListView lv;
    String ImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__meal_management);

        storageReference = FirebaseStorage.getInstance().getReference("MainMealsImages");


        lv = (ListView) findViewById(R.id.mmList);



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
                                        Intent intent =  new Intent(MM_MealManagement.this,  MM_View_Meal_View.class);
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








        myDialog = new Dialog(this);
        addMeal = findViewById(R.id.addMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.activity_mm__add__main__meal__pu);
                TextView txtclose;
                myDialog.setContentView(R.layout.activity_mm__add__main__meal__pu);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                addImagePro = (ProgressBar) myDialog.findViewById(R.id.addPro);
                addImagePro.setVisibility(View.INVISIBLE);
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


                        mealName = myDialog.findViewById(R.id.mealName);
                        foodType = myDialog.findViewById(R.id.mealType);
                        normalPrice = myDialog.findViewById(R.id.normalPrice);
                        largePrice = myDialog.findViewById(R.id.largePrice);
                        breakfast = myDialog.findViewById(R.id.brakfast);
                        lunch = myDialog.findViewById(R.id.lunch);
                        dinner = myDialog.findViewById(R.id.dinner);
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
                            insetDataToDb();
                        }




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




    public void insetDataToDb(){



        addImagePro.setVisibility(View.VISIBLE);
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealsLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String id = ds.child("id").getValue().toString();
                    mealID.add(id);
                }

        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        fb = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        final MainMeals mainMeals = new MainMeals();
        /*primaryKey = CommonConstants.MAIN_MEALS_PREFIX + CommonConstants.MAIN_MEALS_ID;
        ++CommonConstants.MAIN_MEALS_ID;*/

        primaryKey = CommonFunctions.get_id(CommonConstants.MAIN_MEALS_PREFIX, mealsLists);



        mainMeals.setId(primaryKey);
        mainMeals.setMealName(mealName.getText().toString());
        mainMeals.setType(mealName.getText().toString());
        mainMeals.setNormalPrice(Float.parseFloat(normalPrice.getText().toString()));
        mainMeals.setLargePrice(Float.parseFloat(largePrice.getText().toString()));
        mainMeals.setBrakfast(breakfast.isChecked());
        mainMeals.setLunch(lunch.isChecked());
        mainMeals.setDinner(dinner.isChecked());
        mainMeals.setImageName(ImagePath);


        fb.child(mainMeals.getId()).setValue(mainMeals)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
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

        addImagePro.setVisibility(View.VISIBLE);
        if(requestCode == PICK_FROM_GALLARY && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            uplodedImage.setImageURI(imageUri);

            final StorageReference sf = storageReference.child("image" + imageUri.getLastPathSegment());
            sf.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ImagePath = uri.toString();

                        }
                    });
                }
            });



        }
        addImagePro.setVisibility(View.GONE);
    }





    @Override
    protected void onStart() {
        super.onStart();
        progressBar = findViewById(R.id.pro);
        progressBar.setVisibility(View.VISIBLE);
        df = FirebaseDatabase.getInstance().getReference().child("MainMeals");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealsLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    MainMeals mainMeals = ds.getValue(MainMeals.class);
                    mealsLists.add(mainMeals);
                }

                MealList mealList = new MealList(MM_MealManagement.this, mealsLists);

                lv.setAdapter(mealList);
                progressBar.setVisibility(View.GONE);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MainMeals mainMeals =(MainMeals)adapterView.getAdapter().getItem(i);

                        Intent intent =  new Intent(MM_MealManagement.this,  MM_View_Meal_View.class);
                        intent.putExtra("MainMeals", mainMeals);
                        startActivity(intent);



                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
