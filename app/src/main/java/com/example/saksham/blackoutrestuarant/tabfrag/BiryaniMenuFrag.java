package com.example.saksham.blackoutrestuarant.tabfrag;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.FireRecyclerAdapter;
import com.example.saksham.blackoutrestuarant.adapter.FoodViewHolder;
import com.example.saksham.blackoutrestuarant.design.FoodDetail;
import com.example.saksham.blackoutrestuarant.design.MainMenuSelectionFrag;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.example.saksham.blackoutrestuarant.item.Product;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.main.RecyclerTouchListener;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.example.saksham.blackoutrestuarant.service.Internet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BiryaniMenuFrag extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String index;

    FirebaseRecyclerAdapter<Product, FoodViewHolder> adapter;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    @Override
    public void onStart() {
        super.onStart();

        //if(Internet.isConnectedToInternet(getContext()))
        //    adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        //adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
/*
        if(Internet.isConnectedToInternet(getContext()))
            adapter.startListening();
        else
            check();*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_biryani_menu, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.VegRecycle);

        Bundle args = getArguments();

        index = args.getString("index", "00");

        firebaseDatabase = FirebaseSingleDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FoodItemDetail");


        /*ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //HomeMenuData homeMenuData=dataSnapshot.getValue(HomeMenuData.class);
                Object val = dataSnapshot.getValue(Object.class);
                Log.i("cms",val.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
*/

        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        if (Internet.isConnectedToInternet(getContext())) {
            adapter = FireRecyclerAdapter.loadListFood(recyclerView, index, databaseReference, getContext());
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }
        else
            check();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, int adapterPosition) {

                //Log.i("cms","bir "+index+(char)(adapterPosition+97));
                //Toast.makeText(getContext(),"hi",Toast.LENGTH_SHORT).show();
                if (Internet.isConnectedToInternet(getContext())) {
                    Intent foodDetail = new Intent(getContext(), FoodDetail.class);
                    foodDetail.putExtra("FoodId", index + (char) (adapterPosition + 97));
                    startActivity(foodDetail);
                } else
                    Toast.makeText(getContext(),"Please check your Internet Connection !!!",Toast.LENGTH_SHORT).show();
            }
        }));

        return rootView;
    }

    public void check() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please check your Internet Connection !!!")
                .setCancelable(false)
                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Internet.isConnectedToInternet(getContext())) {
                            adapter = FireRecyclerAdapter.loadListFood(recyclerView, index, databaseReference, getContext());
                            recyclerView.setAdapter(adapter);
                            adapter.startListening();

                        } else {
                            Toast.makeText(getContext(), "Please check your Internet Connection !!!", Toast.LENGTH_LONG).show();
                            check();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}