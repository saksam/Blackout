package com.example.saksham.blackoutrestuarant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class MainHomeMenu_Adapter extends RecyclerView.Adapter<MainHomeMenu_Adapter.MyViewHolder> {


    private Context mContext;
    private List<Menu_item> menuList;

    public MainHomeMenu_Adapter(Context mContext, List<Menu_item> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_card, parent, false);

      //  itemView.setOnClickListener(new MainActivity.MyOnClickListener());

       /* itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewType==0)
                {
                    Toast.makeText(mContext,"hello",Toast.LENGTH_LONG).show();
                    view.getContext().startActivity(new Intent(mContext,MainMenuSelectionFrag.class));
                }
                else if(viewType==1)
                {
                    Toast.makeText(mContext,"hello",Toast.LENGTH_LONG).show();
                }
                else if(viewType==2)
                {
                    Toast.makeText(mContext,"hello",Toast.LENGTH_LONG).show();
                }
                else if(viewType==3)
                {
                    Toast.makeText(mContext,"hello",Toast.LENGTH_LONG).show();
                }
                else if(viewType==4)
                {
                    Toast.makeText(mContext,"hello",Toast.LENGTH_LONG).show();
                }
                else if(viewType==5)
                {
                    Toast.makeText(mContext,"hello",Toast.LENGTH_LONG).show();
                }
                else if(viewType==6)
                {
                    Toast.makeText(mContext,"hello",Toast.LENGTH_LONG).show();
                }

            }
        });
*/
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Menu_item menu_item = menuList.get(position);
        holder.title.setText(menu_item.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(menu_item.getThumbnail()).into(holder.thumbnail);


    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }



}
