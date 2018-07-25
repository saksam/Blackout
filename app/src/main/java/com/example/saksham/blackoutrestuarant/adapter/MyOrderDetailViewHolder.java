package com.example.saksham.blackoutrestuarant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.saksham.blackoutrestuarant.R;

/**
 * Created by saksham_ on 15-Apr-18.
 */

public class MyOrderDetailViewHolder extends RecyclerView.ViewHolder {

    public TextView name,size,quantity,price;

    public MyOrderDetailViewHolder(View itemView) {
        super(itemView);

        name=(TextView) itemView.findViewById(R.id.order_item_name);
        size=(TextView) itemView.findViewById(R.id.order_item_size);
        quantity=(TextView) itemView.findViewById(R.id.order_item_quantity);
        price=(TextView) itemView.findViewById(R.id.order_item_price);

    }
}
