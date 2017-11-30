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

import com.ssvmakers.amzonew.autobuynew.Model.SalePageModel;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;
import com.ssvmakers.amzonew.autobuynew.WebScriptActivity;

import java.util.ArrayList;

/**
 * Created by Shubham on 11/6/2017.
 */
public class SalePageAdapter extends RecyclerView.Adapter<SalePageAdapter.HomeEViewHolder> {
    private ArrayList<SalePageModel> data;
    private Context context;
    private int listType;

    public SalePageAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public class HomeEViewHolder extends RecyclerView.ViewHolder {
        private ImageView sale_image;
        private TextView phone_name;

        public HomeEViewHolder(View itemView) {
            super(itemView);
            sale_image = itemView.findViewById(R.id.sale_image);
            phone_name = itemView.findViewById(R.id.phone_name);
            phone_name.setTypeface(FontManager.getReckoner(context));
        }
    }

    public void addMoreItem(ArrayList<SalePageModel> more) {
        this.data.addAll(more);
        Log.d("itemSize", " " + more.size() + " = added");
        notifyDataSetChanged();
    }

    @Override
    public HomeEViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_page_list, parent, false);
        return new HomeEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeEViewHolder holder, int position) {
        final SalePageModel model = data.get(position);
        Glide.with(context).load(model.getImageurl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.sale_image);
        holder.phone_name.setText(model.getModelname().toUpperCase());
        Log.d("ssss",""+ model.getImageurl());
        holder.sale_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Click event triggered",model.getModelname()+"--"+model.getPageurl().toLowerCase());
                Intent intent = new Intent(context, WebScriptActivity.class);
                intent.putExtra("pageurl", model.getPageurl());
                intent.putExtra("modelname", model.getModelname());
                intent.putExtra("imageurl", model.getImageurl());
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