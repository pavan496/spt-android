package com.dh.spt;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Main Activity of the app. The only content of the activity is a webview in
 * which a web page will be loaded.
 * 
 * @author pavan
 * 
 */
public class MainActivity extends ActionBarActivity {

	public static final int FILECHOOSER_RESULTCODE = 0;
	private WebView mWebView;
	private SPTWebChromeClient sptWebChromeClient;
	private FrameLayout webViewPlaceholder;

	/**
	 * Method called to create and initialize the app
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove the title and set the xml layout
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initUI();
	}

	/**
	 * Activity initialize method. Creates a webview and adds attached required
	 */
	private void initUI() {

		// Retrieve UI elements
		webViewPlaceholder = ((FrameLayout) findViewById(R.id.webViewPlaceholder));

		if (mWebView == null) {
			// Get the web view in the layout
			mWebView = new WebView(this);
			mWebView.setLayoutParams(new ViewGroup.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			mWebView.getSettings().setSupportZoom(true);
			mWebView.getSettings().setBuiltInZoomControls(true);
			mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			mWebView.setScrollbarFadingEnabled(true);
			mWebView.getSettings().setLoadsImagesAutomatically(true);

			// Change the settings of the web view to enable javascript and
			// geolocation
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(
					true);
			mWebView.getSettings().setGeolocationEnabled(true);

			// Appending application name to User Agent. This will help in
			// detecting whether the application is opened through browser or
			// via App. This is not being used right now.
			String webViewUserAgent = mWebView.getSettings()
					.getUserAgentString();
			mWebView.getSettings().setUserAgentString(
					"SmartPoliceTown/" + webViewUserAgent);

			// Add a new client which handles the geolocation functionality.
			sptWebChromeClient = new SPTWebChromeClient(this);
			mWebView.setWebChromeClient(sptWebChromeClient);

			// Load the page into the web view
			mWebView.loadUrl("http://DHL-HYD-1008:8080/Smart_Police_Town");

			// In case any links are clicked in the app, setting the webview
			// client
			// will make sure that all the links open in the webview itself.
			mWebView.setWebViewClient(new WebViewClient());
		}
		webViewPlaceholder.addView(mWebView);
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

	/**
	 * This method handles the response of the the file chooser event triggered
	 * for file upload
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == sptWebChromeClient.getUploadMessage()) {
				return;
			}

			Uri result = null;
			try {
				if (resultCode != RESULT_OK) {
					result = null;
				} else {
					// retrieve from the private variable if the intent is null
					result = intent == null ? sptWebChromeClient
							.getCapturedImageURI() : intent.getData();
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "activity :" + e,
						Toast.LENGTH_LONG).show();
			}

			sptWebChromeClient.getUploadMessage().onReceiveValue(result);
		}
	}

	/**
	 * Handles the configuration changes mentioned in Android Manifest.
	 * Orientation change is handled here.
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (mWebView != null) {
			// Remove the WebView from the old placeholder
			webViewPlaceholder.removeView(mWebView);
		}
		super.onConfigurationChanged(newConfig);

		// Load the layout resource for the new configuration
		setContentView(R.layout.activity_main);

		// Reinitialize the UI
		initUI();
	}

	/**
	 * Overriding default save instance state to save instance state of webview
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the state of the WebView
		mWebView.saveState(outState);
	}

	/**
	 * Overriding default restore instance state to restore the saved instance
	 * state of webview
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// Restore the state of the WebView
		mWebView.restoreState(savedInstanceState);
	}
}
