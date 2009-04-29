package com.celebtwit.scheduledjobs;

import java.util.Date;

/**
 * User: Joe Reger Jr
 * Date: Apr 18, 2009
 * Time: 10:51:09 AM
 */
public class TwitterStatus {

    private Date created_at;
    private String created_at_string;
    private String text;
    private String id;
    private String profile_image_url;
    private String website_url;
    private String description;
    private String followers_count;
    private String statuses_count;


    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at=created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text=text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getCreated_at_string() {
        return created_at_string;
    }

    public void setCreated_at_string(String created_at_string) {
        this.created_at_string=created_at_string;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url=profile_image_url;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url=website_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count=followers_count;
    }

    public String getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(String statuses_count) {
        this.statuses_count=statuses_count;
    }
}
