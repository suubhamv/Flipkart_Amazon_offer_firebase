package com.ssvmakers.amzonew.autobuynew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.ssvmakers.amzonew.autobuynew.Utils.GDriveUtils;

import java.util.ArrayList;

/**
 * Created by Shubham on 11/5/2017.
 */

public class HomeEAdapter extends RecyclerView.Adapter<HomeEAdapter.HomeEViewHolder> {
    private ArrayList<HomeOfferModel> data;
    private Context context;
    private int listType;

    public HomeEAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;

    }

    public class HomeEViewHolder extends RecyclerView.ViewHolder {
        private ImageView esiteimage;
        private Button opendealbutton;
        private TextView offerTitle;
        private TextView offerPrice;
        private TextView offerNewPrice;
        private TextView offerSite;
        private TextView offper;


        public HomeEViewHolder(View itemView) {
            super(itemView);
            esiteimage = itemView.findViewById(R.id.esiteimage);
            opendealbutton = itemView.findViewById(R.id.opendealbutton);
            offerTitle = itemView.findViewById(R.id.data);
            offerPrice = itemView.findViewById(R.id.price);
            offerNewPrice = itemView.findViewById(R.id.newprice);
            offper = itemView.findViewById(R.id.offper);
            offerSite = itemView.findViewById(R.id.offerSrc);
            offerSite.setTypeface(FontManager.getReckoner(context));

            //offerNewPrice.setTypeface(FontManager.getAwesome(context));
            offerTitle.setTypeface(FontManager.getRaleway(context));
            //  offerPrice.setTypeface(FontManager.getAwesome(context));
            offerPrice.setPaintFlags(offerPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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

        if (model.getimageurl().contains("http")) {
            Glide.with(context).load(model.getimageurl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.esiteimage);
        } else {
            Glide.with(context).load(GDriveUtils.DriveHead + model.getimageurl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.esiteimage);
        }
        holder.offerSite.setText(model.getSrc());
        holder.offerTitle.setText(model.getProductname());
        holder.offerPrice.setText("M.R.P : " + model.getOldprice().toString());
        holder.offerNewPrice.setText("Offer : " + model.getNewprice().toString());
        holder.offerNewPrice.setTypeface(FontManager.getAwesome(context));
        holder.offerPrice.setTypeface(FontManager.getAwesome(context));
        holder.offper.setText(model.getOff());
        //holder.offp   er.setText();
        holder.esiteimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NormalWebActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("imageUrl", model.getPageurl());
                intent.putExtra("title", model.getProductname());
                intent.putExtra("icon", model.getimageurl());
                intent.putExtra("url", model.getPageurl());
                context.startActivity(intent);
            }
        });
        holder.opendealbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NormalWebActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("imageUrl", model.getPageurl());
                intent.putExtra("title", model.getProductname());
                intent.putExtra("icon", model.getimageurl());
                intent.putExtra("url", model.getimageurl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

}
