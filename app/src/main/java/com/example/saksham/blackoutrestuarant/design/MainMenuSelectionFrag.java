package com.example.saksham.blackoutrestuarant.design;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.DrawerUtil;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.tabfrag.BiryaniMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.DalMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.MushroomMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.MuttonMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.NonVegMenufrag;
import com.example.saksham.blackoutrestuarant.tabfrag.NonVegSoupMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.NonVegStarterMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.ParathaMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.RiceMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.RollMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.RotiMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.TeaCoffeeMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.VegMenufrag;
import com.example.saksham.blackoutrestuarant.tabfrag.VegSoupMenuFrag;
import com.example.saksham.blackoutrestuarant.tabfrag.VegStarterMenuFrag;

public class MainMenuSelectionFrag extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


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
        setContentView(R.layout.activity_main_menu_selection_frag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        DrawerUtil.getDrawer(this,toolbar);

        int fragId=getIntent().getIntExtra("ID",0);
        Log.i("cms","frag "+fragId);
        Toast.makeText(getApplicationContext(),Integer.toString(fragId),Toast.LENGTH_SHORT).show();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(fragId);
        mViewPager.setOffscreenPageLimit(0);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }



  public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle args;
            switch (position)
            {
                case 0:
                    args= new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    Log.i("cms","000");
                    VegStarterMenuFrag vegStarterMenuFrag=new VegStarterMenuFrag();
                    vegStarterMenuFrag.setArguments(args);
                    return vegStarterMenuFrag;

                case 1:
                    args = new Bundle();
                    args.putString("index",conve(position+1));
                    Log.i("cms","001");
                    //args.putInt("index",position);
                    NonVegStarterMenuFrag nonVegStarterMenuFrag=new NonVegStarterMenuFrag();
                    nonVegStarterMenuFrag.setArguments(args);
                    return nonVegStarterMenuFrag;
                case 2:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    Log.i("cms","002");
                    VegMenufrag vegMenufrag=new VegMenufrag();
                    vegMenufrag.setArguments(args);
                    return vegMenufrag;
                case 3:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    Log.i("cms","003");
                    NonVegMenufrag nonVegMenufrag=new NonVegMenufrag();
                    nonVegMenufrag.setArguments(args);
                    return nonVegMenufrag;
                case 4:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    Log.i("cms","004");
                    VegSoupMenuFrag vegSoupMenuFrag=new VegSoupMenuFrag();
                    vegSoupMenuFrag.setArguments(args);
                    return vegSoupMenuFrag;
                case 5:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    Log.i("cms","005");
                    NonVegSoupMenuFrag nonVegSoupMenuFrag=new NonVegSoupMenuFrag();
                    nonVegSoupMenuFrag.setArguments(args);
                    return nonVegSoupMenuFrag;
                case 6:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    Log.i("cms","006");
                    MuttonMenuFrag muttonMenuFrag=new MuttonMenuFrag();
                    muttonMenuFrag.setArguments(args);
                    return muttonMenuFrag;
                case 7:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    Log.i("cms","007");
                    DalMenuFrag dalMenuFrag=new DalMenuFrag();
                    dalMenuFrag.setArguments(args);
                    return dalMenuFrag;
                case 8:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    RotiMenuFrag rotiMenuFrag=new RotiMenuFrag();
                    rotiMenuFrag.setArguments(args);
                    return rotiMenuFrag;
                case 9:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    RiceMenuFrag riceMenuFrag=new RiceMenuFrag();
                    riceMenuFrag.setArguments(args);
                    return riceMenuFrag;
                case 10:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    BiryaniMenuFrag biryaniMenuFrag = new BiryaniMenuFrag();
                    biryaniMenuFrag.setArguments(args);
                    return biryaniMenuFrag;
                case 11:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    ParathaMenuFrag parathaMenuFrag=new ParathaMenuFrag();
                    parathaMenuFrag.setArguments(args);
                    return parathaMenuFrag;
                case 12:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    TeaCoffeeMenuFrag teaCoffeeMenuFrag=new TeaCoffeeMenuFrag();
                    teaCoffeeMenuFrag.setArguments(args);
                    return teaCoffeeMenuFrag;
                case 13:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    MushroomMenuFrag mushroomMenuFrag=new MushroomMenuFrag();
                    mushroomMenuFrag.setArguments(args);
                    return mushroomMenuFrag;
                case 14:
                    args=new Bundle();
                    args.putString("index",conve(position+1));
                    //args.putInt("index",position);
                    RollMenuFrag rollMenuFrag=new RollMenuFrag();
                    rollMenuFrag.setArguments(args);
                    return rollMenuFrag;

                default: return null;
            }
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 15;
        }
    }
    private String conve(int position)
    {
        if(position<=9)
            return "0"+position;

        return Integer.toString(position);
    }
}