package com.example.saksham.blackoutrestuarant.design;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.FireRecyclerAdapter;
import com.example.saksham.blackoutrestuarant.adapter.FoodViewHolder;
import com.example.saksham.blackoutrestuarant.adapter.OrderViewHolder;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.example.saksham.blackoutrestuarant.item.CartDetail;
import com.example.saksham.blackoutrestuarant.item.OrderRequest;
import com.example.saksham.blackoutrestuarant.item.Product;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.main.RecyclerTouchListener;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.example.saksham.blackoutrestuarant.service.Internet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MyOrders extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<OrderRequest, OrderViewHolder> adapter;

    ProgressDialog progress;

    List<CartDetail> cartDetailList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView textView;

    @Override
    protected void onStart() {
        super.onStart();

        //adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(context, "Please check your Internet Connection !!!", Toast.LENGTH_SHORT).show();
        //adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);


        progress = new ProgressDialog(this);
        progress.setTitle("Loading Menu");
        progress.setMessage("Please wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);


        firebaseDatabase = FirebaseSingleDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OrderRequest");

        recyclerView = (RecyclerView) findViewById(R.id.listMyOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Internet.isConnectedToInternet(getApplicationContext()))
        {
            loadOrders(MainActivity.phoneNumber);
            adapter.startListening();
        }


       // else
       //     Toast.makeText(context, "Please check your Internet Connection !!!", Toast.LENGTH_SHORT).show();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, final int position) {

                if(Internet.isConnectedToInternet(getApplicationContext())) {
                    Intent i = new Intent(getApplicationContext(), MyOrderDetail.class)
                            .putExtra("Id", adapter.getRef(position).getKey())
                            .putExtra("Address", adapter.getItem(position).getFlatNo() + ", " + adapter.getItem(position).getStreet() + " near " + adapter.getItem(position).getLandmark() + ", " + adapter.getItem(position).getCity() + ".")
                            .putExtra("Phone", adapter.getItem(position).getPhone())
                            .putExtra("Price", adapter.getItem(position).getPrice());
                    //Log.i("cms","hiiiiiiiiiii"+adapter.getRef(position).getKey());
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection !!!", Toast.LENGTH_SHORT).show();

            }

        }));

    }

    private void check() {

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Please check your Internet Connection !!!")
                    .setCancelable(false)
                    .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (Internet.isConnectedToInternet(getApplicationContext()))
                            {
                                loadOrders(MainActivity.phoneNumber);
                                adapter.startListening();
                            }
                            else
                                check();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

    }

    private void loadOrders(String phoneNumber) {

            progress.show();
            Query queries = databaseReference.orderByChild("userId").equalTo(MainActivity.phoneNumber);

            queries.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.exists()) {

                        Toast.makeText(getApplicationContext(), "No data exists", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//.orderByChild("userId").equalTo(MainActivity.phoneNumber)
            Log.i("cms", "myorderr");
            FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder<OrderRequest>().setQuery(databaseReference.orderByChild("userId").equalTo(MainActivity.phoneNumber), OrderRequest.class).build();
            Log.i("cms", "myorderrr");
            adapter = new FirebaseRecyclerAdapter<OrderRequest, OrderViewHolder>(option) {
                @Override
                public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                    Log.i("cms", "myorder");

                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.order_layout_item, parent, false);
                    return new OrderViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull OrderRequest model) {

                    Log.i("cms", "myBind");

                    Timestamp timestamp = new Timestamp(Long.parseLong(adapter.getRef(position).getKey()));
                    // holder.textOrderAddress.setText(model.getFlatNo()+" "+model.getStreet()+" near "+model.getLandmark()+" "+model.getCity()+".");
                    Date date = new Date(timestamp.getTime());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");

                    String time = simpleDateFormat.format(date);

                    holder.textOrderTime.setText("Date and Time: " + time);
                    holder.textOrderPhone.setText("Phone: " + model.getPhone());
                    holder.textOrderStatus.setText("Order Status: " + codeToStatus(model.getStatus()));
                    holder.textOrderId.setText("OrderId: " + "#" + adapter.getRef(position).getKey());

                    progress.dismiss();
                }
            };
            Log.i("cms", "hi again");
            recyclerView.setAdapter(adapter);


    }

    String codeToStatus(String s) {
        if (s.equals("0"))
            return "Placed";
        else if (s.equals("1"))
            return "Dispatched";
        else
            return "Shipped";
    }
}
