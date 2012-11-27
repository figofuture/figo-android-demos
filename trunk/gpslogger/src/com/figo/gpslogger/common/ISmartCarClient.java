package com.figo.gpslogger.common;

import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;

public class ISmartCarClient {

	public void Upload(String path, Map<?, ?> params, String proxyName,
			int proxyPort) {
		if (null != path) {
			Thread t = new Thread(new ISmartCarUploadHandler(path, params,
					proxyName, proxyPort));
			t.start();
		}
	}

	private class ISmartCarUploadHandler implements Runnable {

		String path;
		Map<?, ?> params;
		String proxyName;
		int proxyPort;

		public ISmartCarUploadHandler(String path, Map<?, ?> params,
				String proxyName, int proxyPort) {
			this.path = path;
			this.params = params;
			this.proxyName = proxyName;
			this.proxyPort = proxyPort;
		}

		@Override
		public void run() {
			try {
				makeRequest(path, params, proxyName, proxyPort);
			} catch (Exception e) {
				Utilities.LogError(path, e);
			}
		}

	}

	public void makeRequest(String path, Map<?, ?> params, String proxyName,
			int proxyPort) throws Exception {
		// instantiates httpclient to make request
		DefaultHttpClient httpclient = new DefaultHttpClient();

		if (null != proxyName) {
			HttpHost proxyHost;
			proxyHost = new HttpHost(proxyName, proxyPort);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxyHost);
		}

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
		String httpResponse = httpclient.execute(httpost, responseHandler);
		if (null != httpResponse) {
			JSONObject result = new JSONObject(httpResponse);
			String returnCode = (String) result.get("ReturnCode");
			if ("200".equalsIgnoreCase(returnCode)) {
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
	
	public void makeAsyncRequest(String path, Map<?, ?> params, String proxyName,
			int proxyPort) throws Exception {
		// instantiates httpclient to make request
		AsyncHttpClient httpclient = new AsyncHttpClient();

		if (null != proxyName) {
			HttpHost proxyHost;
			proxyHost = new HttpHost(proxyName, proxyPort);
			httpclient.getHttpClient().getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxyHost);
		}

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
		String httpResponse = httpclient.getHttpClient().execute(httpost, responseHandler);
		if (null != httpResponse) {
			JSONObject result = new JSONObject(httpResponse);
			String returnCode = (String) result.get("ReturnCode");
			if ("200".equalsIgnoreCase(returnCode)) {
				Utilities.LogVerbose("Upload successfully!");
			} else {
				Utilities.LogInfo(returnCode);
			}
		}
	}
}
