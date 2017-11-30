package com.ssvmakers.amzonew.autobuynew.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssvmakers.amzonew.autobuynew.Adapter.ContactAdapter;
import com.ssvmakers.amzonew.autobuynew.Model.ProfileModel;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;
import com.ssvmakers.amzonew.autobuynew.Utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class ContactUsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView contactRecycler;
    private ContactAdapter adapter;
    private TextView email, mobile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private DatabaseReference mDatabase;


    public ContactUsFragment() {
        // Required empty public constructor
    }

    public static ContactUsFragment newInstance(String param1, String param2) {
        ContactUsFragment fragment = new ContactUsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        contactRecycler = view.findViewById(R.id.profile_recycler);
        email = view.findViewById(R.id.mobile);
        mobile = view.findViewById(R.id.email);
        email.setTypeface(FontManager.getRaleway(getContext()));
        mobile.setTypeface(FontManager.getRaleway(getContext()));
        contactRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        contactRecycler.setHasFixedSize(true);
        adapter = new ContactAdapter(getContext());
        contactRecycler.setAdapter(adapter);
        initData();
        return view;
    }

    private void initData() {
        email.setText(getResources().getString(R.string.emailcontact_string));
        mobile.setText(getResources().getString(R.string.mobilecontact_string));

        mDatabase = Utils.getDataBase(getContext()).getReference("Amzo_App");
        DatabaseReference contact = mDatabase.child("contact");
        contact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ProfileModel> data = new ArrayList<>();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ProfileModel model = snapshot.getValue(ProfileModel.class);

                    if (model.getName().contains("SHUBHAM")) {
                        data.add(0, model);
                    } else {
                        data.add(model);
                    }

                    Log.d("loaddone", model.getName());
                }

                adapter.addMoreItem(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });


        FirebaseDatabase.getInstance().getReference("contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ProfileModel> data = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProfileModel model = snapshot.getValue(ProfileModel.class);
                    data.add(model);
                    Log.d("loaddone", model.getName());
                }

                adapter.addMoreItem(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
