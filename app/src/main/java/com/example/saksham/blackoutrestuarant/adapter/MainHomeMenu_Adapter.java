package com.example.saksham.blackoutrestuarant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saksham.blackoutrestuarant.design.HomeMenuData;
import com.example.saksham.blackoutrestuarant.design.Menu_item;
import com.example.saksham.blackoutrestuarant.R;

import java.util.List;

public class MainHomeMenu_Adapter  {


    private Context mContext;
    private List<Menu_item> menuList;

    public MainHomeMenu_Adapter(Context mContext, List<Menu_item> menuList) {

        this.mContext = mContext;
        this.menuList = menuList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    /*
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            Menu_item menu_item = menuList.get(position);
            holder.title.setText(menu_item.getName());
            // loading album cover using Glide library
            Glide.with(mContext).load(menu_item.getThumbnail()).into(holder.thumbnail);

        }
    */
}
