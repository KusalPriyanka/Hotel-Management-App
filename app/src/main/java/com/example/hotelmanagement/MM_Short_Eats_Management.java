package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import Modal.MainMeals;
import Modal.ShortEats;
import Util.CommonConstants;
import Util.CommonFunctions;
import Util.ShortEatsAdapter;

public class MM_Short_Eats_Management extends AppCompatActivity {
    private ProgressDialog pd;
    private ViewPager pastryVP;
    private DatabaseReference df;
    private ShortEatsAdapter shortEatsAdapter;
    private Button addMeal, addButton, deleteAll, deleteAllfromDb, canselDAll;
    private List<ShortEats> mealsLists = new ArrayList<>();
    private List<String> idList = new ArrayList<>();
    private Dialog myDialog, myDialog3;
    private EditText name, price;
    private CheckBox pastry, pizza, drinks;
    private ImageView uplodedImage, upload;
    private TextView uploadText, deleteHeader;
    private static final int PICK_FROM_GALLARY = 2;
    private ProgressDialog progressDialog;
    private String primaryKey, ImagePath;
    private ShortEats shortEats;
    private Uri imageUri;
    private StorageReference storageReference;
    private Dialog myDialog6;
    private CardView search;
    private EditText SerchTag;
    private ImageView serchIcon;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__short__eats__management);

        storageReference = FirebaseStorage.getInstance().getReference("PastryShopImages");

        progressDialog = new ProgressDialog(this);
        myDialog = new Dialog(this);
        addMeal = findViewById(R.id.addMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtclose;
                myDialog.setContentView(R.layout.mm_add_short_eat_pu);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
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


                        name = myDialog.findViewById(R.id.name);
                        price = myDialog.findViewById(R.id.price);
                        pastry = myDialog.findViewById(R.id.pastry);
                        pizza = myDialog.findViewById(R.id.pizza);
                        drinks = myDialog.findViewById(R.id.drinks);
                        uploadText  = myDialog.findViewById(R.id.textView25);
                        if(name.getText().toString().isEmpty()) {

                            name.setError("Please Enter Meal Name!");


                        }else if(price.getText().toString().isEmpty()) {

                            price.setError("Please Enter Price!");

                        }else if(Float.parseFloat(price.getText().toString()) == 0) {

                            price.setError("Normal Price Can Not Be 0!");

                        }else if(Float.parseFloat(price.getText().toString()) < 0) {

                            price.setError("Normal Price Can Not Be Negative!");

                        }else if (pastry.isChecked() == false && pizza.isChecked() == false && drinks.isChecked() == false){
                            Toast.makeText(getApplicationContext(), "Please Enter SE Type", Toast.LENGTH_LONG).show();
                            pastry.setError("!");
                            pizza.setError("!");
                            drinks.setError("!");
                        }else if (pastry.isChecked() == true && pizza.isChecked() == true && drinks.isChecked() == true){
                            Toast.makeText(getApplicationContext(), "Please Enter One SE Type!", Toast.LENGTH_LONG).show();
                            pastry.setError("!");
                            pizza.setError("!");
                            drinks.setError("!");
                        }else if (pastry.isChecked() == true && pizza.isChecked() == true && drinks.isChecked() == false){
                            Toast.makeText(getApplicationContext(), "Please Enter One SE Type!", Toast.LENGTH_LONG).show();
                            pastry.setError("!");
                            pizza.setError("!");
                            drinks.setError("!");
                        }else if (pastry.isChecked() == true && pizza.isChecked() == false && drinks.isChecked() == true){
                            Toast.makeText(getApplicationContext(), "Please Enter One SE Type!", Toast.LENGTH_LONG).show();
                            pastry.setError("!");
                            pizza.setError("!");
                            drinks.setError("!");
                        }else if (pastry.isChecked() == false && pizza.isChecked() == true && drinks.isChecked() == true){
                            Toast.makeText(getApplicationContext(), "Please Enter One SE Type!", Toast.LENGTH_LONG).show();
                            pastry.setError("!");
                            pizza.setError("!");
                            drinks.setError("!");
                        }

                        else if(imageUri == null){
                            uploadText.setError("!");
                        }

                        else {
                            insetDataToDb();
                        }




                    }
                });
            }
        });









        pastryVP = findViewById(R.id.pastry);
        pd = new ProgressDialog(MM_Short_Eats_Management.this);
        pd.setTitle("ssdsd");
        pd.setMessage("ssdsd");
        pd.show();
        df = FirebaseDatabase.getInstance().getReference().child("ShortEats");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealsLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ShortEats shortEats = ds.getValue(ShortEats.class);
                    mealsLists.add(shortEats);
                }

                shortEatsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pd.dismiss();
        shortEatsAdapter = new ShortEatsAdapter(mealsLists,MM_Short_Eats_Management.this);
        pastryVP.setAdapter(shortEatsAdapter);
        pastryVP.setPadding(130, 0, 130, 0);


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
                            if(id.charAt(0) == 'M' && id.charAt(1) == 'M'){
                                proSerch.setVisibility(View.VISIBLE);
                                df = FirebaseDatabase.getInstance().getReference().child("MainMeals").child(id);
                                df.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        MainMeals mainMeals = dataSnapshot.getValue(MainMeals.class);

                                        if(mainMeals != null){
                                            proSerch.setVisibility(View.GONE);
                                            Intent intent =  new Intent(MM_Short_Eats_Management.this,  MM_View_Meal_View.class);
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

                            }else if(id.charAt(0) == 'S' && id.charAt(1) == 'E'){

                                proSerch.setVisibility(View.VISIBLE);
                                df = FirebaseDatabase.getInstance().getReference().child("ShortEats").child(id);
                                df.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        ShortEats shortEats = dataSnapshot.getValue(ShortEats.class);

                                        if(shortEats != null){
                                            proSerch.setVisibility(View.GONE);
                                            Intent intent =  new Intent(MM_Short_Eats_Management.this,  MM_Short_Eats_View.class);
                                            intent.putExtra("short_eats", shortEats);
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

                            else {
                                proSerch.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Please Enter Valid Id!", Toast.LENGTH_LONG).show();
                            }
                        }





                    }



                });





            }
        });










        myDialog3 = new Dialog(this);
        deleteAll = findViewById(R.id.deleteAll);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView txtclose;
                        myDialog3.setContentView(R.layout.activity_mm__delete__all__pu);
                        txtclose =(TextView) myDialog3.findViewById(R.id.txtclose3);
                        myDialog3.setCanceledOnTouchOutside(false);
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
                                DeleteAllShortEats();
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
        });

    }




    public void DeleteAllShortEats(){
        df = FirebaseDatabase.getInstance().getReference().child("ShortEats");
        df.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "All Meals are Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_Short_Eats_Management.this, MM_Short_Eats_Management.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_Short_Eats_Management.this, MM_Short_Eats_Management.class);
                    startActivity(intent);
                }

            }
        });
    }





    public void insetDataToDb(){

        progressDialog.setTitle("Adding Main Meal");
        progressDialog.setMessage("Data Uploading.Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

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

        primaryKey = CommonFunctions.get_short_eats_id(CommonConstants.SHORT_EATS_PREFIX, mealsLists);
        shortEats = new ShortEats();
        shortEats.setId(primaryKey);
        shortEats.setName(name.getText().toString());
        shortEats.setPrice(Float.parseFloat(price.getText().toString()));
        if(pastry.isChecked() == true){
            shortEats.setPastry(true);
        }if(pizza.isChecked() == true){
            shortEats.setPizza(true);
        }if(drinks.isChecked() == true){
            shortEats.setDrinks(true);
        }





        final StorageReference sf = storageReference.child("SE" + imageUri.getLastPathSegment());
        sf.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImagePath = uri.toString();
                        shortEats.setImage(ImagePath);
                        df = FirebaseDatabase.getInstance().getReference().child("ShortEats");
                        df.child(shortEats.getId()).setValue(shortEats)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Data Inserted Successfully!" + "Created Short Eat Id : " + primaryKey, Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(MM_Short_Eats_Management.this, MM_Short_Eats_Management.class);
                                            startActivity(intent);
                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Data Not Inserted Successfully!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(MM_Short_Eats_Management.this, MM_Short_Eats_Management.class);
                                            startActivity(intent);
                                        }

                                    }
                                });
                    }
                });
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
}
