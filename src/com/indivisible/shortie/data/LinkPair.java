package com.indivisible.shortie.data;

import java.text.SimpleDateFormat;
import java.util.Date;


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

    private long id = -1;
    private long createdMillis = -1;
    private String longUrl = "NONE";
    private String shortUrl = "NONE";

    private static final String FORMAT_DATE_ISO = "yyyy-MM-dd HH:mm";
    private static final String FORMAT_DATE_US = "MMMMM-dd-yyyy HH:mm";
    private static final String FORMAT_DATE_EU = "dd-MMMMM-yyyy HH:mm";

    //TODO: Get user's time format preference


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    public LinkPair()
    {}

    public LinkPair(long createdMillis, String longUrl, String shortUrl)
    {
        this.createdMillis = createdMillis;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public LinkPair(long id, long createdMillis, String longUrl, String shortUrl)
    {
        this(createdMillis, longUrl, shortUrl);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_EU);
        return dateFormat.format(created);
    }

    public String getPrintableAge()
    {
        long now = System.currentTimeMillis();
        long diff = now - createdMillis;
        return "TODO: get Joda";
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
        return String.format("%2d: %s", this.getId(), this.getLongUrl());
    }


}
