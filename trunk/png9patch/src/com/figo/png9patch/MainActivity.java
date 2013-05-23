package com.figo.png9patch;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView iv_src_9patch = (ImageView) findViewById(R.id.image_src_asset_9patch);
		Drawable bg;
		try {
			bg = NinePatchTool.decodeDrawableFromAsset(this, "abs__toast_frame.9.png");
			iv_src_9patch.setImageDrawable(bg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageView iv_background_9patch = (ImageView) findViewById(R.id.image_background_asset_9patch);
		try {
			bg = NinePatchTool.decodeDrawableFromAsset(this, "abs__toast_frame.9.png");
			iv_background_9patch.setBackgroundDrawable(bg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageView iv_background = (ImageView) findViewById(R.id.image_background_asset);
		try {
			BitmapDrawable bgb = (BitmapDrawable) NinePatchTool.decodeDrawableFromAsset(this, "ic_launcher.png");
			iv_background.setBackgroundDrawable(bgb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
