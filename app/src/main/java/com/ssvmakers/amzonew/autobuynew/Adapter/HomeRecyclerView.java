package com.ssvmakers.amzonew.autobuynew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ssvmakers.amzonew.autobuynew.Model.HomeModel;

import com.ssvmakers.amzonew.autobuynew.NormalWebActivity;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;

import java.util.ArrayList;


/**
 * Created by Shubham on 11/4/2017.
 */

public class HomeRecyclerView extends RecyclerView.Adapter<HomeRecyclerView.HomeViewHolder> {
    private ArrayList<HomeModel> data;
    private Context context;
    private int listType;

    public HomeRecyclerView(ArrayList<HomeModel> data, Context context) {
        this.data = data;
        this.context = context;

    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView offerTitle;

        public HomeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_item_imageview);
            offerTitle = itemView.findViewById(R.id.home_item_textview);
        }
    }

    public void addMoreItem(ArrayList<HomeModel> more) {
        this.data.addAll(more);
        notifyDataSetChanged();
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_view, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        final HomeModel model = data.get(position);
        Glide.with(context).load(model.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.imageView);
        holder.offerTitle.setText(model.getTitle());
        holder.offerTitle.setTypeface(FontManager.getReckoner(context));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NormalWebActivity.class);
                intent.putExtra("url", model.getUrl().toString());
                intent.putExtra("title", model.getTitle().toString());
                intent.putExtra("imageUrl", model.getImageUrl().toString());
                context.startActivity(intent);
            }
        });
        Log.d("binding data", position + "");
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

