package com.kytelabs.bleduino.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kytelabs.bleduino.R;
import com.kytelabs.bleduino.adapters.ConnectionManagerListAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionManagerFragment extends Fragment {

    //Member Variables
    //--------------------------------------------------------------------------------
    @InjectView(R.id.connectionManagerRecyclerView) RecyclerView mRecyclerView;
    @InjectView(R.id.connectionManagerRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    public ConnectionManagerFragment() {
        // Required empty public constructor
    }

    //================================================================================
    // On Create View (OnCreate equivalent for fragments)
    //================================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connection_manager, container, false);
        ButterKnife.inject(this, view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);


        setupAdapter();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.accentColor);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupAdapter();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        return view;
    }

    private void setupAdapter() {

        ConnectionManagerListAdapter adapter = new ConnectionManagerListAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);

    }


}
