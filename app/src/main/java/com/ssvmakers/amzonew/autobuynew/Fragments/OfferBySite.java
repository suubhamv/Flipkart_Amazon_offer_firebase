package com.ssvmakers.amzonew.autobuynew.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ssvmakers.amzonew.autobuynew.Adapter.HomeEAdapter;
import com.ssvmakers.amzonew.autobuynew.Adapter.SiteAdapter;
import com.ssvmakers.amzonew.autobuynew.Model.HomeOfferModel;
import com.ssvmakers.amzonew.autobuynew.Model.SiteModel;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.Utils;

import java.util.ArrayList;

public class OfferBySite extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView offerrecycler, siteRecycler;
    private TextView amazontv, flipkartv, paytmtv;
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private HomeEAdapter offeradapter;
    private DatabaseReference mDatabase;
    private SiteAdapter siteAdapter;

    public OfferBySite() {
        // Required empty public constructor
    }

    public static OfferBySite newInstance(String param1, String param2) {
        OfferBySite fragment = new OfferBySite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_by_site, container, false);
        initUi(view);
        initData();
        return view;
    }

    private void initData() {

        mDatabase = Utils.getDataBase(getContext()).getReference("Amzo_App");
        DatabaseReference homeoffers = mDatabase.child("homeoffers");
        DatabaseReference sites = mDatabase.child("sites");
        homeoffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HomeOfferModel> data = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HomeOfferModel model = snapshot.getValue(HomeOfferModel.class);
                    Log.d("imagelink", ""+model.getimageurl());
                    data.add(model);
                }
                offeradapter.addMoreItem(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SiteModel> data = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SiteModel model = snapshot.getValue(SiteModel.class);
                    data.add(model);
                }
                siteAdapter.addMoreItem(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initUi(View view) {
        offerrecycler = view.findViewById(R.id.offerrecycler);
        siteRecycler = view.findViewById(R.id.site_recyclerview);
        siteAdapter = new SiteAdapter(getContext());
        siteRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        siteRecycler.setHasFixedSize(true);
        siteRecycler.setAdapter(siteAdapter);
        offerrecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        offeradapter = new HomeEAdapter(getContext());
        offerrecycler.setAdapter(offeradapter);

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
