package com.example.saksham.blackoutrestuarant.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;

import org.w3c.dom.Text;

/**
 * Created by saksham_ on 23-Mar-18.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder {

    public TextView food_name;
    public ImageView food_image;

  /*  public ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
*/
    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name=(TextView)itemView.findViewById(R.id.titlee);
        food_image=(ImageView)itemView.findViewById(R.id.thumbnaill);

        //itemView.setOnClickListener(this);
    }

    /*@Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }*/
}
