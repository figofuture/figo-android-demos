package com.figo.jsinjection;

import com.figo.jsinjection.util.Reader;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	public class JSNotify {

	}

	private static final String LOG_TAG = "jsinjection";
	private WebView webView = null;
	String loginScripts = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        
        webView = new WebView(this);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				setProgress(progress * 100);
			}
		});

		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		// enable javascript
		webSettings.setJavaScriptEnabled(true);
		// emulate IE
//		webSettings
//				.setUserAgentString("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ENUS)");
		webSettings.setAppCacheEnabled(false);
		webSettings.setDatabaseEnabled(false);
		// disable saving prompt dialog
		webSettings.setSaveFormData(false);
		webSettings.setSavePassword(false);
		
		webView.setWebViewClient(new JSLIBWebClient());
        
        initJSLib();
        
        // install callback function
        webView.addJavascriptInterface(new JSNotify(), "external");
        
		setContentView(webView);
		webView.loadUrl("http://opac.jslib.org.cn/F/?func=login-session&bor_library=NJL50&login_source=bor-info&bor_id=&bor_verification=");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    class JSLIBWebClient extends WebViewClient {
    	
    	public void onReceivedError (WebView view, int errorCode, String description, String failingUrl) {
    		Log.e(LOG_TAG, "ErrorCode: " + errorCode + "  Description: " + description + "  Url: " + failingUrl);
    		super.onReceivedError(view, errorCode, description, failingUrl);
    		
    	}
    	
    	public void onPageStarted (WebView view, String url, Bitmap favicon) {
    		Log.i(LOG_TAG, "Load started: " + url);
    		super.onPageStarted(view, url, favicon);
    	}
    	
    	public void onPageFinished (WebView view, String url) {
    		Log.i(LOG_TAG, "Load finished: " + url);
    		if ( "http://opac.jslib.org.cn/F/?func=login-session&bor_library=NJL50&login_source=bor-info&bor_id=&bor_verification=".equals(url) ) {
    			login("nlxxxxxxxx","xxxxxx");
    		}
    	}
    };
    
    private void initJSLib() {
    	loginScripts = getScript(R.raw.jquery182min) + "  " + getScript(R.raw.login);
    }
    
    private void login ( String username, String password ) {
    	webView.loadUrl("javascript:" + loginScripts);
    	webView.loadUrl("javascript:" + "login(" + "\'" + username + "\'," + "\'" + password + "\'" + ");");
    }
    
    /** 
     * Reads a script form a javascript file located in /res/raw/ 
     * and retuns it as a String.
     */
    public String getScript(int resourceId)
    {
        return Reader.readRawString(getResources(), resourceId);
    }
    
}
