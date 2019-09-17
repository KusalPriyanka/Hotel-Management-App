package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmanagement.R;

public class MainMealsViewHolder extends RecyclerView.ViewHolder {
    public TextView t1;
    public ImageView iv;


    public MainMealsViewHolder(View MMview) {
        super(MMview);
        t1 = (TextView)MMview.findViewById(R.id.name1);
        //iv = (ImageView) MMview.findViewById(R.id.imageMEal);
    }
}
