package Modal;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.hotelmanagement.MainActivity;
import com.example.hotelmanagement.R;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;



import java.io.File;
import java.util.List;

public class MealList extends ArrayAdapter<MainMeals> {
    private Activity context;
    private List<MainMeals> mealsList;
    ImageView image;
    StorageReference storageReference;

    public MealList(Activity context,List<MainMeals> mealsList) {
        super(context, R.layout.mm_main_meal_recyclerview, mealsList);
        this.context = context;
        this.mealsList = mealsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.mm_main_meal_recyclerview, null, true);
        TextView mealName = (TextView) view.findViewById(R.id.name1);
        TextView price = (TextView) view.findViewById(R.id.price1);
        CheckedTextView brakfast = (CheckedTextView) view.findViewById(R.id.brakfast);
        CheckedTextView lunch = (CheckedTextView) view.findViewById(R.id.lunch);
        CheckedTextView dinner = (CheckedTextView) view.findViewById(R.id.dinner);
        image = (ImageView) view.findViewById(R.id.img);


        MainMeals mainMeals = mealsList.get(position);

        mealName.setText(mainMeals.getMealName());
        price.setText("RS - " +mainMeals.getNormalPrice() + "0");
        if (mainMeals.isBrakfast() == true){
            brakfast.setCheckMarkDrawable(R.drawable.ic_check_circle_gold_24dp);
        }
        if (mainMeals.isLunch() == true){
            lunch.setCheckMarkDrawable(R.drawable.ic_check_circle_gold_24dp);
        }
        if (mainMeals.isDinner() == true){
            dinner.setCheckMarkDrawable(R.drawable.ic_check_circle_gold_24dp);
        }

        Glide.with(getContext()).load(mainMeals.getImageName()).into(image);
        return view;

    }
}
