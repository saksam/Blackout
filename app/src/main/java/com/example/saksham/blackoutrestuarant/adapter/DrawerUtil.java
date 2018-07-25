package com.example.saksham.blackoutrestuarant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.database.Database;
import com.example.saksham.blackoutrestuarant.design.Cart;
import com.example.saksham.blackoutrestuarant.design.LoginHome;
import com.example.saksham.blackoutrestuarant.design.MyOrders;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class DrawerUtil {

    public static Drawer getDrawer(final Activity activity, Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem = new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.headerdrawer)
                .addProfiles(
                        new ProfileDrawerItem().withName(MainActivity.phoneNumber).withIcon(R.drawable.proicon)
                )
                .build();

        PrimaryDrawerItem drawerItemHome = new PrimaryDrawerItem().withIdentifier(1)
                .withName("Home").withIcon(R.drawable.ic_home_black_24dp).withSelectable(false);
        PrimaryDrawerItem drawerItemCart = new PrimaryDrawerItem()
                .withIdentifier(2).withName("Cart").withIcon(R.drawable.ic_shopping_cart_black_24dp).withSelectable(false);
        PrimaryDrawerItem drawerItemOrder = new PrimaryDrawerItem()
                .withIdentifier(3).withName("Your Orders").withIcon(R.drawable.ic_favorite_border_black_24dp).withSelectable(false);

        SecondaryDrawerItem drawerItemSignOut = new SecondaryDrawerItem().withIdentifier(4)
                .withName("Sign Out").withIcon(R.drawable.ic_remove_circle_outline_black_24dp).withSelectable(false);
        SecondaryDrawerItem drawerItemContact = new SecondaryDrawerItem().withIdentifier(5)
                .withName("Contact Us").withIcon(R.drawable.contactus).withSelectable(false);
        SecondaryDrawerItem drawerItemAbout = new SecondaryDrawerItem().withIdentifier(6)
                .withName("About").withIcon(R.drawable.ic_info_outline_black_24dp).withSelectable(false);


        //create the drawer and remember the `Drawer` result object
        final Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        drawerItemHome,
                        drawerItemCart,
                        drawerItemOrder,
                        new DividerDrawerItem(),
                        drawerItemSignOut,
                        new DividerDrawerItem(),
                        drawerItemContact,
                        drawerItemAbout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                       /* if (drawerItem.getIdentifier() == 2 && !(activity instanceof MainActivity)) {
                            // load tournament screen
                            Intent intent = new Intent(activity, MainActivity.class);
                            view.getContext().startActivity(intent);
                        }*/
                        if (drawerItem.getIdentifier() == 1) {
                            //Toast.makeText(activity.getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                            if (!(activity instanceof MainActivity)) {
                                Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(i);
                                activity.finish();
                            }
                        }
                        if(drawerItem.getIdentifier()==2)
                        {
                            if (!(activity instanceof Cart)) {
                                Intent i = new Intent(activity.getApplicationContext(), Cart.class);
                                activity.startActivity(i);
                            }
                        }
                        if(drawerItem.getIdentifier()==3)
                        {
                            if (!(activity instanceof MyOrders)) {
                                Intent i = new Intent(activity.getApplicationContext(), MyOrders.class);
                                activity.startActivity(i);
                            }
                        }
                        if(drawerItem.getIdentifier()==4)
                        {
                            AuthUI.getInstance()
                                    .signOut(activity.getApplicationContext())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        public void onComplete(@NonNull Task<Void> task) {
                                            new Database(activity.getApplicationContext()).cleanCart();
                                            Toast.makeText(activity.getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();
                                            activity.startActivity(new Intent(activity.getApplicationContext(), LoginHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            activity.finish();
                                        }
                                    });

                        }

                        return false;
                    }
                })
                .build();
        return result;
    }

}
