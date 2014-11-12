package com.splayer.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.splayer.util.SLog;

import java.text.DecimalFormat;
import java.util.ArrayList;

import tour.android.com.myapplication.R;

public class MainActivity extends Activity implements SlidingPaneLayout.PanelSlideListener, View.OnTouchListener {
    private SlidingPaneLayout mSlideLayout;
    private WebView webView;
    private ProgressBar progressBar;
    private int slideToucheAction;
    private ArrayList<Website> data = new ArrayList<Website>();
    private boolean openByCall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideLayout = (SlidingPaneLayout) findViewById(R.id.slide);
        webView = (WebView) findViewById(R.id.web);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                SLog.i("new progress:" + newProgress);
                progressBar.setProgress(newProgress);
            }
        });
        mSlideLayout.setPanelSlideListener(this);
        mSlideLayout.setOnTouchListener(this);
        mSlideLayout.setParallaxDistance((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        data.add(new Website("http://www.baidu.com", "百度"));
        data.add(new Website("http://www.qq.com", "腾讯"));
        data.add(new Website("http://www.163.com", "网易"));

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(new MyAdapter());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                close();
                webView.loadUrl(data.get(position).url);
            }
        });
    }

    @Override
    public void onPanelSlide(View view, float v) {

    }

    public void close() {
        openByCall = true;
        mSlideLayout.closePane();
    }

    public void open() {
        openByCall = true;
        mSlideLayout.openPane();
    }

    @Override
    public void onPanelOpened(View view) {
        openByCall = false;
    }

    @Override
    public void onPanelClosed(View view) {
        openByCall = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        slideToucheAction = event.getAction();
        return false;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Website getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1, null);
            }
            ((TextView) convertView).setText(getItem(position).name);
            return convertView;
        }
    }

    class Website {
        Website(String url, String name) {
            this.url = url;
            this.name = name;
        }

        String url;
        String name;
    }

}
