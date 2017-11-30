package com.ssvmakers.amzonew.autobuynew.AmzoScripts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssvmakers.amzonew.autobuynew.Adapter.SalePageAdapter;
import com.ssvmakers.amzonew.autobuynew.Model.SalePageModel;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;

import java.util.ArrayList;

public class ScriptHome extends AppCompatActivity {
    private RecyclerView sale_page_recyler;
    private ArrayList<SalePageModel> data = new ArrayList();
    private SalePageAdapter adapter;
    private DatabaseReference mDatabase;
    private com.elyeproj.loaderviewlibrary.LoaderImageView banner;
    private TextView opensalefor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_home);
        initUI();
        initListeners();
        initData();

    }

    private void initListeners() {

    }

    private void initData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Amzo_App");
        DatabaseReference reference = mDatabase.child("salePageItmes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SalePageModel> more = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    SalePageModel model = snapshot.getValue(SalePageModel.class);
                    more.add(model);
                }
                adapter.addMoreItem(more);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("salepagebanner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.getValue().toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(banner);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initUI() {
        banner = findViewById(R.id.salePageHeadimage);
        sale_page_recyler = findViewById(R.id.sale_page_recyler);
        opensalefor = findViewById(R.id.opensalefor);
        opensalefor.setTypeface(FontManager.getReckoner(getApplicationContext()));
        sale_page_recyler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        sale_page_recyler.setHasFixedSize(true);
        adapter = new SalePageAdapter(getApplicationContext());
        sale_page_recyler.setAdapter(adapter);

    }


}
