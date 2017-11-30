package com.ssvmakers.amzonew.autobuynew.Adapter;

/**
 * Created by Shubham on 11/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.ssvmakers.amzonew.autobuynew.Model.SiteModel;
import com.ssvmakers.amzonew.autobuynew.NormalWebActivity;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;
import com.ssvmakers.amzonew.autobuynew.Utils.GDriveUtils;

import java.util.ArrayList;

/**
 * Created by Shubham on 11/6/2017.
 */
public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.HomeEViewHolder> {
    private ArrayList<SiteModel> data;
    private Context context;
    private int listType;

    public SiteAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public class HomeEViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private RelativeLayout relativeLayout;

        public HomeEViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            relativeLayout = itemView.findViewById(R.id.realitvelayout);
            title.setTypeface(FontManager.getRaleway(context));
        }
    }

    public void addMoreItem(ArrayList<SiteModel> more) {
        this.data.addAll(more);
        Log.d("itemSize", " " + more.size() + " = added");
        notifyDataSetChanged();
    }

    @Override
    public HomeEViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.site_item_layout, parent, false);
        return new HomeEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeEViewHolder holder, int position) {
        final SiteModel model = data.get(position);
        if (model.getImageUrl().contains("http://") || model.getImageUrl().contains("https://")) {
            Glide.with(context).load(model.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.image);
        } else {
            Glide.with(context).load(GDriveUtils.DriveHead + model.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.image);
        }

        holder.title.setText(model.getTitle().toUpperCase());
        Log.d("ssss", model.getImageUrl());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NormalWebActivity.class);
                intent.putExtra("url", model.getUrl());
                intent.putExtra("imageUrl", model.getImageUrl());
                intent.putExtra("title", model.getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

}