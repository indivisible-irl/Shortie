package com.indivisible.shortie.shorts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.util.Log;

public class GoogleShortener
        implements Shortener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private final static String GOOGLE_URL = "https://www.googleapis.com/urlshortener/v1/url";
    private final static String API_KEY = "AIzaSyAWCd8ONumLwxv0RqthcHXjcD1OlhXgrlE";
    private final static String KEY_APIKEY = "key";
    private final static String KEY_LONGURL = "longUrl";
    private final static String CONTENT_TYPE = "application/json";

    //private static final String SHORTENER_TITLE = "Google";
    private static final String TAG = "googleUrl";


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    public static String requestUrl(String longUrl)
    {
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = createPostRequest(longUrl);

            HttpResponse response = httpClient.execute(httpPost);
            return parseResponse(response);
        }
        catch (ClientProtocolException e)
        {
            Log.e(TAG, "Error with HTTP request protocol");
        }
        catch (IOException e)
        {
            Log.e(TAG, "Error with request IO");
        }

        return null;
    }

    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    //  request

    private static HttpPost createPostRequest(String longUrl)
    {
        String jsonRequest = createJsonRequestString(longUrl);
        if (jsonRequest != null)
        {
            try
            {
                StringEntity postEntity = new StringEntity(jsonRequest.toString());
                postEntity.setContentType(CONTENT_TYPE);
                HttpPost httpPost = new HttpPost();
                httpPost.setURI(new URI(GOOGLE_URL));
                httpPost.setEntity(postEntity);
                return httpPost;
            }
            catch (UnsupportedEncodingException e)
            {
                Log.e(TAG, "Error adding JSON String to Post.");
            }
            catch (URISyntaxException e)
            {
                Log.e(TAG, "Error encoding URI into HttpPost");
            }
        }
        return null;
    }

    private static String createJsonRequestString(String longUrl)
    {
        try
        {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put(KEY_APIKEY, API_KEY);
            jsonRequest.put(KEY_LONGURL, longUrl);
            return jsonRequest.toString();
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Error creating json request with url: " + longUrl);
        }
        return null;
    }

    //  response

    private static String parseResponse(HttpResponse response)
    {
        if (responseOk(response))
        {
            try
            {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(response
                        .getEntity().getContent(), "UTF-8"));
                for (String line = null; (line = br.readLine()) != null;)
                {
                    sb.append(line).append("\n");
                }
                JSONTokener tokener = new JSONTokener(sb.toString());
                JSONArray jsonResponse = new JSONArray(tokener);
                return jsonResponse.toString();
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    // can be super
    private static boolean responseOk(HttpResponse response)
    {
        int statusCode = response.getStatusLine().getStatusCode();
        Log.d(TAG, "HTTP response status code: " + statusCode);
        return (statusCode < 300 && statusCode >= 200);
    }
}
