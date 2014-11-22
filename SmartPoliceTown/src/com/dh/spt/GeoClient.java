package com.dh.spt;

import android.webkit.WebChromeClient;
import android.webkit.GeolocationPermissions.Callback;

/**
 * Extension of WebChromeClient class, used to bypass the geolocation permission
 * prompted by the webpage loaded by the WebView
 * 
 * @author pavan
 * 
 */
public class GeoClient extends WebChromeClient {

	@Override
	public void onGeolocationPermissionsShowPrompt(String origin,
			Callback callback) {
		// Overriding the default functionality
		super.onGeolocationPermissionsShowPrompt(origin, callback);
		callback.invoke(origin, true, false);
	}

}
