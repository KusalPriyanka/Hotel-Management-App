package Modal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotelmanagement.R;

import java.util.List;

public class WedHallList  extends ArrayAdapter<EM_HallManagement> {
    private Activity context;
    private List<EM_HallManagement> wedList;

    public WedHallList(Activity context,List<EM_HallManagement> wedlist) {
        super(context, R.layout.em_hallset_recycleview, wedlist);
        this.context = context;
        this.wedList = wedlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.em_hallset_recycleview, null, true);

        TextView hallName = (TextView) view.findViewById(R.id.hallname);
        TextView hallPrice = (TextView) view.findViewById(R.id.price);
        TextView hallDesc = (TextView) view.findViewById(R.id.descrip);
        ImageView hallImage = (ImageView) view.findViewById(R.id.hallImage);

        EM_HallManagement em_hallManagement = wedList.get(position);

        hallName.setText(em_hallManagement.getName());
        hallPrice.setText(em_hallManagement.getPrice()+"");
        hallDesc.setText(em_hallManagement.getDescription());
        Glide.with(getContext()).load(em_hallManagement.getImageName()).into(hallImage);


        return view;

    }

}
