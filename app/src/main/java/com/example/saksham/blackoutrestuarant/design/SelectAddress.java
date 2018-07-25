package com.example.saksham.blackoutrestuarant.design;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.UserAddressHolder;
import com.example.saksham.blackoutrestuarant.database.Database;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.example.saksham.blackoutrestuarant.item.OrderRequest;
import com.example.saksham.blackoutrestuarant.item.UserAddress;
import com.example.saksham.blackoutrestuarant.item.UserAddressItem;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.saksham.blackoutrestuarant.design.Cart.cart;
import static com.example.saksham.blackoutrestuarant.main.MainActivity.phoneNumber;

public class SelectAddress extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<UserAddressItem, UserAddressHolder> adapter;

    ProgressDialog progress;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button addButton;

    List<UserAddressItem> userAddressList;

    List<UserAddressItem> tempList = new ArrayList<>();

    String price;

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
        setContentView(R.layout.activity_select_address);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading Menu");
        progress.setMessage("Please wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);


        firebaseDatabase = FirebaseSingleDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserAddress");

        addButton = (Button) findViewById(R.id.addAddButton);
        recyclerView = (RecyclerView) findViewById(R.id.listMyAddress);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent i = getIntent();
        price = i.getExtras().getString("price");

        loadOrders();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addAddressMethod();

            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, final int position) {


                if (Internet.isConnectedToInternet(getApplicationContext())) {
                    OrderRequest orderRequest = new OrderRequest(
                            adapter.getItem(position).getPhone(),
                            adapter.getItem(position).getName(),
                            adapter.getItem(position).getFlatNo(),
                            adapter.getItem(position).getStreet(),
                            adapter.getItem(position).getLandmark(),
                            adapter.getItem(position).getPincode(),
                            adapter.getItem(position).getCity(),
                            "up",
                            cart,
                            "0",
                            phoneNumber,
                            price
                    );
                    databaseReference = firebaseDatabase.getReference("OrderRequest");
                    databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(orderRequest);

                    new Database(getBaseContext()).cleanCart();

                    Toast.makeText(SelectAddress.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(SelectAddress.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                } else
                    Toast.makeText(SelectAddress.this, "Please check your Internet Connection !!!", Toast.LENGTH_SHORT).show();
            }

        }));


    }

    private void loadOrders() {

        progress.show();
        userAddressList = new ArrayList<>();
        Query queries = databaseReference.child(MainActivity.phoneNumber);
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


        FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder<UserAddressItem>().setQuery(databaseReference.child(MainActivity.phoneNumber).child("addressList"), UserAddressItem.class).build();
        adapter = new FirebaseRecyclerAdapter<UserAddressItem, UserAddressHolder>(option) {
            @Override
            public UserAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_address_item, parent, false);
                return new UserAddressHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserAddressHolder holder, int position, @NonNull UserAddressItem model) {


                userAddressList.add(model);

                Log.i("cms", "onbind  " + model.getName());
                holder.user_name.setText(model.getName());
                holder.user_address.setText(model.getFlatNo() + ", " + model.getStreet() + ".");
                holder.user_city.setText(model.getCity() + "-" + model.getPincode());
                holder.user_phone.setText(model.getPhone());


                progress.dismiss();
            }
        };
        Log.i("cms", "hi again");
        recyclerView.setAdapter(adapter);


    }

    private void addAddressMethod() {


        databaseReference = firebaseDatabase.getReference("UserAddress");

        LayoutInflater inflater = getLayoutInflater();

        View alertLayout = inflater.inflate(R.layout.take_user_new_address, null);


        final EditText add_name = alertLayout.findViewById(R.id.add_name);
        final EditText add_phone = alertLayout.findViewById(R.id.add_phone);
        final EditText add_pincode = alertLayout.findViewById(R.id.add_pincode);
        final EditText add_flat = alertLayout.findViewById(R.id.add_flat);
        final EditText add_area = alertLayout.findViewById(R.id.add_area);
        final EditText add_landmark = alertLayout.findViewById(R.id.add_landmark);
        final EditText add_city = alertLayout.findViewById(R.id.add_city);

       // final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelectAddress.this)
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(SelectAddress.this)
                .setPositiveButton("OK",null).setNegativeButton("Cancel",null)
                .setView(alertLayout).setIcon(R.drawable.ic_shopping_cart_black_24dp)
                .setTitle("Add New Address..").create();


        //alertDialogBuilder.setView(alertLayout);

        alertDialogBuilder.show();

        alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int flag = 0;

                if (add_name.getText().length() == 0) {
                    add_name.setError("Must not be null");
                    flag = 1;
                }
                if (add_phone.getText().length() != 10) {
                    add_phone.setError("Must be of 10 digits");
                    flag = 1;
                }
                if (add_pincode.getText().length() != 6) {
                    add_pincode.setError("Must be of 6 digits");
                    flag = 1;
                }
                if (add_flat.getText().length() == 0) {
                    add_flat.setError("Must not be null");
                    flag = 1;
                }
                if (add_area.getText().length() == 0) {
                    add_area.setError("Must not be null");
                    flag = 1;
                }
                if (add_landmark.getText().length() == 0) {
                    add_landmark.setError("Must not be null");
                    flag = 1;
                }
                if (flag==0) {

                    UserAddressItem userAddressItem = new UserAddressItem(
                            add_phone.getText().toString(),
                            add_name.getText().toString(),
                            add_flat.getText().toString(),
                            add_area.getText().toString(),
                            add_landmark.getText().toString(),
                            add_pincode.getText().toString(),
                            add_city.getText().toString()
                    );

                    //List<UserAddressItem> list=new ArrayList<>();
                    //  tempList=userAddressList;
                    // tempList.add(userAddressItem);

                    if (Internet.isConnectedToInternet(getApplicationContext())) {
                        userAddressList.add(userAddressItem);

                        UserAddress userAddress = new UserAddress(userAddressList);

                        databaseReference.child(MainActivity.phoneNumber).setValue(userAddress);
                        // loadOrders();
                        Toast.makeText(SelectAddress.this, "New Address Added Successfully", Toast.LENGTH_LONG).show();
                        Log.i("cms", "data");

                        userAddressList.remove(userAddressList.size() - 1);
                    } else {
                        Toast.makeText(SelectAddress.this, "Please check your Internet Connection !!!", Toast.LENGTH_SHORT).show();

                    }

                   alertDialogBuilder.dismiss();
                }
                else
                    return;
            }
        });

        alertDialogBuilder.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder.dismiss();
            }
        });

      /*  alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                int flag = 0;

                if (add_name.getText() == null) {
                    add_name.setError("Error");
                    flag = 1;
                }
                if (add_phone.getText().length() != 10) {
                    add_phone.setError("Error");
                    flag = 1;
                }
                if (add_pincode.getText().length() != 6) {
                    add_pincode.setError("Error");
                    flag = 1;
                }
                if (add_flat.getText().length() == 0) {
                    add_pincode.setError("Error");
                    flag = 1;
                }
                if (add_area.getText().length() == 0) {
                    add_area.setError("Error");
                    flag = 1;
                }
                if (add_landmark.getText().length() == 0) {
                    add_landmark.setError("Error");
                    flag = 1;
                }
                if (flag==0) {

                    UserAddressItem userAddressItem = new UserAddressItem(
                            add_phone.getText().toString(),
                            add_name.getText().toString(),
                            add_flat.getText().toString(),
                            add_area.getText().toString(),
                            add_landmark.getText().toString(),
                            add_pincode.getText().toString(),
                            add_city.getText().toString()
                    );

                    //List<UserAddressItem> list=new ArrayList<>();
                    //  tempList=userAddressList;
                    // tempList.add(userAddressItem);

                    if (Internet.isConnectedToInternet(getApplicationContext())) {
                        userAddressList.add(userAddressItem);

                        UserAddress userAddress = new UserAddress(userAddressList);

                        databaseReference.child(MainActivity.phoneNumber).setValue(userAddress);
                        // loadOrders();
                        Toast.makeText(SelectAddress.this, "New Address Added Successfully", Toast.LENGTH_LONG).show();
                        Log.i("cms", "data");

                        userAddressList.remove(userAddressList.size() - 1);
                    } else {
                        Toast.makeText(SelectAddress.this, "Please check your Internet Connection !!!", Toast.LENGTH_SHORT).show();

                    }

                    dialog.dismiss();
                }
                else
                    return;

            }
        });
*/
  /*      alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });
*/

  //      AlertDialog dialog = alertDialogBuilder.create();

    //    dialog.setTitle("Add New Address..");
        //dialog.setMessage("Enter your Address");


      //  dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        //dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
      //  dialog.show();
    }
}
