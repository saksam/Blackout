package com.example.saksham.blackoutrestuarant;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bumptech.glide.Glide;
import com.example.saksham.blackoutrestuarant.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    ViewPager viewPager;
    LinearLayout sliderDots;
    public int dotCounts;
    public ImageView[] dots;
    public ActivityMainBinding mBinding;

    private RecyclerView recyclerView;
    private MainHomeMenu_Adapter adapter;
    private List<Menu_item> menuList;

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
*/
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


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


       // initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        menuList = new ArrayList<>();
        adapter = new MainHomeMenu_Adapter(this, menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
               // Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                //        Toast.LENGTH_SHORT).show();

                if(position==0)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",0);
                    startActivity(intent);
                }
                else if(position==1)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",1);
                    startActivity(intent);
                }
                else if(position==2)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",2);
                    startActivity(intent);
                }
                else if(position==3)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",3);
                    startActivity(intent);
                }
                else if(position==4)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",4);
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));


        prepareAlbums();


        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new myTimerTask(),4000,4000);

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

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album0,
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9};

        Menu_item a = new Menu_item("Veg",covers[0]);
        menuList.add(a);

        a = new Menu_item("Non Veg",covers[1]);
        menuList.add(a);

        a = new Menu_item("Snacks",covers[2]);
        menuList.add(a);

        a = new Menu_item("Born to Die",covers[3]);
        menuList.add(a);

        a = new Menu_item("Honeymoon",covers[4]);
        menuList.add(a);

        a = new Menu_item("I Need a Doctor",covers[5]);
        menuList.add(a);

        a = new Menu_item("Loud",covers[6]);
        menuList.add(a);

        a = new Menu_item("Legend",covers[7]);
        menuList.add(a);

        a = new Menu_item("Hello",covers[8]);
        menuList.add(a);

        a = new Menu_item("Greatest Hits",covers[9]);
        menuList.add(a);

        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}

