package com.indivisible.shortie;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class GetUrl {
	private final URL googleUrl = new URL("https://www.googleapis.com/urlshortener/v1/url");
	private JSONObject request = new JSONObject();
	private final String authKey = "AIzaSyAWCd8ONumLwxv0RqthcHXjcD1OlhXgrlE";	
	
	public GetUrl(String longUrl){
		try {
			request.put("longUrl", longUrl);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private String shortenUrl(){
		HttpURLConnection httpConn = new HttpURLConnection(GoogleUrl);
		
		return null;
	}
	
}
