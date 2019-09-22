package Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelmanagement.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderReservation extends RecyclerView.ViewHolder{

    public RelativeLayout root;
    public CircleImageView imageView;
    public TextView txtTitle;
    public TextView txtDesc;

    public ViewHolderReservation(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.list_root);
        txtTitle = itemView.findViewById(R.id.list_title);
        txtDesc = itemView.findViewById(R.id.list_desc);
        imageView = itemView.findViewById(R.id.img);
    }

    public void setTxtTitle(String string) {
        txtTitle.setText(string);
    }


    public void setTxtDesc(String string) {
        txtDesc.setText(string);
    }

    public void setImageView(String URL){
        Glide.with(root).
                load(URL).
                into(imageView);
    }

}
