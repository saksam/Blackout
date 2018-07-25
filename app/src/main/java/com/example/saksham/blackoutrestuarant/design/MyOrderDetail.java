package com.example.saksham.blackoutrestuarant.design;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.saksham.blackoutrestuarant.adapter.MyOrderDetailViewHolder;
import com.example.saksham.blackoutrestuarant.adapter.OrderViewHolder;
import com.example.saksham.blackoutrestuarant.item.CartDetail;
import com.example.saksham.blackoutrestuarant.item.OrderRequest;
import com.example.saksham.blackoutrestuarant.item.Product;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyOrderDetail extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<CartDetail,MyOrderDetailViewHolder> adapter;

    TextView orderId,orderAddress,orderPhone,orderPrice;

    ProgressDialog progress;

    String Id,Address,Phone,Price;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);

        orderId=(TextView)findViewById(R.id.myOrderDetailId);
        orderAddress=(TextView)findViewById(R.id.myOrderDetailAddress);
        orderPhone=(TextView)findViewById(R.id.myOrderDetailPhone);
        orderPrice=(TextView)findViewById(R.id.myOrderDetailPrice);



        progress = new ProgressDialog(this);
        progress.setTitle("Loading Menu");
        progress.setMessage("Please wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        Intent i=getIntent();
        Id=i.getExtras().getString("Id","0");
        Address=i.getExtras().getString("Address","0");
        Phone=i.getExtras().getString("Phone","0");
        Price=i.getExtras().getString("Price","0");

        orderId.setText("Order Id: #"+Id);
        orderPhone.setText("Phone: "+Phone);
        orderPrice.setText("Total Price: "+Price);
        orderAddress.setText("Address: "+Address);

        firebaseDatabase= FirebaseSingleDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("OrderRequest");

        recyclerView=(RecyclerView)findViewById(R.id.listMyOrdersDetail);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Id);
    }

    private void loadOrders(String phoneNumber) {


        progress.show();

        FirebaseRecyclerOptions option=new FirebaseRecyclerOptions.Builder<CartDetail>().setQuery(databaseReference.child(phoneNumber).child("foodList"),CartDetail.class).build();
        adapter=new FirebaseRecyclerAdapter<CartDetail, MyOrderDetailViewHolder>(option) {

            @Override
            protected void onBindViewHolder(@NonNull MyOrderDetailViewHolder holder, int position, @NonNull CartDetail model) {

                holder.name.setText("Name: "+model.getProductName());
                holder.size.setText("Size: "+model.getSize());
                holder.quantity.setText("Quantity: "+Integer.toString(model.getQuantity()));
                holder.price.setText("Price: "+Double.toString(model.getPrice()));

                progress.dismiss();
            }

            @Override
            public MyOrderDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.myorderdetail_item, parent, false);
                return new MyOrderDetailViewHolder(view);
            }
        };
        Log.i("cms","hi again");
        recyclerView.setAdapter(adapter);

    }
}
