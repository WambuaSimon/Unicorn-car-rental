package com.wizag.unicorn.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.wizag.unicorn.R;
import im.delight.android.webview.AdvancedWebView;

public class Activity_PayWebview extends AppCompatActivity implements AdvancedWebView.Listener{
    private AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_webview);

        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.loadUrl("https://unicorn.wizag.co.ke/make-payment");

    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }
}
