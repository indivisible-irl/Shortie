package com.indivisible.shortie.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.indivisible.shortie.service.ResponseStatus;


/**
 * Class to hold details for a shortened link.
 * 
 * @author indiv
 */
public class LinkPair
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    //TODO: store shortener service
    private long id = -1;
    private long createdMillis = -1;
    private String longUrl = DEFAULT_URL;
    private String shortUrl = DEFAULT_URL;
    private ResponseStatus status;

    //TODO: Get user's time format preference
    //private static final String FORMAT_DATE_ISO = "yyyy-MM-dd HH:mm";
    //private static final String FORMAT_DATE_US = "MMMMM dd yyyy HH:mm";
    private static final String FORMAT_DATE_EU = "dd MMM yyyy HH:mm";
    public static final String DEFAULT_URL = "NONE";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    public LinkPair()
    {}

    public LinkPair(long createdMillis, String longUrl, ResponseStatus status)
    {
        this.createdMillis = createdMillis;
        this.longUrl = longUrl;
        this.status = status;
    }

    public LinkPair(long createdMillis, String longUrl, String shortUrl,
            ResponseStatus status)
    {
        this(createdMillis, longUrl, status);
        this.shortUrl = shortUrl;
    }

    public LinkPair(long id, long createdMillis, String longUrl, String shortUrl,
            ResponseStatus status)
    {
        this(createdMillis, longUrl, shortUrl, status);
        this.id = id;
    }


    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getCreatedMillis()
    {
        return this.createdMillis;
    }

    public void setCreatedMillis(long createdMillis)
    {
        this.createdMillis = createdMillis;
    }

    public String getLongUrl()
    {
        return this.longUrl;
    }

    public void setLongUrl(String longUrl)
    {
        this.longUrl = longUrl;
    }

    public String getShortUrl()
    {
        return this.shortUrl;
    }

    public void setShortUrl(String shortUrl)
    {
        if (shortUrl != null) this.shortUrl = shortUrl;
    }

    public ResponseStatus getStatus()
    {
        return status;
    }

    public void setStatus(ResponseStatus status)
    {
        this.status = status;
    }


    ///////////////////////////////////////////////////////
    ////    methods
    ///////////////////////////////////////////////////////

    public Date getCreatedDate()
    {
        return new Date(this.createdMillis);
    }

    public String getPrintableDate()
    {
        Date created = getCreatedDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_EU, Locale.US);
        return dateFormat.format(created);
    }

    public String getPrintableAge()
    {
        //long now = System.currentTimeMillis();
        //long diff = now - createdMillis;
        return "TODO: get Joda";
    }

    public String getStatusOrShortUrl()
    {
        if (this.shortUrl.equals(DEFAULT_URL))
        {
            return ResponseStatus.getString(status);
        }
        return this.shortUrl;
    }

    ///////////////////////////////////////////////////////
    ////    overrides
    ///////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof LinkPair)
        {
            LinkPair linkPair = (LinkPair) o;
            return (this.getId() - linkPair.getId()) == 0;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return ((Long) this.getId()).hashCode();
    }

    @Override
    public String toString()
    {
        return String.format(Locale.US,
                             "%2d: %s|%s",
                             this.getId(),
                             this.getLongUrl(),
                             this.getShortUrl());
    }


}
