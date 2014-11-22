package com.dh.spt;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Main Activity of the app. The only content of the activity is a webview in
 * which a web page will be loaded.
 * 
 * @author pavan
 * 
 */
public class MainActivity extends ActionBarActivity {

	private WebView mWebView;

	/**
	 * Method called to create and initialize the app
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove the title and set the xml layout
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// Get the web view in the layout
		mWebView = (WebView) findViewById(R.id.activity_main_webview);

		// Change the settings of the web view to enable javascript and
		// geolocation
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setGeolocationEnabled(true);

		// Add a new client which handles the geolocation functionality.
		GeoClient geo = new GeoClient();
		mWebView.setWebChromeClient(geo);

		// Load the page into the web view
		mWebView.loadUrl("http://DHL-HYD-1008:8080/Smart_Police_Town");

		// In case any links are clicked in the app, setting the webview client
		// will make sure that all the links open in the webview itself.
		mWebView.setWebViewClient(new WebViewClient());
	}

	/**
	 * On back press event. This overridden method invokes the back
	 * functionality of the website in the webview instead of the app
	 */
	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack())
			mWebView.goBack();
		else
			super.onBackPressed();
	}

}
