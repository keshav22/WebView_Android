package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    WebView mywebview;

    SwipeRefreshLayout pullToRefresh;
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()) {
            setContentView(R.layout.nointernet);
            pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    finish();
                    startActivity(getIntent());
                }
            });

        } else {
            setContentView(R.layout.activity_main);

            mywebview = (WebView) findViewById(R.id.webview);
            mywebview.loadUrl("http://www.beginnertopro.in/");
            mywebview.getSettings().setJavaScriptEnabled(true);
            mywebview.setWebChromeClient(new WebChromeClient());

            mywebview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString());

                    return true;
                }

                @Override
                public void onPageFinished(WebView webView, String url) {
                    Log.d("test", "onPageFinished");
                    if (url.compareTo("http://www.beginnertopro.in/redirecter.php") == 0) {
                        //Toast.makeText(getApplicationContext(),"Enter",Toast.LENGTH_SHORT).show();
                        //webView.clearCache(true);
                        webView.loadUrl("http://www.beginnertopro.in/");
                    }
                    if (url.compareTo("http://www.beginnertopro.in/") == 0) {
                        webView.clearHistory();
                    }
                }
            });
            mywebview.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    // Do something cool here
                    //    Toast.makeText(getApplicationContext(),String.valueOf(progress),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mywebview.canGoBack()) {
                        mywebview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    public void onBackPressed() {
//
//        if(stack.peek().compareTo("")==0)
//        {
//            super.onBackPressed();
//
//        }
//        else
//        {
//            WebView mywebview = (WebView) findViewById(R.id.webview);
//            mywebview.loadUrl(stack.pop());
//        }
//
//    }
}
