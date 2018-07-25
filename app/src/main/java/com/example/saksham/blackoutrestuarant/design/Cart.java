package com.example.saksham.blackoutrestuarant.design;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.CartAdapter;
import com.example.saksham.blackoutrestuarant.adapter.MyOrderDetailViewHolder;
import com.example.saksham.blackoutrestuarant.adapter.UserAddressHolder;
import com.example.saksham.blackoutrestuarant.database.Database;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.example.saksham.blackoutrestuarant.item.CartDetail;
import com.example.saksham.blackoutrestuarant.item.OrderRequest;
import com.example.saksham.blackoutrestuarant.item.UserAddress;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.main.RecyclerTouchListener;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.example.saksham.blackoutrestuarant.service.Internet;
import com.example.saksham.blackoutrestuarant.tabfrag.NonVegStarterMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.takeAddress;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.Request;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.saksham.blackoutrestuarant.main.MainActivity.phoneNumber;

public class Cart extends AppCompatActivity {

    private FirebaseRecyclerAdapter<UserAddress, UserAddressHolder> adapter;
    static RecyclerView recyclerView;
    RecyclerView address_recycler;
    RecyclerView.LayoutManager layoutManager;

    AlertDialog dialogBox;

    ProgressDialog progress;
    Button addAddressBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView noItem;

    static TextView textTotalPrice;
    Button placeOrder;

    public static List<CartDetail> cart = new ArrayList<>();

    public static CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseDatabase = FirebaseSingleDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OrderRequest");


        progress = new ProgressDialog(this);
        progress.setTitle("Loading Menu");
        progress.setMessage("Please wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        noItem = (TextView) findViewById(R.id.noItem);
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textTotalPrice = (TextView) findViewById(R.id.totalPrice);

        placeOrder = (Button) findViewById(R.id.btnPlaceOrder);


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();

                takeAddress add=new takeAddress();
                Bundle args = new Bundle();
                args.putString("TotalPrice",textTotalPrice.getText().toString());

                Log.i("cms","hello "+textTotalPrice.getText().toString());
                add.setArguments(args);
                add.show(fragmentManager,"hello");
                */


                /*
                Fragment fragment = new takeAddress();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id., fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();*/
                // showUserAddress();
                if(cart.size()!=0) {
                    if (Internet.isConnectedToInternet(getApplicationContext())) {
                        startActivity(new Intent(Cart.this, SelectAddress.class).putExtra("price", textTotalPrice.getText().toString()));
                    } else
                        Toast.makeText(Cart.this, "Please check your Internet Connection !!!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Add Items to Cart first !!",Toast.LENGTH_SHORT).show();
            }
        });

        cart = new Database(this).getCart();
        if (cart.size() == 0) {
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.INVISIBLE);
            cartAdapter = new CartAdapter(cart, this);
            loadListFood();
        }

    }

    private void showUserAddress() {
        LayoutInflater inflater = getLayoutInflater();

        View alertLayout = inflater.inflate(R.layout.show_user_address, null);

        address_recycler = (RecyclerView) alertLayout.findViewById(R.id.userAddressRecycler);
        addAddressBtn = (Button) alertLayout.findViewById(R.id.addAddressButton);


        //loadUserAddress();


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Cart.this);

        alertDialogBuilder.setView(alertLayout);


        dialogBox = alertDialogBuilder.create();

        dialogBox.setTitle("Delivery Address");
        //dialog.setMessage("Enter your Address");


        dialogBox.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        dialogBox.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);


        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogBox.dismiss();
                //addAddressMethod();
            }
        });

    }

    /*
        private void addAddressMethod()
        {
            databaseReference=firebaseDatabase.getReference("UserAddress");

            LayoutInflater inflater = getLayoutInflater();

            View alertLayout = inflater.inflate(R.layout.take_user_new_address, null);



            final EditText add_name = alertLayout.findViewById(R.id.add_name);
            final EditText add_phone = alertLayout.findViewById(R.id.add_phone);
            final EditText add_pincode = alertLayout.findViewById(R.id.add_pincode);
            final EditText add_flat = alertLayout.findViewById(R.id.add_flat);
            final EditText add_area = alertLayout.findViewById(R.id.add_area);
            final EditText add_landmark = alertLayout.findViewById(R.id.add_landmark);
            final EditText add_city = alertLayout.findViewById(R.id.add_city);

            final AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(Cart.this);


            alertDialogBuilder.setView(alertLayout);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {


                  UserAddress userAddress=new UserAddress(
                          add_phone.getText().toString(),
                          add_name.getText().toString(),
                          add_flat.getText().toString(),
                          add_area.getText().toString(),
                          add_landmark.getText().toString(),
                          add_pincode.getText().toString(),
                          add_city.getText().toString()
                          );
                    databaseReference.child(MainActivity.phoneNumber).setValue(userAddress);
                    Toast.makeText(Cart.this, "New Address Added Successfully", Toast.LENGTH_LONG).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                    dialog.dismiss();
                }
            });



            AlertDialog dialog = alertDialogBuilder.create();

            dialog.setTitle("Add New Address..");
            //dialog.setMessage("Enter your Address");


            dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


            dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
            dialog.show();
        }
    */
/*
    private void loadUserAddress()
    {

        databaseReference=firebaseDatabase.getReference("UserAddress");
        progress.show();

        Query queries = databaseReference.child(MainActivity.phoneNumber);
        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()) {

                    Toast.makeText(getApplicationContext(), "No data exists", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                    dialogBox.setMessage("No Saved Address");
                    dialogBox.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerOptions option=new FirebaseRecyclerOptions.Builder<UserAddress>().setQuery(databaseReference.child(MainActivity.phoneNumber),UserAddress.class).build();
        adapter=new FirebaseRecyclerAdapter<UserAddress, UserAddressHolder>(option) {

            @Override
            protected void onBindViewHolder(@NonNull UserAddressHolder holder, int position, @NonNull UserAddress model) {

                holder.user_name.setText(model.getName());
                holder.user_address.setText(model.getFlatNo()+", "+model.getStreet()+".");
                holder.user_city.setText(model.getCity()+"-"+model.getPincode());
                holder.user_phone.setText(model.getPhone());


                progress.dismiss();
                dialogBox.show();
            }

            @Override
            public UserAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_address_item, parent, false);

                return new UserAddressHolder(view);
            }
        };
        Log.i("cms","hi again");



        address_recycler.setAdapter(adapter);



    }
*/
    private void showAlertDialog() {

        databaseReference = firebaseDatabase.getReference("OrderRequest");
        LayoutInflater inflater = getLayoutInflater();

        View alertLayout = inflater.inflate(R.layout.take_address, null);


        final EditText add_name = alertLayout.findViewById(R.id.add_name);
        final EditText add_phone = alertLayout.findViewById(R.id.add_phone);
        final EditText add_pincode = alertLayout.findViewById(R.id.add_pincode);
        final EditText add_flat = alertLayout.findViewById(R.id.add_flat);
        final EditText add_area = alertLayout.findViewById(R.id.add_area);
        final EditText add_landmark = alertLayout.findViewById(R.id.add_landmark);
        final EditText add_city = alertLayout.findViewById(R.id.add_city);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Cart.this);


        alertDialogBuilder.setView(alertLayout);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                OrderRequest orderRequest = new OrderRequest(
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
                        phoneNumber,
                        textTotalPrice.getText().toString()
                );
                databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(orderRequest);

                new Database(getBaseContext()).cleanCart();

                Toast.makeText(Cart.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });


        AlertDialog dialog = alertDialogBuilder.create();

        dialog.setTitle("One More Step");
        //dialog.setMessage("Enter your Address");


        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        dialog.show();
    }


    public static void loadListFood() {


        recyclerView.setAdapter(cartAdapter);
        double total = 0;
        for (CartDetail cartDetail : cart) {
            total += cartDetail.getQuantity() * cartDetail.getPrice();
        }

        Locale locale = new Locale("en", "IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        textTotalPrice.setText(fmt.format(total));

    }
}
