package com.ssvmakers.amzonew.autobuynew;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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


import com.ssvmakers.amzonew.autobuynew.Utils.FontManager;

import angtrim.com.fivestarslibrary.FiveStarsDialog;
import angtrim.com.fivestarslibrary.NegativeReviewListener;
import angtrim.com.fivestarslibrary.ReviewListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class WebScriptActivity extends AppCompatActivity {
    private ImageView refresh;
    private TextView heading;
    private CircleImageView mobileimage;
    private WebView myWebView;
    private ProgressBar spinner;
    private Toolbar toolbar;
    private CoordinatorLayout root_view;
    private String modelName;
    private ProgressBar dialog;
    private String url;
    private CharSequence thankyou = "https://www.amazon.in/gp/buy/thankyou";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_script);

        initUi();
        initWebView();
        initData();

    }

    private void initWebView() {

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

    private void initData() {
        final Intent intent = getIntent();
        modelName = intent.getStringExtra("modelname");
        heading.setText(modelName);
        url = intent.getStringExtra("pageurl");
        Glide.with(getApplicationContext()).load(intent.getStringExtra("imageurl")).diskCacheStrategy(DiskCacheStrategy.ALL).into(mobileimage);

        initProcess();
        //initScript(intent.getStringExtra("pageurl"));
    }

    private void initProcess() {
        final String modelname = getIntent().getStringExtra("modelname").toLowerCase();
        final String pageurl = getIntent().getStringExtra("modelname").toLowerCase();
        Toast.makeText(getApplicationContext(), modelname, Toast.LENGTH_LONG).show();
        switch (modelname) {
            case "redmi y1":
                redmiY1();
                break;
            case "redmi y1 lite":
                redmiY1Lite();
                break;
            case "redmi4a":
                redmi4a();
                break;
            case "redmi 4":
                redmi4Script();
                break;
            default:
                gernalScript(url, modelname);
        }
    }

    private void initScript(final String pageurl) {
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

                if (url.contains("https://www.amazon.in/gp/buy/thankyou/handlers/")) {
                    new Runnable() {
                        @Override
                        public void run() {

                            FiveStarsDialog fiveStarsDialog = new FiveStarsDialog(getApplicationContext(), "ssvmakers@gmail.com");
                            fiveStarsDialog.setRateText("Your custom text")
                                    .setTitle("Your custom title")
                                    .setForceMode(false)

                                    .setUpperBound(2) // Market opened if a rating >= 2 is selected
                                    .setNegativeReviewListener(new NegativeReviewListener() {
                                        @Override
                                        public void onNegativeReview(int i) {

                                        }
                                    }) // OVERRIDE mail intent for negative review
                                    .setReviewListener(new ReviewListener() {
                                        @Override
                                        public void onReview(int i) {

                                        }
                                    }) // Used to listen for reviews (if you want to track them )
                                    .showAfter(0);
                        }

                    };
                } else if (url.toLowerCase().contains("https://www.amazon.in/ap/signin")) {
                    //WebScriptActivity.this.myWebView.loadUrl("http://sriraman.in/app/complete_amazon_setup.html");
                    Snackbar.make(view, "Click Amazon Setup", 500).show();
                } else if (url.toLowerCase().contains("https://www.amazon.in/gp/your-account/order-history")) {
                    myWebView.loadUrl(pageurl);
                    Snackbar.make(view, "Logged In. Please wait till we check everything.", 500).show();
                } else if (url.toLowerCase().contains("//www.amazon.in/b")) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    myWebView.loadUrl("javascript: (function()%7Bvar el%3Ddocument.createElement(%27div%27),b%3Ddocument.getElementsByTagName(%27body%27)%5B0%5D%3Botherlib%3Dfalse,msg%3D%27%27%3Bel.style.position%3D%27fixed%27%3Bel.style.height%3D%2732px%27%3Bel.style.width%3D%27220px%27%3Bel.style.marginLeft%3D%27-110px%27%3Bel.style.top%3D%270%27%3Bel.style.left%3D%2750%25%27%3Bel.style.padding%3D%275px 10px%27%3Bel.style.zIndex%3D1001%3Bel.style.fontSize%3D%2712px%27%3Bel.style.color%3D%27%23222%27%3Bel.style.backgroundColor%3D%27%23f99%27%3Bif(typeof jQuery!%3D%27undefined%27)%7Bmsg%3D%27This page already using jQuery v%27%2BjQuery.fn.jquery%3Breturn showMsg()%3B%7Delse if(typeof %24%3D%3D%27function%27)%7Botherlib%3Dtrue%3B%7D function getScript(url,success)%7Bvar script%3Ddocument.createElement(%27script%27)%3Bscript.src%3Durl%3Bvar head%3Ddocument.getElementsByTagName(%27head%27)%5B0%5D,done%3Dfalse%3Bscript.onload%3Dscript.onreadystatechange%3Dfunction()%7Bif(!done%26%26(!this.readyState%7C%7Cthis.readyState%3D%3D%27loaded%27%7C%7Cthis.readyState%3D%3D%27complete%27))%7Bdone%3Dtrue%3Bsuccess()%3Bscript.onload%3Dscript.onreadystatechange%3Dnull%3Bhead.removeChild(script)%3B%7D%7D%3Bhead.appendChild(script)%3B%7D getScript(%27https://code.jquery.com/jquery-latest.min.js%27,function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bmsg%3D%27Sorry, but jQuery wasn%5C%27t able to load%27%3B%7Delse%7Bmsg%3D%27This page is now jQuerified with v%27%2BjQuery.fn.jquery%3Bif(otherlib)%7Bmsg%2B%3D%27 and noConflict(). Use %24jq(), not %24().%27%3B%7D%7D return showMsg()%3B%7D)%3Bfunction showMsg()%7Bel.innerHTML%3Dmsg%3Bb.appendChild(el)%3Bwindow.setTimeout(function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bb.removeChild(el)%3B%7Delse%7BjQuery(el).fadeOut(%27slow%27,function()%7BjQuery(this).remove()%3B%7D)%3Bif(otherlib)%7B%24jq%3DjQuery.noConflict()%3B%7D%7D%7D,2500)%3B%7D%7D)()%3B");
                                    myWebView.loadUrl("javascript:if(jQuery('h2').html()==\"Redmi 4\"){ jQuery('.bxc-grid__text p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    //myWebView.loadUrl("javascript:if(jQuery('h1').html()==\"Redmi 4A\"){ jQuery('.a-color-secondary p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    Snackbar.make(view, "Script is running...", -2).show();
                                    Log.d("Worling", "wrkin");
                                }
                            });
                        }
                    }).start();
                }
            }
        });
        this.myWebView.loadUrl("https://www.amazon.in/gp/your-account/order-history");
        Snackbar.make(myWebView, "Testing the setup. \nDon't Interact with screen now..", -2).show();
    }

    private void initUi() {
        root_view = findViewById(R.id.rootview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refresh = findViewById(R.id.refreshimageview);
        heading = findViewById(R.id.modelnametv);
        mobileimage = findViewById(R.id.mobileimage);
        myWebView = findViewById(R.id.webviewScript);
        spinner = findViewById(R.id.progressBar1);
        heading.setTypeface(FontManager.getReckoner(getApplicationContext()));
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                animation.setRepeatCount(2);
                refresh.startAnimation(animation);
                myWebView.reload();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.webactivtty_toolbarmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.copy:
                if (!myWebView.getUrl().equals(null)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("itemurl", myWebView.getUrl().toUpperCase());
                    clipboard.setPrimaryClip(clip);
                } else {
                    Toast.makeText(this, "Please wait until page is opening...", Toast.LENGTH_SHORT).show();
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

    private void refreshPage() {
        initScript(myWebView.getUrl().toString());
    }

    private void redmi4a() {
        if (Build.VERSION.SDK_INT >= 19) {
            WebView webView = this.myWebView;
            WebView.setWebContentsDebuggingEnabled(true);
        }

        this.myWebView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        this.myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(final WebView view, String url) {
                if (url.toLowerCase().contains("https://www.amazon.in/ap/signin")) {

                    Snackbar.make(view, "Login to amazon", -2).show();
                } else if (url.toLowerCase().contains("https://www.amazon.in/gp/your-account/order-history")) {
                    WebScriptActivity.this.myWebView.loadUrl("http://amzn.to/2jugYKl");
                    Snackbar.make(view, "You have loged in Please wait redirecting to " + modelName.toUpperCase() + " Sale Page", 10).show();
                } else if (url.toLowerCase().contains("//www.amazon.in/b")) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    WebScriptActivity.this.myWebView.loadUrl("javascript: (function()%7Bvar el%3Ddocument.createElement(%27div%27),b%3Ddocument.getElementsByTagName(%27body%27)%5B0%5D%3Botherlib%3Dfalse,msg%3D%27%27%3Bel.style.position%3D%27fixed%27%3Bel.style.height%3D%2732px%27%3Bel.style.width%3D%27220px%27%3Bel.style.marginLeft%3D%27-110px%27%3Bel.style.top%3D%270%27%3Bel.style.left%3D%2750%25%27%3Bel.style.padding%3D%275px 10px%27%3Bel.style.zIndex%3D1001%3Bel.style.fontSize%3D%2712px%27%3Bel.style.color%3D%27%23222%27%3Bel.style.backgroundColor%3D%27%23f99%27%3Bif(typeof jQuery!%3D%27undefined%27)%7Bmsg%3D%27This page already using jQuery v%27%2BjQuery.fn.jquery%3Breturn showMsg()%3B%7Delse if(typeof %24%3D%3D%27function%27)%7Botherlib%3Dtrue%3B%7D function getScript(url,success)%7Bvar script%3Ddocument.createElement(%27script%27)%3Bscript.src%3Durl%3Bvar head%3Ddocument.getElementsByTagName(%27head%27)%5B0%5D,done%3Dfalse%3Bscript.onload%3Dscript.onreadystatechange%3Dfunction()%7Bif(!done%26%26(!this.readyState%7C%7Cthis.readyState%3D%3D%27loaded%27%7C%7Cthis.readyState%3D%3D%27complete%27))%7Bdone%3Dtrue%3Bsuccess()%3Bscript.onload%3Dscript.onreadystatechange%3Dnull%3Bhead.removeChild(script)%3B%7D%7D%3Bhead.appendChild(script)%3B%7D getScript(%27https://code.jquery.com/jquery-latest.min.js%27,function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bmsg%3D%27Sorry, but jQuery wasn%5C%27t able to load%27%3B%7Delse%7Bmsg%3D%27This page is now jQuerified with v%27%2BjQuery.fn.jquery%3Bif(otherlib)%7Bmsg%2B%3D%27 and noConflict(). Use %24jq(), not %24().%27%3B%7D%7D return showMsg()%3B%7D)%3Bfunction showMsg()%7Bel.innerHTML%3Dmsg%3Bb.appendChild(el)%3Bwindow.setTimeout(function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bb.removeChild(el)%3B%7Delse%7BjQuery(el).fadeOut(%27slow%27,function()%7BjQuery(this).remove()%3B%7D)%3Bif(otherlib)%7B%24jq%3DjQuery.noConflict()%3B%7D%7D%7D,2500)%3B%7D%7D)()%3B");
                                    WebScriptActivity.this.myWebView.loadUrl("javascript:if(jQuery('h2').html()==\"Redmi 4A\"){ jQuery('.bxc-grid__text p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    Snackbar.make(view, "We are Clicking Please wait...", -2).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });
        this.myWebView.loadUrl("https://www.amazon.in/gp/your-account/order-history");
        Snackbar.make(root_view, "Testing the setup. \nDon't Interact with screen now..", -2).show();
    }

    private void redmiY1() {
        if (Build.VERSION.SDK_INT >= 19) {
            WebView webView = this.myWebView;
            WebView.setWebContentsDebuggingEnabled(true);
        }

        this.myWebView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        this.myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(final WebView view, String url) {
                spinner.setVisibility(View.INVISIBLE);

                if (url.toLowerCase().contains("https://www.amazon.in/ap/signin")) {
                    Snackbar.make(view, "Please Login...", -2).show();
                } else if (url.toLowerCase().contains("https://www.amazon.in/gp/your-account/order-history")) {
                    WebScriptActivity.this.myWebView.loadUrl("http://amzn.to/2jxhXJJ");
                    Snackbar.make(view, "You have loged in Please wait redirecting to " + modelName.toUpperCase() + " Sale Page", -2).show();
                } else if (url.toLowerCase().contains("//www.amazon.in/b")) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    WebScriptActivity.this.myWebView.loadUrl("javascript: (function()%7Bvar el%3Ddocument.createElement(%27div%27),b%3Ddocument.getElementsByTagName(%27body%27)%5B0%5D%3Botherlib%3Dfalse,msg%3D%27%27%3Bel.style.position%3D%27fixed%27%3Bel.style.height%3D%2732px%27%3Bel.style.width%3D%27220px%27%3Bel.style.marginLeft%3D%27-110px%27%3Bel.style.top%3D%270%27%3Bel.style.left%3D%2750%25%27%3Bel.style.padding%3D%275px 10px%27%3Bel.style.zIndex%3D1001%3Bel.style.fontSize%3D%2712px%27%3Bel.style.color%3D%27%23222%27%3Bel.style.backgroundColor%3D%27%23f99%27%3Bif(typeof jQuery!%3D%27undefined%27)%7Bmsg%3D%27This page already using jQuery v%27%2BjQuery.fn.jquery%3Breturn showMsg()%3B%7Delse if(typeof %24%3D%3D%27function%27)%7Botherlib%3Dtrue%3B%7D function getScript(url,success)%7Bvar script%3Ddocument.createElement(%27script%27)%3Bscript.src%3Durl%3Bvar head%3Ddocument.getElementsByTagName(%27head%27)%5B0%5D,done%3Dfalse%3Bscript.onload%3Dscript.onreadystatechange%3Dfunction()%7Bif(!done%26%26(!this.readyState%7C%7Cthis.readyState%3D%3D%27loaded%27%7C%7Cthis.readyState%3D%3D%27complete%27))%7Bdone%3Dtrue%3Bsuccess()%3Bscript.onload%3Dscript.onreadystatechange%3Dnull%3Bhead.removeChild(script)%3B%7D%7D%3Bhead.appendChild(script)%3B%7D getScript(%27https://code.jquery.com/jquery-latest.min.js%27,function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bmsg%3D%27Sorry, but jQuery wasn%5C%27t able to load%27%3B%7Delse%7Bmsg%3D%27This page is now jQuerified with v%27%2BjQuery.fn.jquery%3Bif(otherlib)%7Bmsg%2B%3D%27 and noConflict(). Use %24jq(), not %24().%27%3B%7D%7D return showMsg()%3B%7D)%3Bfunction showMsg()%7Bel.innerHTML%3Dmsg%3Bb.appendChild(el)%3Bwindow.setTimeout(function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bb.removeChild(el)%3B%7Delse%7BjQuery(el).fadeOut(%27slow%27,function()%7BjQuery(this).remove()%3B%7D)%3Bif(otherlib)%7B%24jq%3DjQuery.noConflict()%3B%7D%7D%7D,2500)%3B%7D%7D)()%3B");
                                    WebScriptActivity.this.myWebView.loadUrl("javascript:if(jQuery('h2').html()==\"Redmi Y1\"){ jQuery('.bxc-grid__text p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    Snackbar.make(view, "We are Clicking Please wait.../nThis is test script May work and may not be", -2).show();


                                }
                            });
                        }
                    }).start();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                spinner.setVisibility(View.VISIBLE);
            }
        });
        this.myWebView.loadUrl("https://www.amazon.in/gp/your-account/order-history");
        Snackbar.make(root_view, "Testing the setup. \nDon't Interact with screen now..", -2).show();
    }

    private void redmiY1Lite() {
        if (Build.VERSION.SDK_INT >= 19) {
            WebView webView = this.myWebView;
            WebView.setWebContentsDebuggingEnabled(true);
        }
        this.myWebView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        this.myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(final WebView view, String url) {
                spinner.setVisibility(View.INVISIBLE);
                if (url.toLowerCase().contains("https://www.amazon.in/ap/signin")) {

                    Snackbar.make(view, "Click Amazon Setup", -2).show();
                } else if (url.toLowerCase().contains("https://www.amazon.in/gp/your-account/order-history")) {
                    // WebScriptActivity.this.myWebView.loadUrl("http://amzn.to/2jxhXJJ");
                    WebScriptActivity.this.myWebView.loadUrl("http://amzn.to/2hE4Jds");
                    Snackbar.make(view, "Logged In. Please wait till we check everything.", -2).show();
                } else if (url.toLowerCase().contains("//www.amazon.in/b")) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    WebScriptActivity.this.myWebView.loadUrl("javascript: (function()%7Bvar el%3Ddocument.createElement(%27div%27),b%3Ddocument.getElementsByTagName(%27body%27)%5B0%5D%3Botherlib%3Dfalse,msg%3D%27%27%3Bel.style.position%3D%27fixed%27%3Bel.style.height%3D%2732px%27%3Bel.style.width%3D%27220px%27%3Bel.style.marginLeft%3D%27-110px%27%3Bel.style.top%3D%270%27%3Bel.style.left%3D%2750%25%27%3Bel.style.padding%3D%275px 10px%27%3Bel.style.zIndex%3D1001%3Bel.style.fontSize%3D%2712px%27%3Bel.style.color%3D%27%23222%27%3Bel.style.backgroundColor%3D%27%23f99%27%3Bif(typeof jQuery!%3D%27undefined%27)%7Bmsg%3D%27This page already using jQuery v%27%2BjQuery.fn.jquery%3Breturn showMsg()%3B%7Delse if(typeof %24%3D%3D%27function%27)%7Botherlib%3Dtrue%3B%7D function getScript(url,success)%7Bvar script%3Ddocument.createElement(%27script%27)%3Bscript.src%3Durl%3Bvar head%3Ddocument.getElementsByTagName(%27head%27)%5B0%5D,done%3Dfalse%3Bscript.onload%3Dscript.onreadystatechange%3Dfunction()%7Bif(!done%26%26(!this.readyState%7C%7Cthis.readyState%3D%3D%27loaded%27%7C%7Cthis.readyState%3D%3D%27complete%27))%7Bdone%3Dtrue%3Bsuccess()%3Bscript.onload%3Dscript.onreadystatechange%3Dnull%3Bhead.removeChild(script)%3B%7D%7D%3Bhead.appendChild(script)%3B%7D getScript(%27https://code.jquery.com/jquery-latest.min.js%27,function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bmsg%3D%27Sorry, but jQuery wasn%5C%27t able to load%27%3B%7Delse%7Bmsg%3D%27This page is now jQuerified with v%27%2BjQuery.fn.jquery%3Bif(otherlib)%7Bmsg%2B%3D%27 and noConflict(). Use %24jq(), not %24().%27%3B%7D%7D return showMsg()%3B%7D)%3Bfunction showMsg()%7Bel.innerHTML%3Dmsg%3Bb.appendChild(el)%3Bwindow.setTimeout(function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bb.removeChild(el)%3B%7Delse%7BjQuery(el).fadeOut(%27slow%27,function()%7BjQuery(this).remove()%3B%7D)%3Bif(otherlib)%7B%24jq%3DjQuery.noConflict()%3B%7D%7D%7D,2500)%3B%7D%7D)()%3B");
                                    WebScriptActivity.this.myWebView.loadUrl("javascript:if(jQuery('h2').html()==\"Redmi Y1 Lite\"){ jQuery('.bxc-grid__text p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    Snackbar.make(view, "Our Javascript is working in background", -2).show();
                                }
                            });
                        }
                    }).start();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                spinner.setVisibility(View.VISIBLE);
            }
        });
        this.myWebView.loadUrl("https://www.amazon.in/gp/your-account/order-history");
        Snackbar.make(root_view, "Testing the setup. \nDon't Interact with screen now..", -2).show();
    }

    private void gernalScript(final String saleUrl, final String text) {
        if (Build.VERSION.SDK_INT >= 19) {
            WebView webView = this.myWebView;
            WebView.setWebContentsDebuggingEnabled(true);
        }
        Log.d("url-model", saleUrl + " " + text);
        this.myWebView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        this.myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(final WebView view, String url) {
                spinner.setVisibility(View.INVISIBLE);
                if (url.toLowerCase().contains("https://www.amazon.in/ap/signin")) {

                    Snackbar.make(view, "Login to amazon", -2).show();
                } else if (url.toLowerCase().contains("https://www.amazon.in/gp/your-account/order-history")) {
                    WebScriptActivity.this.myWebView.loadUrl(saleUrl);
                    Snackbar.make(view, "You have loged in Please wait redirecting to " + modelName.toUpperCase() + " Sale Page", -2).show();
                } else if (url.toLowerCase().contains("//www.amazon.in/")) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    WebScriptActivity.this.myWebView.loadUrl("javascript: (function()%7Bvar el%3Ddocument.createElement(%27div%27),b%3Ddocument.getElementsByTagName(%27body%27)%5B0%5D%3Botherlib%3Dfalse,msg%3D%27%27%3Bel.style.position%3D%27fixed%27%3Bel.style.height%3D%2732px%27%3Bel.style.width%3D%27220px%27%3Bel.style.marginLeft%3D%27-110px%27%3Bel.style.top%3D%270%27%3Bel.style.left%3D%2750%25%27%3Bel.style.padding%3D%275px 10px%27%3Bel.style.zIndex%3D1001%3Bel.style.fontSize%3D%2712px%27%3Bel.style.color%3D%27%23222%27%3Bel.style.backgroundColor%3D%27%23f99%27%3Bif(typeof jQuery!%3D%27undefined%27)%7Bmsg%3D%27This page already using jQuery v%27%2BjQuery.fn.jquery%3Breturn showMsg()%3B%7Delse if(typeof %24%3D%3D%27function%27)%7Botherlib%3Dtrue%3B%7D function getScript(url,success)%7Bvar script%3Ddocument.createElement(%27script%27)%3Bscript.src%3Durl%3Bvar head%3Ddocument.getElementsByTagName(%27head%27)%5B0%5D,done%3Dfalse%3Bscript.onload%3Dscript.onreadystatechange%3Dfunction()%7Bif(!done%26%26(!this.readyState%7C%7Cthis.readyState%3D%3D%27loaded%27%7C%7Cthis.readyState%3D%3D%27complete%27))%7Bdone%3Dtrue%3Bsuccess()%3Bscript.onload%3Dscript.onreadystatechange%3Dnull%3Bhead.removeChild(script)%3B%7D%7D%3Bhead.appendChild(script)%3B%7D getScript(%27https://code.jquery.com/jquery-latest.min.js%27,function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bmsg%3D%27Sorry, but jQuery wasn%5C%27t able to load%27%3B%7Delse%7Bmsg%3D%27This page is now jQuerified with v%27%2BjQuery.fn.jquery%3Bif(otherlib)%7Bmsg%2B%3D%27 and noConflict(). Use %24jq(), not %24().%27%3B%7D%7D return showMsg()%3B%7D)%3Bfunction showMsg()%7Bel.innerHTML%3Dmsg%3Bb.appendChild(el)%3Bwindow.setTimeout(function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bb.removeChild(el)%3B%7Delse%7BjQuery(el).fadeOut(%27slow%27,function()%7BjQuery(this).remove()%3B%7D)%3Bif(otherlib)%7B%24jq%3DjQuery.noConflict()%3B%7D%7D%7D,2500)%3B%7D%7D)()%3B");
                                    WebScriptActivity.this.myWebView.loadUrl("javascript:if(jQuery('h2').html()==\"" + text + "\"){ jQuery('.bxc-grid__text p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    Snackbar.make(view, "We are Clicking Please wait...\nThis is test script May work and may not be", -2).show();
                                }
                            });
                        }
                    }).start();
                } else if (url.contains(thankyou)) {
                    showRateMeDialog();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                spinner.setVisibility(View.VISIBLE);
            }
        });
        this.myWebView.loadUrl("https://www.amazon.in/gp/your-account/order-history");
        Snackbar.make(root_view, "Testing the setup. \nDon't Interact with screen now..", -2).show();
    }

    private void redmi4Script() {
        this.myWebView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WebScriptActivity.this.spinner.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(final WebView view, String url) {
                WebScriptActivity.this.spinner.setVisibility(View.GONE);
                CookieSyncManager.getInstance().sync();
                if (url.toLowerCase().contains("https://www.amazon.in/ap/signin")) {
                    Snackbar.make(view, "Click Amazon Setup", 500).show();
                } else if (url.toLowerCase().contains("https://www.amazon.in/gp/your-account/order-history")) {
                    WebScriptActivity.this.myWebView.loadUrl("http://amzn.to/2j3kZVk");
                    Snackbar.make(view, "Logged In. Please wait till we check everything.", 500).show();
                } else if (url.toLowerCase().contains("//www.amazon.in/b")) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    WebScriptActivity.this.myWebView.loadUrl("javascript: (function()%7Bvar el%3Ddocument.createElement(%27div%27),b%3Ddocument.getElementsByTagName(%27body%27)%5B0%5D%3Botherlib%3Dfalse,msg%3D%27%27%3Bel.style.position%3D%27fixed%27%3Bel.style.height%3D%2732px%27%3Bel.style.width%3D%27220px%27%3Bel.style.marginLeft%3D%27-110px%27%3Bel.style.top%3D%270%27%3Bel.style.left%3D%2750%25%27%3Bel.style.padding%3D%275px 10px%27%3Bel.style.zIndex%3D1001%3Bel.style.fontSize%3D%2712px%27%3Bel.style.color%3D%27%23222%27%3Bel.style.backgroundColor%3D%27%23f99%27%3Bif(typeof jQuery!%3D%27undefined%27)%7Bmsg%3D%27This page already using jQuery v%27%2BjQuery.fn.jquery%3Breturn showMsg()%3B%7Delse if(typeof %24%3D%3D%27function%27)%7Botherlib%3Dtrue%3B%7D function getScript(url,success)%7Bvar script%3Ddocument.createElement(%27script%27)%3Bscript.src%3Durl%3Bvar head%3Ddocument.getElementsByTagName(%27head%27)%5B0%5D,done%3Dfalse%3Bscript.onload%3Dscript.onreadystatechange%3Dfunction()%7Bif(!done%26%26(!this.readyState%7C%7Cthis.readyState%3D%3D%27loaded%27%7C%7Cthis.readyState%3D%3D%27complete%27))%7Bdone%3Dtrue%3Bsuccess()%3Bscript.onload%3Dscript.onreadystatechange%3Dnull%3Bhead.removeChild(script)%3B%7D%7D%3Bhead.appendChild(script)%3B%7D getScript(%27https://code.jquery.com/jquery-latest.min.js%27,function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bmsg%3D%27Sorry, but jQuery wasn%5C%27t able to load%27%3B%7Delse%7Bmsg%3D%27This page is now jQuerified with v%27%2BjQuery.fn.jquery%3Bif(otherlib)%7Bmsg%2B%3D%27 and noConflict(). Use %24jq(), not %24().%27%3B%7D%7D return showMsg()%3B%7D)%3Bfunction showMsg()%7Bel.innerHTML%3Dmsg%3Bb.appendChild(el)%3Bwindow.setTimeout(function()%7Bif(typeof jQuery%3D%3D%27undefined%27)%7Bb.removeChild(el)%3B%7Delse%7BjQuery(el).fadeOut(%27slow%27,function()%7BjQuery(this).remove()%3B%7D)%3Bif(otherlib)%7B%24jq%3DjQuery.noConflict()%3B%7D%7D%7D,2500)%3B%7D%7D)()%3B");
                                    WebScriptActivity.this.myWebView.loadUrl("javascript:if(jQuery('h2').html()==\"Redmi 4\"){ jQuery('.bxc-grid__text p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    WebScriptActivity.this.myWebView.loadUrl("javascript:if(jQuery('h1').html()==\"Redmi 4A\"){ jQuery('.a-color-secondary p').html(\"Script is running in the background to add this phone to your cart. Please give us 5 star after ordering the phone.\"); setInterval(function() { jQuery('button').trigger('click'); console.log('Running Script... Please give us 5 star after ordering the phone.'); }, 10); }");
                                    Snackbar.make(view, "Script is running...", -2).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });
        this.myWebView.loadUrl("https://www.amazon.in/gp/your-account/order-history");
        Snackbar.make(root_view, "Testing the setup. \nDon't Interact with screen now..", -2).show();
    }

    private void showRateMeDialog() {
        FiveStarsDialog fiveStarsDialog = new FiveStarsDialog(this, "ssvmakers@gmail.com");
        fiveStarsDialog.setRateText("Order Placed successfully ! Give us 5 star ")
                .setTitle("Order Placed")
                .setForceMode(false)
                .setUpperBound(3) // Market opened if a rating >= 2 is selected
                .setNegativeReviewListener(new NegativeReviewListener() {
                    @Override
                    public void onNegativeReview(int i) {

                    }
                }) // OVERRIDE mail intent for negative review
                .setReviewListener(new ReviewListener() {
                    @Override
                    public void onReview(int i) {

                    }
                }).showAfter(0); // Used to listen for reviews (if you want to track them )

    }


}
