package com.example.saksham.blackoutrestuarant.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saksham.blackoutrestuarant.R;

/**
 * Created by saksham_ on 22-Mar-18.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder  {

    public TextView textView;
    public ImageView imageView;

    public MenuViewHolder(View itemView) {
        super(itemView);
        Log.i("cms","menuholder");
        textView=(TextView)itemView.findViewById(R.id.title);
        imageView=(ImageView)itemView.findViewById(R.id.thumbnail);
    }

}
