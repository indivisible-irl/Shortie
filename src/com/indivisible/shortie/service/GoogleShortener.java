package com.indivisible.shortie.service;

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
import android.util.Log;
import com.indivisible.shortie.data.LinkPair;

public class GoogleShortener
        implements Shortener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private final static String GOOGLE_URL = "https://www.googleapis.com/urlshortener/v1/url";
    //private final static String API_KEY = "AIzaSyAWCd8ONumLwxv0RqthcHXjcD1OlhXgrlE";
    private final static String API_KEY = "AIzaSyAWCd8ONumLwxv0RqthcHXjcD1OlhXgrlEaaaaaaaaaaaaaaaaa";

    private final static String CONTENT_TYPE = "application/json";
    private final static String KEY_APIKEY = "key";
    //private final static String KEY_KIND = "kind";
    private final static String KEY_SHORTURL = "id";
    private final static String KEY_LONGURL = "longUrl";

    private final static String KEY_ERROR_ROOT = "error";
    private final static String KEY_ERRORS_ARRAY = "errors";
    private final static String KEY_ERR_REASON = "reason";
    private final static String ERR_REASON_INVALID = "invalid";
    private final static String KEY_ERR_LOCATION = "location";
    private final static String ERR_LOC_LONGURL = "resource.longUrl";


    private static final String TITLE = "Google";
    private static final String TAG = "sho:GoogleShort";


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    /**
     * Get this service's identifying name
     * 
     * @return
     */
    public static String getTitle()
    {
        return GoogleShortener.TITLE;
    }

    @Override
    public LinkPair shortenUrl(LinkPair linkPair)
    {
        Log.d(TAG, "Start GoogleShortener...");
        HttpResponse response = null;
        JSONObject jsonResponse = null;
        String shortUrl = null;

        response = request(linkPair);
        if (response != null)
        {
            Log.d(TAG, "received response");
            jsonResponse = getJsonResponse(linkPair, response);
        }
        if (jsonResponse != null)
        {
            if (getResponseStatusCode(response) != 200)
            {
                Log.e(TAG, "Response code not 200: " + getResponseStatusCode(response));
                parseForError(linkPair, jsonResponse);
                return linkPair;
            }
            Log.d(TAG, "extracted JSON");
            shortUrl = extractJSONShortUrl(linkPair, jsonResponse);
        }
        if (shortUrl != null)
        {
            Log.d(TAG, "extracted shortUrl");
            linkPair.setShortUrl(shortUrl);
            linkPair.setStatus(ResponseStatus.OK);
        }
        return linkPair;
    }

    ///////////////////////////////////////////////////////
    ////    request & response
    ///////////////////////////////////////////////////////

    private HttpResponse request(LinkPair linkPair)
    {
        linkPair.setStatus(ResponseStatus.PRE_REQUEST);
        String longUrl = linkPair.getLongUrl();
        //TODO: need to deny empty longUrl
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            String jsonRequestString = createJsonRequestString(longUrl);
            HttpPost httpPost = createPostRequest(jsonRequestString);

            linkPair.setStatus(ResponseStatus.REQUESTING);
            HttpResponse response = httpClient.execute(httpPost);
            linkPair.setStatus(ResponseStatus.REQUESTED);

            return response;
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Error creating json request object with url:");
            Log.e(TAG, longUrl);
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e(TAG, "Error crafting HttpPost");
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            Log.e(TAG, "Error with HTTP request protocol");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e(TAG, "Error with request IO");
            e.printStackTrace();
        }
        catch (URISyntaxException e)
        {
            Log.e(TAG, "Error making URI from String: [" + GOOGLE_URL + "]");
            e.printStackTrace();
        }
        linkPair.setStatus(ResponseStatus.REQUEST_ERROR);
        return null;
    }

    private static HttpPost createPostRequest(String jsonRequestString)
        throws UnsupportedEncodingException, URISyntaxException, JSONException
    {
        StringEntity postEntity = new StringEntity(jsonRequestString);  //UnsupportedEncoding
        postEntity.setContentType(CONTENT_TYPE);
        HttpPost httpPost = new HttpPost();
        httpPost.setURI(new URI(GOOGLE_URL));
        httpPost.setEntity(postEntity);
        return httpPost;
    }

    /**
     * Parse the returned response for the received
     * 
     * @param response
     * @return
     */
    private JSONObject getJsonResponse(LinkPair linkPair, HttpResponse response)
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
            return new JSONObject(sb.toString());
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e(TAG, "Error reading response stream");
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            Log.e(TAG, "Null found retrieving response.getEntity()");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e(TAG, "Error reading response.getEntity().getContent()");
            e.printStackTrace();
        }
        catch (IllegalStateException e)
        {
            Log.e(TAG, "Cannot read response twice.");
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Error making JSONObject from response stream");
            e.printStackTrace();
        }
        linkPair.setStatus(ResponseStatus.RESPONSE_ERROR);
        return null;
    }


    ///////////////////////////////////////////////////////
    ////    json
    ///////////////////////////////////////////////////////

    private static String createJsonRequestString(String longUrl)
        throws JSONException
    {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put(KEY_APIKEY, API_KEY);
        jsonRequest.put(KEY_LONGURL, longUrl);
        return jsonRequest.toString();
    }

    private static String extractJSONShortUrl(LinkPair linkPair, JSONObject jsonResponse)
    {
        try
        {
            return jsonResponse.getString(KEY_SHORTURL);
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Could not get shortUrl from JSONObject");
            linkPair.setStatus(ResponseStatus.JSON_PARSE_ERROR);

        }
        return null;
    }

    private static void parseForError(LinkPair linkPair, JSONObject jsonError)
    {
        try
        {
            JSONObject errorRoot = jsonError.getJSONObject(KEY_ERROR_ROOT);
            JSONArray errors = errorRoot.getJSONArray(KEY_ERRORS_ARRAY);
            JSONObject error;
            String errorLocation;
            String errorReason;
            int length = errors.length();
            for (int i = 0; i < length; i++)
            {
                Log.e(TAG, "Response Error " + (i + 1) + " of " + length);
                try
                {
                    error = (JSONObject) errors.get(i);
                    errorLocation = error.getString(KEY_ERR_LOCATION);
                    Log.e(TAG, "Location: " + errorLocation);
                    errorReason = error.getString(KEY_ERR_REASON);
                    Log.e(TAG, "Reason:   " + errorReason);
                    if (errorLocation.equals(ERR_LOC_LONGURL)
                            && errorReason.equals(ERR_REASON_INVALID))
                    {
                        linkPair.setStatus(ResponseStatus.INVALID_URL);
                        break;
                    }
                }
                catch (ClassCastException e)
                {
                    Log.e(TAG, "Failed to cast as JSONObject");
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "Failed reading error!");
                }
            }
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Error parsing response errors. Little ironic, no?");
            linkPair.setStatus(ResponseStatus.JSON_PARSE_ERROR);
        }

    }


    ///////////////////////////////////////////////////////
    ////    response parsing
    ///////////////////////////////////////////////////////

    /**
     * Get the HTTP status code from a response.
     * 
     * @param response
     * @return
     */
    private static int getResponseStatusCode(HttpResponse response)
    {
        return response.getStatusLine().getStatusCode();
    }


}
