package com.dh.spt;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * Extension of WebChromeClient class, used to bypass the geolocation permission
 * and also prompted by the webpage loaded by the WebView
 * 
 * @author pavan
 * 
 */
public class SPTWebChromeClient extends WebChromeClient {

	MainActivity mainActivity;
	private ValueCallback<Uri> mUploadMessage;
	private Uri mCapturedImageURI;

	public SPTWebChromeClient(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public void onGeolocationPermissionsShowPrompt(String origin,
			Callback callback) {
		// Overriding the default functionality
		super.onGeolocationPermissionsShowPrompt(origin, callback);
		callback.invoke(origin, true, false);
	}

	public void openFileChooser(ValueCallback<Uri> uploadMsg,
			String acceptType, String capture) {

		openFileChooser(uploadMsg, acceptType);
	}

	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
		// Update message
		mUploadMessage = uploadMsg;

		try {

			// Create SmartPoliceTown folder at sdcard

			File imageStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"SmartPoliceTown");

			if (!imageStorageDir.exists()) {
				// Create AndroidExampleFolder at sdcard
				imageStorageDir.mkdirs();
			}

			// Create camera captured image file path and name
			File file = new File(imageStorageDir + File.separator + "IMG_"
					+ String.valueOf(System.currentTimeMillis()) + ".jpg");

			mCapturedImageURI = Uri.fromFile(file);

			// Camera capture image intent
			final Intent captureIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

			captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			i.addCategory(Intent.CATEGORY_OPENABLE);
			i.setType("image/*");

			// Create file chooser intent
			Intent chooserIntent = Intent.createChooser(i, "Image Chooser");

			// Set camera intent to file chooser
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
					new Parcelable[] { captureIntent });

			// On select image call onActivityResult method of activity
			mainActivity.startActivityForResult(chooserIntent,
					MainActivity.FILECHOOSER_RESULTCODE);

		} catch (Exception e) {

		}
	}

	// openFileChooser for Android < 3.0
	public void openFileChooser(ValueCallback<Uri> uploadMsg) {
		openFileChooser(uploadMsg, "");
	}

	public ValueCallback<Uri> getUploadMessage() {
		return mUploadMessage;
	}

	public Uri getCapturedImageURI() {
		return mCapturedImageURI;
	}

}
