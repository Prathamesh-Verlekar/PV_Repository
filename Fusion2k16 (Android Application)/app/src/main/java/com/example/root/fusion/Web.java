package com.example.root.fusion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class Web extends AppCompatActivity {
 private WebView we;
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        we = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = we.getSettings();
        webSettings.setJavaScriptEnabled(true);
        we.setWebChromeClient(new MyWebViewClient());
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);
        we.loadUrl("https://nirmalsaini.typeform.com/to/FiWIdw");
        Web.this.progress.setProgress(0);
    }

    class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Web.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

}


