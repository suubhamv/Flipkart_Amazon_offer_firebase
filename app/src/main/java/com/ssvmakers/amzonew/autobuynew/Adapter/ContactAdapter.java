package com.ssvmakers.amzonew.autobuynew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.ssvmakers.amzonew.autobuynew.Model.ProfileModel;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;
import com.transitionseverywhere.ChangeText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Shubham on 11/10/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.HomeViewHolder> {
    private ArrayList<ProfileModel> data;
    private Context context;
    private int listType;

    public ContactAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;

    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView fb, whatsApp, phone, name;
        ImageView adminimage;
        private ViewGroup view;

        public HomeViewHolder(View view) {
            super(view);
            whatsApp = view.findViewById(R.id.contact_whatsapp);
            fb = view.findViewById(R.id.contact_fb);
            this.view=view.findViewById(R.id.viewGroup);
            phone = view.findViewById(R.id.contact_call);
            name = view.findViewById(R.id.name);
            adminimage = view.findViewById(R.id.admin_pic);
            whatsApp.setTypeface(FontManager.getAwesome(context));
            phone.setTypeface(FontManager.getAwesome(context));
            fb.setTypeface(FontManager.getAwesome(context));
            name.setTypeface(FontManager.getRaleway(context));
        }
    }

    public void addMoreItem(ArrayList<ProfileModel> more) {
        this.data.addAll(more);
        notifyDataSetChanged();

    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_us_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        final ProfileModel model = data.get(position);
        Glide.with(context).load(model.getPicUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.adminimage);
        holder.name.setText(model.getName());
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = "" + model.getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tel));
                context.startActivity(intent);


            }
        });
        holder.whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsNumber = model.getWaUrl();//without '+'
                try {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");

                   // TransitionManager.beginDelayedTransition(holder.view, new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN));
                    holder.name.setText(model.getPhone()+"");

                    //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                /*    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, " ");
                    sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);*/
                } catch (Exception e) {
                    Toast.makeText(context, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stackoverflow.com")));
            }
        });

        Log.d("binding data", position + "");
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

