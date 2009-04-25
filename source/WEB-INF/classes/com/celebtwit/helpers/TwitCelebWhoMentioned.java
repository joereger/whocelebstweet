package com.celebtwit.helpers;

import com.celebtwit.dao.Twit;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2009
 * Time: 1:26:42 PM
 */
public class TwitCelebWhoMentioned {

    private Twit twit;
    private int mentions=0;

    public Twit getTwit() {
        return twit;
    }

    public void setTwit(Twit twit) {
        this.twit=twit;
    }

    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions=mentions;
    }
}