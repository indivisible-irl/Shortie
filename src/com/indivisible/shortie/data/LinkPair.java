package com.indivisible.shortie.data;


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
    private int createdMillis = -1;
    private String longUrl = "NONE";
    private String shortUrl = "NONE";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    public LinkPair()
    {}

    public LinkPair(long id, int createdMillis, String longUrl, String shortUrl)
    {
        this.id = id;
        this.createdMillis = createdMillis;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
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

    public int getCreatedMillis()
    {
        return this.createdMillis;
    }

    public void setCreatedMillis(int createdMillis)
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
        this.shortUrl = shortUrl;
    }
}
