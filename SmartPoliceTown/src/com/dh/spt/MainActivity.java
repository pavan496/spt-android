package com.dh.spt;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends ActionBarActivity {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mWebView = (WebView) findViewById(R.id.activity_main_webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setGeolocationEnabled(true);

		GeoClient geo = new GeoClient();
		mWebView.setWebChromeClient(geo);

		mWebView.loadUrl("http://115.119.152.134:82/SmartPoliceTown");
		mWebView.setWebViewClient(new WebViewClient());
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack())
			mWebView.goBack();
		else
			super.onBackPressed();
	}

}
