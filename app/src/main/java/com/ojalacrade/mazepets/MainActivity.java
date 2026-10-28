package com.ojalacrade.mazepets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

public class MainActivity extends Activity {

    // Aapka AdMob Banner Unit ID
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3775789465941687/4401977068";
    private AdView mAdView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set maximum ad content rating to G (Family friendly)
        RequestConfiguration requestConfiguration =
                new RequestConfiguration.Builder()
                        .setMaxAdContentRating(
                                RequestConfiguration.MAX_AD_CONTENT_RATING_G
                        )
                        .build();

        MobileAds.setRequestConfiguration(requestConfiguration);

        // Initialize AdMob Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Main vertical layout
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        // Game WebView (Upar game display hoga)
        WebView webView = new WebView(this);
        webView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/maze-pets.html");

        // Banner Ad (Niche Ad banner show hoga)
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(BANNER_AD_UNIT_ID);

        mAdView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Layout me views add karein (Pehle WebView, phir Niche AdView)
        root.addView(webView);
        root.addView(mAdView);

        setContentView(root);
    }

    // Ad lifecycle methods memory leaks bachane aur ads ko properly manage karne ke liye
    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
