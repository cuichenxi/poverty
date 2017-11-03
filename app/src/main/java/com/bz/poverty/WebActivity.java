package com.bz.poverty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.framework.activity.BaseActivity;
import com.framework.activity.BaseFragment;

/**
 * Created by chenxi.cui on 2017/9/30.
 */
public class WebActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragemtn_web);
        String url = myBundle.getString("url");
        final String title = myBundle.getString("title");
        titleBarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null && view.getId() == R.id.title_left_btn) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                       finish();
                    }
                }
            }
        };
        setTitleBar(title, true);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                setTitleBar(title, true);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl(url);
    }
}
