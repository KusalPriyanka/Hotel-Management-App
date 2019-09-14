package Modal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hotelmanagement.R;

import java.util.List;

public class MealList extends ArrayAdapter<MainMeals> {
    private Activity context;
    private List<MainMeals> mealsList;

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
        TextView type = (TextView) view.findViewById(R.id.tb1);
        MainMeals mainMeals = mealsList.get(position);
        mealName.setText(mainMeals.getMealName());
        type.setText(mainMeals.getType());

        return view;

    }
}
