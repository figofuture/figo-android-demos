package com.figofuture.maskhomekey;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

	private final static String LOG_TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		Button exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}});
		Button dialog = (Button) findViewById(R.id.dialog);
		dialog.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(MainActivity.this);
				//dialog.setContentView(R.layout.mydailog);
				//dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
				dialog.show();
				dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);

				dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener(){
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
						switch (keyCode) {
						case KeyEvent.KEYCODE_HOME:
							Log.i(LOG_TAG, "KEYCODE_HOME");
							return true;
							// mask back
						case KeyEvent.KEYCODE_BACK:
							Log.i(LOG_TAG, "KEYCODE_BACK");
							return true;
						case KeyEvent.KEYCODE_CALL:
							Log.i(LOG_TAG, "KEYCODE_CALL");
							return true;
						case KeyEvent.KEYCODE_SYM:
							Log.i(LOG_TAG, "KEYCODE_SYM");
							return true;
							// mask volume
						case KeyEvent.KEYCODE_VOLUME_DOWN:
							Log.i(LOG_TAG, "KEYCODE_VOLUME_DOWN");
							return true;
						case KeyEvent.KEYCODE_VOLUME_UP:
							Log.i(LOG_TAG, "KEYCODE_VOLUME_UP");
							return true;
						case KeyEvent.KEYCODE_STAR:
							Log.i(LOG_TAG, "KEYCODE_STAR");
							return true;
							// mask camera
						case KeyEvent.KEYCODE_CAMERA:
							Log.i(LOG_TAG, "KEYCODE_CAMERA");
							return true;
						case KeyEvent.KEYCODE_FOCUS:
							Log.i(LOG_TAG, "KEYCODE_FOCUS");
							return true;
							// mask search
						case KeyEvent.KEYCODE_SEARCH:
							Log.i(LOG_TAG, "KEYCODE_SEARCH");
							return true;
						}
						return false;
					}
				}); 
			}});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(LOG_TAG, "keycode=" + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			Log.i(LOG_TAG, "KEYCODE_HOME");
			return true;
			// mask back
		case KeyEvent.KEYCODE_BACK:
			Log.i(LOG_TAG, "KEYCODE_BACK");
			return true;
		case KeyEvent.KEYCODE_CALL:
			Log.i(LOG_TAG, "KEYCODE_CALL");
			return true;
		case KeyEvent.KEYCODE_SYM:
			Log.i(LOG_TAG, "KEYCODE_SYM");
			return true;
			// mask volume
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			Log.i(LOG_TAG, "KEYCODE_VOLUME_DOWN");
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			Log.i(LOG_TAG, "KEYCODE_VOLUME_UP");
			return true;
		case KeyEvent.KEYCODE_STAR:
			Log.i(LOG_TAG, "KEYCODE_STAR");
			return true;
			// mask camera
		case KeyEvent.KEYCODE_CAMERA:
			Log.i(LOG_TAG, "KEYCODE_CAMERA");
			return true;
		case KeyEvent.KEYCODE_FOCUS:
			Log.i(LOG_TAG, "KEYCODE_FOCUS");
			return true;
			// mask search
		case KeyEvent.KEYCODE_SEARCH:
			Log.i(LOG_TAG, "KEYCODE_SEARCH");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onAttachedToWindow() {
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}
}
