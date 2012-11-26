package com.figo.gpslogger.common;

import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ISmartCarClient {

	public void Upload( String path, Map<?,?> params ) {
		Thread t = new Thread(new ISmartCarUploadHandler(path, params));
		t.start();
	}
	
	private class ISmartCarUploadHandler implements Runnable {

		String mPath;
		Map<?,?> mParams;
		
		public ISmartCarUploadHandler( String path, Map<?,?> params ) {
			mPath = path;
			mParams = params;
		}
		
		@Override
		public void run() {
			try {
				makeRequest(mPath,mParams);
			} catch (Exception e) {
				Utilities.LogError(mPath, e);
			}
		}
		
	}
	
	public void makeRequest(String path, Map<?,?> params)
			throws Exception {
		// instantiates httpclient to make request
		DefaultHttpClient httpclient = new DefaultHttpClient();

		// url with the post data
		HttpPost httpost = new HttpPost(path);

		// convert parameters into JSON object
		JSONObject holder = getJsonObjectFromMap(params);

		// passes the results to a string builder/entity
		StringEntity se = new StringEntity(holder.toString());

		// sets the post request as the resulting string
		httpost.setEntity(se);

		// sets a request header so the page receiving the request
		// will know what to do with it
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");

		// Handles what is returned from the page
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String  httpResponse = httpclient.execute(httpost, responseHandler);
		if ( null != httpResponse ) {
			JSONObject result = new JSONObject( httpResponse);
			String returnCode = (String) result.get("ReturnCode");
			if ( "200".equalsIgnoreCase(returnCode) ) {
				Utilities.LogVerbose("Upload successfully!");
			} else {
				Utilities.LogInfo(returnCode);
			}
		}
	}

	private JSONObject getJsonObjectFromMap(Map<?, ?> params)
			throws JSONException {

		// all the passed parameters from the post request
		// iterator used to loop through all the parameters
		// passed in the post request
		Iterator<?> iter = params.entrySet().iterator();

		// Stores JSON
		JSONObject holder = new JSONObject();

		// While there is another entry
		while (iter.hasNext()) {
			// gets an entry in the params
			Map.Entry<?, ?> pairs = (Map.Entry<?, ?>) iter.next();

			// creates a key for Map
			String key = (String) pairs.getKey();

			// Create a new map
			String data = (String) pairs.getValue();

			holder.put(key, data);
		}
		return holder;
	}
}
