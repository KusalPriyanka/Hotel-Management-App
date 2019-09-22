package Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hotelmanagement.MainActivity;
import com.example.hotelmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import Modal.Packages;
import Modal.Reservation;

public class AdapterForPackage extends PagerAdapter {

    private List<Packages> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private Intent intent;

    public AdapterForPackage(List<Packages> models, Context context) {
        this.models = models;
        this.context = context;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.reservation_packages, container, false);

        ImageView imageView;
        TextView title, desc, price;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);
        price = view.findViewById(R.id.price);

        title.setText(models.get(position).getName());
        desc.setText(models.get(position).getDes());
        price.setText("LKR " + models.get(position).getPrice() + "/=");

        Glide.with(context)
                .load(models.get(position).getUrl())
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(intent != null){

                    final ProgressDialog progressDialog = new ProgressDialog(context);

                    progressDialog.setTitle("Make Reservation");
                    progressDialog.setMessage("Make New Reservation... Wait!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Reservation reservation = (Reservation) getIntent().getSerializableExtra("Res");
                    reservation.setPackageId(models.get(position).getId());

                    DatabaseReference fb = FirebaseDatabase.getInstance().getReference().child("Reservation");

                    fb.push().setValue(reservation)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    progressDialog.dismiss();
                                    Toast.makeText(context,"Make Reservation Sucessfully!",Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, MainActivity.class));
                                }
                            });
                }

            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
