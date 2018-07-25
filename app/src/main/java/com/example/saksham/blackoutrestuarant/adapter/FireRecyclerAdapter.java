package com.example.saksham.blackoutrestuarant.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.design.FoodDetail;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.example.saksham.blackoutrestuarant.item.Product;
import com.example.saksham.blackoutrestuarant.main.RecyclerTouchListener;
import com.example.saksham.blackoutrestuarant.service.Internet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by saksham_ on 27-Mar-18.
 */

public class FireRecyclerAdapter {

    static FirebaseRecyclerAdapter<Product, FoodViewHolder> adapter;

    public static FirebaseRecyclerAdapter<Product, FoodViewHolder> loadListFood(final RecyclerView recyclerView, final String index, DatabaseReference databaseReference, final Context context) {

            final ProgressDialog progress;
            progress = new ProgressDialog(context);
            progress.setTitle("Loading Menu");
            progress.setMessage("Please wait");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            Log.i("cms", "load " + index);
            progress.show();
            FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder<Product>().setQuery(databaseReference.child(index), Product.class).build();
            adapter = new FirebaseRecyclerAdapter<Product, FoodViewHolder>(option) {
                @Override
                protected void onBindViewHolder(@NonNull FoodViewHolder holder, final int position, @NonNull Product model) {


                    holder.food_name.setText(model.getName());
                    Log.i("cms", "pqr inside veg frag");
                    Picasso.with(context).load(model.getImage()).into(holder.food_image);

                    progress.dismiss();
                }

                @Override
                public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.submenuitem, parent, false);
                    return new FoodViewHolder(view);
                }
            };
            Log.i("cms", "pqr outside veg frag");
            return adapter;

    }
}