package com.example.jdgjapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mwh on 2018/1/21.
 */

public class AddressListFragment extends Fragment {

    public AddressListFragment() {

    }

    public static AddressListFragment newInstance(){
        AddressListFragment fragment = new AddressListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_address_list, container, false);
    }
}
