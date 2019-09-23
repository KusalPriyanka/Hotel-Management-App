package Util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hotelmanagement.R;

import java.util.List;

import Modal.ShortEats;


public class DrinksAdapter extends PagerAdapter {

    private List<ShortEats> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private Intent intent;
    ShortEats shortEats;

    public DrinksAdapter(List<ShortEats> models, Context context) {
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

        View view = layoutInflater.inflate(R.layout.short_eats_recycle_view, container, false);

        ImageView imageView;
        TextView name, price;

        imageView = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);


        shortEats = models.get(position);

        name.setText(models.get(position).getName());
        price.setText(models.get(position).getPrice() + "0/=");

        Glide.with(context)
                .load(models.get(position).getImage())
                .into(imageView);





        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}