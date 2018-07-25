package com.example.saksham.blackoutrestuarant.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.saksham.blackoutrestuarant.R;

/**
 * Created by saksham_ on 14-Apr-18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {


    public TextView textOrderId,textOrderStatus,textOrderPhone,textOrderTime;

    public OrderViewHolder(View itemView) {
        super(itemView);
        Log.i("cms","hiiiiiiiiiiiiii");
        textOrderId=(TextView)itemView.findViewById(R.id.order_id);
        textOrderStatus=(TextView)itemView.findViewById(R.id.order_status);
        textOrderPhone=(TextView)itemView.findViewById(R.id.order_phone);
        textOrderTime=(TextView)itemView.findViewById(R.id.order_time);

    }


}
