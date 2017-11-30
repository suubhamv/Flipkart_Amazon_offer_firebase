package com.ssvmakers.amzonew.autobuynew;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ssvmakers.amzonew.autobuynew.Utils.GDriveUtils;


public class NormalWebActivity extends AppCompatActivity {
    private WebView myWebView;
    private TextView heading;
    private ImageView icon, refresh;
    ProgressBar spinner;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        initViews();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent.getStringExtra("imageUrl").contains("http")){
            Glide.with(this).load(intent.getStringExtra("imageUrl")).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(icon);
        }else{
            Glide.with(this).load(GDriveUtils.DriveHead+intent.getStringExtra("imageUrl")).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(icon);

        }

        heading.setText(intent.getStringExtra("title"));
        Log.d("dataintent",intent.getStringExtra("imageUrl")+"- "+intent.getStringExtra("title"));
        initScript(intent.getStringExtra("url"));

    }

    private void initViews() {
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myWebView = findViewById(R.id.webview);

        refresh = findViewById(R.id.refreshimageview);
        icon = findViewById(R.id.mobileimage);

        heading = findViewById(R.id.modelnametv);
        spinner = findViewById(R.id.progressBar1);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                animation.setRepeatCount(2);
                refresh.startAnimation(animation);
                myWebView.reload();

            }
        });
        //=============web view===========
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        CookieSyncManager.getInstance().sync();
        CookieManager.setAcceptFileSchemeCookies(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptCookie();
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        this.myWebView.setWebViewClient(new WebViewClient());
        this.myWebView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        this.myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webactivtty_toolbarmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.copy:
                if (!myWebView.getUrl().equals(null)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("itemurl", myWebView.getUrl().toUpperCase());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Someting wrong!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share:

                if (!myWebView.getUrl().equals(null)) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey Check this product through AMZO APP..");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, myWebView.getUrl().toString());
                    startActivity(Intent.createChooser(intent, "Share With"));
                } else {
                    Toast.makeText(this, "Please wait until page is opening...", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return true;
    }

    private void initScript(final String pageurl) {

        if (Build.VERSION.SDK_INT >= 19) {
            WebView webView = this.myWebView;
            WebView.setWebContentsDebuggingEnabled(true);
        }
        this.myWebView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        this.myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                spinner.setVisibility(View.VISIBLE);
                Log.d("url", url);
            }

            public void onPageFinished(final WebView view, String url) {
                spinner.setVisibility(View.GONE);
                CookieSyncManager.getInstance().sync();
                Log.d("url", url);

            }
        });
        Log.d("urlRecive", ""+pageurl);
        this.myWebView.loadUrl(pageurl);
        Snackbar.make(myWebView, "Please Wait redirecting... ", 5).show();
    }

}
