package com.example.saksham.blackoutrestuarant.tabfrag;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.database.Database;
import com.example.saksham.blackoutrestuarant.design.Cart;
import com.example.saksham.blackoutrestuarant.item.OrderRequest;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.saksham.blackoutrestuarant.design.Cart.cart;


public class takeAddress extends DialogFragment {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String price;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        price= bundle.getString("TotalPrice","");

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_take_address, container, false);



        final EditText add_name = view.findViewById(R.id.add_name);
        final EditText add_phone = view.findViewById(R.id.add_phone);
        final EditText add_pincode = view.findViewById(R.id.add_pincode);
        final EditText add_flat = view.findViewById(R.id.add_flat);
        final EditText add_area = view.findViewById(R.id.add_area);
        final EditText add_landmark = view.findViewById(R.id.add_landmark);
        final EditText add_city = view.findViewById(R.id.add_city);
        final EditText add_state = view.findViewById(R.id.add_state);
        final Button okbtn=view.findViewById(R.id.orderPlacedbtn);
        final Button cancelbtn=view.findViewById(R.id.orderCancelbtn);

        firebaseDatabase= FirebaseSingleDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("OrderRequest");

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderRequest orderRequest=new OrderRequest(
                        add_phone.getText().toString(),
                        add_name.getText().toString(),
                        add_flat.getText().toString(),
                        add_area.getText().toString(),
                        add_landmark.getText().toString(),
                        add_pincode.getText().toString(),
                        add_city.getText().toString(),
"up",
                        cart,
                        "0",
                        MainActivity.phoneNumber,
                        price
                );
                databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(orderRequest);

                new Database(getContext()).cleanCart();

                Toast.makeText(getContext(), "Saved Sucessfully", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return view;
    }
}
