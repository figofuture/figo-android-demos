package com.figo.testcrc32md5sha1;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import com.figo.common.utils.EncryptUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	String LOG_TAG = MainActivity.class.getSimpleName();
	PackageManager mPM = null;
	Button mBegin = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		
		setProgressBarIndeterminateVisibility(false);
		mBegin = (Button) findViewById(R.id.begin);
		mBegin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new ExportTask().execute(new Long(0));
			}
		});
		
		mPM = this.getPackageManager();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	class ExportTask extends AsyncTask<Long, Integer, Long> {

		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
			mBegin.setEnabled(false);
		}

		@Override
		protected Long doInBackground(Long... params) {
			List<PackageInfo> list = mPM
					.getInstalledPackages(PackageManager.GET_SIGNATURES);
			for (PackageInfo pif : list) {
				//Log.i(LOG_TAG, pif.packageName);
				Signature[] sigs = pif.signatures;
				String[] signatures = new String[sigs.length];
				if (sigs.length > 0) {
					for (int i = 0; i < signatures.length; i++) {
						signatures[i] = sigs[i].toCharsString();
					}

					Arrays.sort(signatures);
					StringBuilder joinedSignature = new StringBuilder();
					for (int i = 0; i < signatures.length; i++) {
						joinedSignature.append(signatures[i]);
					}
					//Log.i(LOG_TAG, "Joined apk signature: " + joinedSignature);

					try {
						String result = null;
						
						Thread.sleep(1);
						
						long from = System.currentTimeMillis();
						result = EncryptUtil.getAdler32(joinedSignature.toString());
						long to = System.currentTimeMillis();
						long delta = to - from;
						Log.i( LOG_TAG, pif.packageName + " Adler32 time: " + (delta) + "ms" +  " String: " + result);
						
						from = System.currentTimeMillis();
						result = EncryptUtil.getCRC32(joinedSignature.toString());
						to = System.currentTimeMillis();
						delta = to - from;
						Log.i( LOG_TAG, pif.packageName + " CRC32   time: " + delta + "ms" +  " String: " + result );
						
						from = System.currentTimeMillis();
						result = EncryptUtil.getMD5(joinedSignature.toString());
						to = System.currentTimeMillis();
						delta = to - from;
						Log.i( LOG_TAG, pif.packageName + " MD5     time: " + delta + "ms" +  " String: " + result );
						
						from = System.currentTimeMillis();
						result = EncryptUtil.getSHA1(joinedSignature.toString());
						to = System.currentTimeMillis();
						delta = to - from;
						Log.i( LOG_TAG, pif.packageName + " SHA1    time: " + delta + "ms" +  " String: " + result );
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		protected void onPostExecute(Long result) {
			setProgressBarIndeterminateVisibility(false);
			mBegin.setEnabled(true);
		}
	}
}
