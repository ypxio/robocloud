package com.robocloud.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

/**
 * This class is used for sending data to the server
 *
 * @author Robertus Lilik Haryanto
 * @version 1.0
 */
public class HttpRequestClass {

	private HttpResponse response = null;

	private String url = null;
	private Map<String, String> params = new HashMap<String, String>();
	private Method requestMethod = null;

	enum Method {
		GET, POST
	}

	protected HttpRequestClass(String url, Map<String, String> params, Method requestMethod) {
		this.url = url;
		this.params = params;
		this.requestMethod = requestMethod;
		
		
	}

	public String sendRequest() {

		Log.d("HttpRequest@sendRequest", "Starting request...");

		Log.d("HttpRequest@sendRequest", "URL: " + this.url + ", requestMethod: "
				+ this.requestMethod);

		// Create a new HttpClient and Post Header
		HttpClient httpClient = new DefaultHttpClient();
		HttpRequestBase httpRequest = null;

		InputStream responseStream = null;
		String responseString = null;

		try {
			if (this.requestMethod == Method.POST) {
				httpRequest = new HttpPost(this.url);

				if (httpRequest != null) {
					((HttpPost) httpRequest).setEntity(new UrlEncodedFormEntity(
							encodePostParameter(this.params)));
					// Execute HTTP Post Request
					response = httpClient.execute(httpRequest);
				}

			} else if (this.requestMethod == Method.GET) {
				httpRequest = new HttpGet(this.url + encodeGetParameter(this.params));

				if (httpRequest != null) {
					// Execute HTTP Post Request
					response = httpClient.execute(httpRequest);
				}
			}

			if (response != null) {
				responseStream = response.getEntity().getContent();
				responseString = generateResponseString(responseStream);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.d("HttpRequest@sendRequest", "Finished!");

		return responseString;
	}

	public List<BasicNameValuePair> encodePostParameter(Map<String, String> params) {
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(1);
		Set<String> keys = params.keySet();
		for (String key : keys) {
			String value = params.get(key);
			nameValuePairs.add(new BasicNameValuePair(key, value));
		}
		return nameValuePairs;
	}

	public String encodeGetParameter(Map<String, String> params) {
		StringBuilder buff = new StringBuilder("?");
		Set<String> keys = params.keySet();
		for (String key : keys) {
			String value = params.get(key);
			buff.append(key).append("=").append(URLEncoder.encode(value));
		}
		return buff.toString();
	}

	public String generateResponseString(InputStream stream) {
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader buffer = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();

		try {
			String cur;
			while ((cur = buffer.readLine()) != null) {
				sb.append(cur + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
