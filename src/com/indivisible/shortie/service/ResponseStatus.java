package com.indivisible.shortie.service;

/**
 * Enumerator used to indicate the state or error point in a URL shorten
 * request
 * 
 * @author indiv
 */
public enum ResponseStatus
{
    UNKNOWN,            // not one of the other valid values
    NONE,               // no request has been made yet
    PRE_REQUEST,        // preparing for request
    REQUESTING,         // request has been made
    REQUEST_ERROR,      // failed to make request
    NO_CONNECTION,      // could not make request due to failed network connection
    REQUESTED,          // request completed successfully
    RESPONSE_ERROR,     // error with response
    JSON_PARSE_ERROR,   // problem parsing received JSON response
    INVALID_CALL,       // request successful, service rejected api call
    INVALID_URL,        // request successful, service rejected URL
    OK;                 // request & response successful


    //TODO: set @string resource gets here for future locale compatibility
    private static final String strUnknown = "UNKNOWN";
    private static final String strNone = "Not started";
    private static final String strPreRequest = "Preparing request";
    private static final String strRequesting = "Requesting";
    private static final String strRequestError = "HTTP request error";
    private static final String strNoConnection = "No connection";
    private static final String strRequested = "Requested";
    private static final String strJsonParseErr = "Json request parsing error";
    private static final String strInvalidCall = "Invalid API request";
    private static final String strInvalidUrl = "Invalid URL sent";
    private static final String strOk = "Ok";

    /**
     * Get a String representation of the ResponseStatus. <br/>
     * Can be saved into the database and displayed on screen.
     * 
     * @param status
     * @return
     */
    public static final String getString(ResponseStatus status)
    {
        if (status == null)
        {
            return "NULL";
        }
        switch (status)
        {
            case NONE:
                return strNone;
            case PRE_REQUEST:
                return strPreRequest;
            case REQUEST_ERROR:
                return strRequestError;
            case REQUESTING:
                return strRequesting;
            case NO_CONNECTION:
                return strNoConnection;
            case REQUESTED:
                return strRequested;
            case JSON_PARSE_ERROR:
                return strJsonParseErr;
            case INVALID_CALL:
                return strInvalidCall;
            case INVALID_URL:
                return strInvalidUrl;
            case OK:
                return strOk;
            default:
                return strUnknown;
        }
    }

    /**
     * Get a ResponseStatus enum from it's representative String. <br/>
     * Not cross locale safe.
     * 
     * @param statusName
     * @return
     */
    public static final ResponseStatus getStatus(String statusName)
    {
        if (statusName.equals(OK.name()))
        {
            return ResponseStatus.OK;
        }
        if (statusName.equals(NONE.name()))
        {
            return ResponseStatus.NONE;
        }
        else if (statusName.equals(PRE_REQUEST.name()))
        {
            return ResponseStatus.PRE_REQUEST;
        }
        else if (statusName.equals(REQUEST_ERROR.name()))
        {
            return ResponseStatus.REQUEST_ERROR;
        }
        else if (statusName.equals(REQUESTING.name()))
        {
            return ResponseStatus.REQUESTING;
        }
        else if (statusName.equals(NO_CONNECTION.name()))
        {
            return ResponseStatus.NO_CONNECTION;
        }
        else if (statusName.equals(REQUESTED.name()))
        {
            return ResponseStatus.REQUESTED;
        }
        else if (statusName.equals(JSON_PARSE_ERROR.name()))
        {
            return ResponseStatus.JSON_PARSE_ERROR;
        }
        else if (statusName.equals(INVALID_CALL.name()))
        {
            return ResponseStatus.INVALID_CALL;
        }
        else if (statusName.equals(INVALID_URL.name()))
        {
            return ResponseStatus.INVALID_URL;
        }
        else
        {
            return ResponseStatus.UNKNOWN;
        }
    }
}
