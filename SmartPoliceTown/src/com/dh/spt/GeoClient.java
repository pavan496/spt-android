package com.dh.spt;

import android.webkit.WebChromeClient;
import android.webkit.GeolocationPermissions.Callback;

public class GeoClient extends WebChromeClient {

	
	
	@Override
	public void onGeolocationPermissionsShowPrompt(String origin,
			Callback callback) {
		// TODO Auto-generated method stub
		super.onGeolocationPermissionsShowPrompt(origin, callback);
		callback.invoke(origin, true, false);
	}

}
