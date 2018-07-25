package com.example.saksham.blackoutrestuarant.design;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.DrawerUtil;
import com.example.saksham.blackoutrestuarant.database.Database;
import com.example.saksham.blackoutrestuarant.interfaces.OnGetDataListener;
import com.example.saksham.blackoutrestuarant.item.CartDetail;
import com.example.saksham.blackoutrestuarant.item.Product;
import com.example.saksham.blackoutrestuarant.item.Quant;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ElegantNumberButton numberButton;
    Spinner spinner;

    Toolbar toolbar;

    Product food,foodTemp;
    Button addToCart;

    String subcat;
    String foodId = "";
    String cat;
    String strQuantity;

    HashMap<String, Double> hashMap;

    ArrayList<String> quantity;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, secondReference;

    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_btn, menu);

        MenuItem getItem = menu.findItem(R.id.open_Cart);
        if (getItem != null) {

            getItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),Cart.class));
                    return true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

//        Log.i("cms", MainActivity.phoneNumber);

        foodTemp=new Product();

        firebaseDatabase = FirebaseSingleDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FoodItemDetail");
//        secondReference=firebaseDatabase.getReference("ItemSize");

        toolbar=findViewById(R.id.myTooll);
        setSupportActionBar(toolbar);

        DrawerUtil.getDrawer(this,toolbar);

        hashMap = new HashMap<>();
        quantity = new ArrayList<>();

        addToCart=(Button)findViewById(R.id.addToCart);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        food_description = (TextView) findViewById(R.id.food_Description);
        food_name = (TextView) findViewById(R.id.foodName);
        food_price = (TextView) findViewById(R.id.foodPrice);
        food_image = (ImageView) findViewById(R.id.img_food);

        spinner = (Spinner) findViewById(R.id.dropDown);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingLayoutt);

        foodId = getIntent().getStringExtra("FoodId");
        cat = foodId.substring(0, 2);
        subcat = foodId.substring(2, 3);
        strQuantity = null;


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("cms","item " +food.getItemId());
                double p = Double.parseDouble(food_price.getText().toString());
                int q = Integer.parseInt(numberButton.getNumber());

                new Database(getBaseContext()).addToCart(new CartDetail(
                        foodId,
                        MainActivity.phoneNumber,
                        food_name.getText().toString(),

                        spinner.getSelectedItem().toString(),
                        p/q,
                        Integer.parseInt(numberButton.getNumber())));
                Toast.makeText(FoodDetail.this,"Added To Cart",Toast.LENGTH_SHORT).show();
            }
        });

        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {


            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                Double temp;
                String val = spinner.getSelectedItem().toString();
                temp=hashMap.get(val);
                temp=temp*newValue;
                food_price.setText(temp.toString());

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    Double temp;
                    temp = hashMap.get(spinner.getSelectedItem().toString());
                    temp = temp * Integer.parseInt(numberButton.getNumber());
                    food_price.setText(temp.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingAppbar);



        mCheckInforInServer(cat, subcat);

    }

    private void getQuantity(final OnGetDataListener listener) {
        listener.onStart();
        databaseReference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                hashMap.clear();
                Quant quant = dataSnapshot.getValue(Quant.class);
                if (!quant.getQuarter().equals("0"))
                    quantity.add("Quarter");
                if (!quant.getHalf().equals("0"))
                    quantity.add("Half");
                if (!quant.getFull().equals("0"))
                    quantity.add("Full");

                hashMap.put("Quarter", Double.parseDouble(quant.getQuarter()));
                hashMap.put("Half", Double.parseDouble(quant.getHalf()));
                hashMap.put("Full", Double.parseDouble(quant.getFull()));


                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getDetailfood(String cat, String subcat, final OnGetDataListener listener) {

        listener.onStart();
        databaseReference.child(cat).child(subcat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food = dataSnapshot.getValue(Product.class);
                foodTemp=food;
                Picasso.with(getBaseContext()).load(food.getImage()).into(food_image);
                strQuantity = food.getQuantity();
                collapsingToolbarLayout.setTitle(food.getName());
                food_price.setText(food.getPrice());
                hashMap.put("Regular",Double.parseDouble(food.getPrice()));
                food_name.setText(food.getName());
                food_description.setText(food.getDescription());

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void mCheckInforInServer(String cat, String subcat) {
        getDetailfood(cat, subcat, new OnGetDataListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
                //Log.i("cms", "start");
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                //DO SOME THING WHEN GET DATA SUCCESS HERE
                quantity.clear();

                //Log.i("cms", "histr  " + strQuantity);
                if (strQuantity.equals("0")) {
                    quantity.add("Regular");

                    //ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,android.R.layout.simple_spinner_item,quantity);

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, quantity);

                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(spinnerAdapter);
                } else {
                    databaseReference = firebaseDatabase.getReference("ItemSize");
                    //getQuantity();
                    checkQuantity();
                }
                //databaseReference=firebaseDatabase.getReference("FoodItemDetail");

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void checkQuantity() {
        getQuantity(new OnGetDataListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
//                Log.i("cms", "start");
            }

            @Override
            public void onSuccess(DataSnapshot data) {

                //spinner = (Spinner) findViewById(R.id.dropDown);

                //ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,android.R.layout.simple_spinner_item,quantity);

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, quantity);

                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(spinnerAdapter);

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
