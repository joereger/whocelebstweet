package com.celebtwit.helpers;

import com.celebtwit.dao.Twit;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2009
 * Time: 1:26:42 PM
 */
public class TwitUniqueCelebsMentioning {

    private Twit twit;
    private int uniquecelebsmentioning=0;

    public Twit getTwit() {
        return twit;
    }

    public void setTwit(Twit twit) {
        this.twit=twit;
    }

    public int getUniquecelebsmentioning() {
        return uniquecelebsmentioning;
    }

    public void setUniquecelebsmentioning(int uniquecelebsmentioning) {
        this.uniquecelebsmentioning=uniquecelebsmentioning;
    }
}