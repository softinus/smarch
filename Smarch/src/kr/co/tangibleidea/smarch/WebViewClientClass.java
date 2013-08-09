package kr.co.tangibleidea.smarch;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebViewClientClass extends WebViewClient { 
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    { 
        view.loadUrl(url); 
        return true; 
    } 
}