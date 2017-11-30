package com.ssvmakers.amzonew.autobuynew;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;


import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;

/**
 * Created by Shubham on 11/16/2017.
 */

public class CustomDialog extends Dialog {
    private Context context;
    private TextView textView;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setTitleText(String text) {
        if (textView == null) {
            textView = findViewById(R.id.dialog_text);
        }
        this.textView.setText(text);

    }

    public void dismissWithAnimation() {
        this.getWindow().getAttributes().windowAnimations = R.anim.left_slide_dismiss;
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);
        textView = findViewById(R.id.dialog_text);
        textView.setTypeface(FontManager.getRaleway(context));
    }
}
