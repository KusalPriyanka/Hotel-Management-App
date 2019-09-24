package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import Modal.MainMeals;
import Modal.ShortEats;

public class MM_Short_Eats_View extends AppCompatActivity {
    private TextView id, headerDeletePU , name, price, uploadText ;
    private Dialog myDialog6, myDialog4, myDialog2;
    private CardView search;
    private EditText SerchTag;
    private ImageView serchIcon, image, delete, update, uploadedImage, back;
    private DatabaseReference df;
    private Button deleteAllfromDb, canselDAll, editDetails;
    private ShortEats shortEats;
    private static final int PICK_FROM_GALLARY = 2;
    private Uri imageUri;
    private ProgressBar  proSerch;
    private StorageReference storageReference;
    private ImageView upload, uplodedImage, edit;
    private String ImagePath;
    private MainMeals mainMeals;
    private int count = 0;
    private ProgressDialog progressDialog;
    private CheckBox pastry, pizza, drinks;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm__short__eats__view);

        progressDialog = new ProgressDialog(this);


        back = findViewById(R.id.backToMa);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MM_Short_Eats_View.this, MM_Short_Eats_Management.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        shortEats = (ShortEats) intent.getSerializableExtra("short_eats");

        id = findViewById(R.id.userNameLabel);
        id.setText(shortEats.getId());

        image = findViewById(R.id.image);
        Glide.with(MM_Short_Eats_View.this).load(shortEats.getImage()).into(image);

        name = findViewById(R.id.name);
        name.setText(shortEats.getName());

        price = findViewById(R.id.price);
        price.setText("RS - "+ shortEats.getPrice() + "0/-");

        //search bar
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
                                            Intent intent =  new Intent(MM_Short_Eats_View.this,  MM_View_Meal_View.class);
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
                                            Intent intent =  new Intent(MM_Short_Eats_View.this,  MM_Short_Eats_View.class);
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

        //delete selected item
        delete = findViewById(R.id.imageView);
        myDialog4 = new Dialog(this);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView txtclose;
                myDialog4.setContentView(R.layout.activity_mm__delete__all__pu);
                headerDeletePU = myDialog4.findViewById(R.id.header);
                headerDeletePU.setText("Are You Want To delete");
                txtclose = (TextView) myDialog4.findViewById(R.id.txtclose3);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog4.dismiss();
                    }
                });
                myDialog4.setCanceledOnTouchOutside(false);
                myDialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog4.show();


                deleteAllfromDb = myDialog4.findViewById(R.id.button5);
                deleteAllfromDb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteMainMeal(shortEats.getId());
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






        //edit
       myDialog2 = new Dialog(this);
       edit = findViewById(R.id.back);
       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               TextView txtclose1;
               myDialog2.setContentView(R.layout.mm_edit_short_eats_pu);
               txtclose1 = (TextView) myDialog2.findViewById(R.id.txtclose);
               txtclose1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       myDialog2.dismiss();
                   }
               });

               myDialog2.setCanceledOnTouchOutside(false);
               myDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               myDialog2.show();

               name = myDialog2.findViewById(R.id.mealName);
               price = myDialog2.findViewById(R.id.normalPrice);
               pastry = myDialog2.findViewById(R.id.pastry);
               pizza = myDialog2.findViewById(R.id.pizza);
               drinks = myDialog2.findViewById(R.id.drinks);
               uploadText  = myDialog2.findViewById(R.id.textView25);
               uploadedImage  = myDialog2.findViewById(R.id.uplodedImage);

               name.setText(shortEats.getName());
               price.setText(shortEats.getPrice() + "");
               if (shortEats.isPastry() == true) {
                   pastry.setChecked(true);
               }
               if (shortEats.isPizza() == true) {
                   pizza.setChecked(true);
               }
               if (shortEats.isDrinks() == true) {
                   drinks.setChecked(true);
               }
               Glide.with(MM_Short_Eats_View.this).load(shortEats.getImage()).into(uploadedImage);
               ImagePath = shortEats.getImage();

               upload = myDialog2.findViewById(R.id.upload);
               upload.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent();
                       intent.setType("image/*");
                       intent.setAction(Intent.ACTION_GET_CONTENT);
                       startActivityForResult(intent, PICK_FROM_GALLARY);
                   }

               });

               uplodedImage = myDialog2.findViewById(R.id.imageView16);



               editDetails = (Button) myDialog2.findViewById(R.id.edit);
               editDetails.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

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
                       }else {
                           updatedetails();
                       }

                   }
               });

           }
       });



    }



    public void DeleteMainMeal(String id) {
        df = FirebaseDatabase.getInstance().getReference().child("ShortEats").child(id);
        df.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_Short_Eats_View.this, MM_Short_Eats_Management.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MM_Short_Eats_View.this, MM_Short_Eats_Management.class);
                    startActivity(intent);
                }

            }
        });
    }



    public void updatedetails(){

        if (count == 0){
            progressDialog.setTitle("Updating Short Eat " + shortEats.getId() );
            progressDialog.setMessage("Data Uploading.Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ShortEats se = new ShortEats();
            se.setId(shortEats.getId());
            se.setPrice(Float.parseFloat(price.getText().toString()));
            se.setName(name.getText().toString());
            se.setImage(ImagePath);

            df = FirebaseDatabase.getInstance().getReference().child("ShortEats").child(se.getId());
            df.setValue(se).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MM_Short_Eats_View.this, MM_Short_Eats_Management.class);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Data Not Updated Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MM_Short_Eats_View.this, MM_Short_Eats_Management.class);
                        startActivity(intent);
                    }

                }
            });
        }else{

            progressDialog.setTitle("Updating Short Eat " + shortEats.getId() );
            progressDialog.setMessage("Data Uploading.Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            final StorageReference sf = storageReference.child("image" + imageUri.getLastPathSegment());
            sf.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ImagePath = uri.toString();
                            ShortEats se = new ShortEats();
                            se.setId(shortEats.getId());
                            se.setPrice(Float.parseFloat(price.getText().toString()));
                            se.setName(name.getText().toString());
                            se.setImage(ImagePath);

                            df = FirebaseDatabase.getInstance().getReference().child("ShortEats").child(se.getId());
                            df.setValue(se).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MM_Short_Eats_View.this, MM_Short_Eats_Management.class);
                                        startActivity(intent);
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Data Not Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MM_Short_Eats_View.this, MM_Short_Eats_Management.class);
                                        startActivity(intent);
                                    }

                                }
                            });

                        }
                    });
                }
            });








        }






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FROM_GALLARY && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            uploadedImage.setImageURI(imageUri);
            count++;

        }
    }
}
