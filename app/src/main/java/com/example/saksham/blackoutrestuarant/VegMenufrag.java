package com.example.saksham.blackoutrestuarant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by saksham_ on 19-Mar-18.
 */

public class VegMenufrag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_veg_menu_selection, container, false);
        return rootView;
    }
}
