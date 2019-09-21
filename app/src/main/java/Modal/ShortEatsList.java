package Modal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotelmanagement.R;

import java.util.List;

public class ShortEatsList extends ArrayAdapter<ShortEats> {
    private Activity context;
    private List<ShortEats> shortEatsList;

    public ShortEatsList(Activity context,List<ShortEats> shortEatsList) {
        super(context, R.layout.short_eats_recycle_view, shortEatsList);
        this.context = context;
        this.shortEatsList = shortEatsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.short_eats_recycle_view, null, true);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView price = (TextView) view.findViewById(R.id.price);
        ImageView image = (ImageView) view.findViewById(R.id.image);


        ShortEats shortEats= shortEatsList.get(position);

        name.setText(shortEats.getShortEatName());
        price.setText("RS - " +shortEats.getPrice() + "0/-");


        Glide.with(getContext()).load(shortEats.getImageName()).into(image);
        return view;

    }
}
