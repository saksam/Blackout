package com.example.saksham.blackoutrestuarant.main;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
//import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.DrawerUtil;
import com.example.saksham.blackoutrestuarant.adapter.MenuViewHolder;
//import com.example.saksham.blackoutrestuarant.databinding.ActivityMainBinding;
import com.example.saksham.blackoutrestuarant.design.Cart;
import com.example.saksham.blackoutrestuarant.design.FoodDetail;
import com.example.saksham.blackoutrestuarant.design.HomeMenuData;
import com.example.saksham.blackoutrestuarant.design.MainMenuSelectionFrag;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.example.saksham.blackoutrestuarant.item.Banner;
import com.example.saksham.blackoutrestuarant.service.Internet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
/*
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }
*/

    Toolbar toolbar;

    HashMap<String,String> image_list;
    SliderLayout mSlider;

    ProgressDialog progress;
    ViewPager viewPager;
    LinearLayout sliderDots;
    public int dotCounts;
    public ImageView[] dots;
   // public ActivityMainBinding mBinding;

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth au;
    public static String phoneNumber;

    FirebaseRecyclerAdapter<HomeMenuData,MenuViewHolder> adapter;

    @Override
    protected void onStart() {
        super.onStart();
        if(Internet.isConnectedToInternet(getBaseContext()))
            adapter.startListening();
      //  else
      //  {
      //      check();
      //  }
    }

    @Override
    protected void onStop() {
        super.onStop();

        //adapter.stopListening();

        mSlider.stopAutoCycle();
    }

    @Override
    protected void onResume() {
        super.onResume();

       if(Internet.isConnectedToInternet(getBaseContext()))
            adapter.startListening();
      // else
      //      check();


        mSlider.startAutoCycle();
    }

    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_btn, menu);

        MenuItem getItem = menu.findItem(R.id.open_Cart);
        if (getItem != null) {

            getItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this,Cart.class));
                    return true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("HomeMenuData");

        au=FirebaseAuth.getInstance();
        phoneNumber=au.getCurrentUser().getPhoneNumber();


        toolbar=(Toolbar)findViewById(R.id.myTool);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);


        DrawerUtil.getDrawer(this,toolbar);
        //drawer.closeDrawer();


        progress = new ProgressDialog(this);
        progress.setTitle("Loading Menu");
        progress.setMessage("Please wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

     /*   FloatingActionButton fab = findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,Cart.class));
            }
        });

*/
     /*
        ValueEventListener valueEventListener=new ValueEventListener() {
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
/*
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewPager=mBinding.viewPager;
        sliderDots=mBinding.SliderDots;
        SwipeAdapter swipeAdapter=new SwipeAdapter(this);
        viewPager.setAdapter(swipeAdapter);
        dotCounts=swipeAdapter.getCount();
        dots=new ImageView[dotCounts];

        for(int i=0;i<dotCounts;i++)
        {
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for(int i=0;i<dotCounts;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

*/
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


       // initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
     //   recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 5, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.i("cms","abc");

        if(Internet.isConnectedToInternet(getBaseContext())) {
            loadMenu();
            adapter.startListening();
        }
        else
        {
            check();
        }
        Log.i("cms","def");



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
               // Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                //        Toast.LENGTH_SHORT).show();

                if(Internet.isConnectedToInternet(getApplicationContext())) {
                    if (position == 0) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 0);
                        startActivity(intent);
                    } else if (position == 1) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 1);
                        startActivity(intent);
                    } else if (position == 2) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 2);
                        startActivity(intent);
                    } else if (position == 3) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 3);
                        startActivity(intent);
                    } else if (position == 4) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 4);
                        startActivity(intent);
                    } else if (position == 5) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 5);
                        startActivity(intent);
                    } else if (position == 6) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 6);
                        startActivity(intent);
                    } else if (position == 7) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 7);
                        startActivity(intent);
                    } else if (position == 8) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 8);
                        startActivity(intent);
                    } else if (position == 9) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 9);
                        startActivity(intent);
                    } else if (position == 10) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 10);
                        startActivity(intent);
                    } else if (position == 11) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 11);
                        startActivity(intent);
                    } else if (position == 12) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 12);
                        startActivity(intent);
                    } else if (position == 13) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 13);
                        startActivity(intent);
                    } else if (position == 14) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuSelectionFrag.class);
                        intent.putExtra("ID", 14);
                        startActivity(intent);
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"Check your Internet Connection !!!",Toast.LENGTH_SHORT).show();

            }

        }));

      //  Timer timer=new Timer();
      //  timer.scheduleAtFixedRate(new myTimerTask(),4000,4000);



        //Slider
        setupSlider();

    }



    public void check()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Please check your Internet Connection !!!")
                .setCancelable(false)
                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(Internet.isConnectedToInternet(getBaseContext()))
                        {

                            loadMenu();
                            adapter.startListening();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please check your Internet Connection !!!",Toast.LENGTH_LONG).show();
                            check();
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setupSlider()
    {
        mSlider=(SliderLayout)findViewById(R.id.slider);
        image_list=new HashMap<>();
        final DatabaseReference databaseRef=firebaseDatabase.getReference("Banner");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Banner banner =dataSnapshot1.getValue(Banner.class);
                    image_list.put(banner.getName()+"_"+ banner.getId(), banner.getImage());
                }
                for(String key:image_list.keySet())
                {
                    String[] keySplit=key.split("_");
                    String nameOfFood=keySplit[0];
                    final String idOfFood=keySplit[1];

                    TextSliderView textSliderView=new TextSliderView(getBaseContext());
                    textSliderView
                            .description(nameOfFood)
                            .image(image_list.get(key))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {


                                    Intent foodDetail=new Intent(getApplicationContext(), FoodDetail.class);
                                    foodDetail.putExtra("FoodId",idOfFood);
                                    startActivity(foodDetail);


                                }
                            });
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("FoodId",idOfFood);

                    mSlider.addSlider(textSliderView);

                    databaseRef.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
    }


    void loadMenu()
    {
      //  databaseReference = databaseReference.child("01");
        //Query query=databaseReference.orderByKey();
        progress.show();
        FirebaseRecyclerOptions option=new FirebaseRecyclerOptions.Builder<HomeMenuData>().setQuery(databaseReference,HomeMenuData.class).build();
        adapter=new FirebaseRecyclerAdapter<HomeMenuData, MenuViewHolder>(option) {

            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_card, parent, false);
                return new MenuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull HomeMenuData model) {

                holder.textView.setText(model.getName());
                Log.i("cms","pqr");
                Picasso.with(getBaseContext()).load(model.getImage()).into(holder.imageView);
                progress.dismiss();
            }
        };

        Log.i("cms","xyz");
        recyclerView.setAdapter(adapter);
        Log.i("cms","xyz1");
    }

    public class myTimerTask extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem()==0)
                        viewPager.setCurrentItem(1);
                    else if(viewPager.getCurrentItem()==1)
                        viewPager.setCurrentItem(2);
                    else
                        viewPager.setCurrentItem(0);

                }
            });
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}



