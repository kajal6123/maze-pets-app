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

    private static final String BANNER_AD_UNIT_ID =
            "ca-app-pub-3775789465941687/4401977068";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set maximum ad content rating to G
        RequestConfiguration requestConfiguration =
                new RequestConfiguration.Builder()
                        .setMaxAdContentRating(
                                RequestConfiguration.MAX_AD_CONTENT_RATING_G
                        )
                        .build();

        MobileAds.setRequestConfiguration(requestConfiguration);

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});

        // Main vertical layout
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        // Game WebView
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

        // Banner Ad
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BANNER_AD_UNIT_ID);

        adView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Add game first, then banner at bottom
        root.addView(webView);
        root.addView(adView);

        setContentView(root);
    }
}
