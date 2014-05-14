package com.indivisible.shortie.service;

import com.indivisible.shortie.data.LinkPair;


public interface Shortener
{

    LinkPair shortenUrl(LinkPair linkPair);
}
