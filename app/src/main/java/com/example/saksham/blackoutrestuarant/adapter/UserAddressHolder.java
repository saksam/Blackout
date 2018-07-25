package com.example.saksham.blackoutrestuarant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.saksham.blackoutrestuarant.R;


/**
 * Created by saksham_ on 27-Apr-18.
 */

public class UserAddressHolder extends RecyclerView.ViewHolder {


    public TextView user_name,user_address,user_city,user_phone;

    public UserAddressHolder(View itemView) {
        super(itemView);

        user_name = (TextView) itemView.findViewById(R.id.user_name);
        user_address = (TextView) itemView.findViewById(R.id.user_address);
        user_city = (TextView) itemView.findViewById(R.id.user_city_pin);
        user_phone = (TextView) itemView.findViewById(R.id.user_phone);
    }
}
