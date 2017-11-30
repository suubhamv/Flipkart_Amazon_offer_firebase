package com.ssvmakers.amzonew.autobuynew.Adapter;

import android.content.Context;

/**
 * Created by Shubham on 11/19/2017.
 */


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.ssvmakers.amzonew.autobuynew.Model.HomeOfferModel;
import com.ssvmakers.amzonew.autobuynew.NormalWebActivity;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;

import java.util.ArrayList;

/**
 * Created by Shubham on 11/5/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.HomeEViewHolder> {
    private ArrayList<HomeOfferModel> data;
    private Context context;
    private int listType;

    public NotificationAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;

    }

    public class HomeEViewHolder extends RecyclerView.ViewHolder {
        private ImageView esiteimage;
        private Button opendealbutton;
        private TextView offerTitle;
        private TextView offerPrice;
        private TextView offerSite;


        public HomeEViewHolder(View itemView) {
            super(itemView);
            esiteimage = itemView.findViewById(R.id.esiteimage);
            opendealbutton = itemView.findViewById(R.id.opendealbutton);
            offerTitle = itemView.findViewById(R.id.data);
            offerPrice = itemView.findViewById(R.id.price);
            offerSite = itemView.findViewById(R.id.offerSrc);
            offerSite.setTypeface(FontManager.getReckoner(context));
            offerTitle.setTypeface(FontManager.getReckoner(context));
            offerPrice.setTypeface(FontManager.getAwesome(context));
        }
    }

    public void addMoreItem(ArrayList<HomeOfferModel> more) {
        this.data.addAll(more);
        Log.d("itemSize", " " + more.size() + " = added");
        notifyDataSetChanged();
    }

    @Override
    public HomeEViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items_grid, parent, false);
        return new HomeEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeEViewHolder holder, final int position) {
        final HomeOfferModel model = data.get(position);
        Glide.with(context).load(model.getimageurl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.esiteimage);
        holder.offerSite.setText(model.getSrc());
        holder.offerTitle.setText(model.getProductname());
        holder.offerPrice.setText("Price - " + model.getNewprice().toString());
        holder.offerPrice.setTypeface(FontManager.getAwesome(context));

        holder.opendealbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NormalWebActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", model.getPageurl());
                intent.putExtra("url", model.getProductname());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

}
