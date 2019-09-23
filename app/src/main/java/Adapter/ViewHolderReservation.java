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
    public TextView txtTitle, txtcheckin, txtcheckout, noofperson;

    public ViewHolderReservation(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.list_root);
        txtTitle = itemView.findViewById(R.id.list_title);
        txtcheckin = itemView.findViewById(R.id.checkin);
        txtcheckout = itemView.findViewById(R.id.checkout);
        noofperson = itemView.findViewById(R.id.noofperson);
        imageView = itemView.findViewById(R.id.img);
    }

    public void setTxtTitle(String string) {
        txtTitle.setText(string);
    }


    public void setCheckin(String date, String time) {
        txtcheckin.setText("Checkin Date : " + date + " Time : " + time);
    }

    public void setCheckout(String date, String time) {
        txtcheckout.setText("Checkout Date : " + date + " Time : " + time);
    }

    public void setNoofperson(int no) {
        noofperson.setText("No Of Person : " + no);
    }

    public void setImageView(String URL){
        Glide.with(root).
                load(URL).
                into(imageView);
    }

}
