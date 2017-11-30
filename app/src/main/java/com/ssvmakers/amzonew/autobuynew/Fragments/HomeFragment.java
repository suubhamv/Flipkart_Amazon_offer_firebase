package com.ssvmakers.amzonew.autobuynew.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssvmakers.amzonew.autobuynew.Adapter.HomeRecyclerView;
import com.ssvmakers.amzonew.autobuynew.Adapter.SalePageAdapter;
import com.ssvmakers.amzonew.autobuynew.Model.HomeModel;

import com.ssvmakers.amzonew.autobuynew.Model.HomeOfferModel;
import com.ssvmakers.amzonew.autobuynew.Model.SalePageModel;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;
import com.ssvmakers.amzonew.autobuynew.Utils.Utils;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM2 = "param2";
    //   private ScrollView scrollView;
    private String mParam1;
    private FrameLayout fragmentContainer;
    private RecyclerView homeRecyclerView;//, home_fragment_recycler_offers;
    private TextView home_offer_tv;//, todays_offer_tv;
    private OnFragmentInteractionListener mListener;
    private HomeRecyclerView homeAdapter;
    //private HomeEAdapter home_fragment_recycler_offers_adapter;
    private ArrayList<HomeModel> data = new ArrayList<>();
    private ArrayList<HomeOfferModel> data1 = new ArrayList<>();
    // private Button salepage_button;
    //private ImageView sale_image;
    private DatabaseReference mDatabase;
    private RecyclerView siteRecyclerview;
    private SalePageAdapter siteAdapter;

    public HomeFragment() {   // Required empty public constructor
    }

    public static HomeFragment newInstance(String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        initFonts();
        initListeners();
        fetchData();
        initData();

        return view;
    }

    private void initListeners() {


    }

    private void initFonts() {
        home_offer_tv.setTypeface(FontManager.getReckoner(getContext()));

    }

    private void initComponents(View view) {
        //==============================straggredGridLayoutManager-=====================================

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        home_offer_tv = view.findViewById(R.id.home_offer_tv);
        fragmentContainer = view.findViewById(R.id.containers);
        homeRecyclerView = view.findViewById(R.id.home_recyclerview);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        siteRecyclerview = view.findViewById(R.id.offerrecycler);
        siteRecyclerview.setHasFixedSize(true);
        siteRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        siteAdapter = new SalePageAdapter(getContext());
        siteRecyclerview.setAdapter(siteAdapter);
        homeAdapter = new HomeRecyclerView(data, getActivity());
        homeRecyclerView.setAdapter(homeAdapter);

    }

    private void fetchData() {
        mDatabase = Utils.getDataBase(getContext()).getReference("Amzo_App");
        DatabaseReference mSlides = mDatabase.child("slides");
        DatabaseReference mHomeOffers = mDatabase.child("homeoffers");
        DatabaseReference msale_day_image_url = mDatabase.child("sale_day_image_url");
        mSlides.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HomeModel> more = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("dataSnapkey", snapshot.getValue().toString());
                    HomeModel model = snapshot.getValue(HomeModel.class);
                    more.add(model);
                }
                homeAdapter.addMoreItem(more);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Someting wrong Please try again", Toast.LENGTH_LONG);
            }
        });
        msale_day_image_url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("saleimage", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*mHomeOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HomeOfferModel> more = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HomeOfferModel model = snapshot.getValue(HomeOfferModel.class);
                    Log.d("HomeofferModel", model.getImageUrl() + "-");
                    more.add(model);

                }
                home_fragment_recycler_offers_adapter.addMoreItem(more);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
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

    public void refresh() {
     /*   if (getArguments().getInt("index", 0) > 0 && scrollView != null) {
            scrollView.fullScroll(ScrollView.SCROLL_AXIS_VERTICAL);
        }*/
    }

    public void willBeDisplayed() {
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    private void initData() {
        final ProgressDialog p = new ProgressDialog(getContext());
        p.setTitle("Please Wait...");
        p.setMessage("Loading...");
        p.setIndeterminate(true);
        p.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Amzo_App");
        DatabaseReference reference = mDatabase.child("salePageItmes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (p.isShowing()) {
                    p.dismiss();
                }
                ArrayList<SalePageModel> more = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SalePageModel model = snapshot.getValue(SalePageModel.class);
                    more.add(model);
                }
                siteAdapter.addMoreItem(more);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


}
