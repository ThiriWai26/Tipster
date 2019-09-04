package com.chann.tipster.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chann.tipster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment {


    public MatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_list, container, false);
        return view;
    }

}
