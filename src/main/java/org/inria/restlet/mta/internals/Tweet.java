package org.inria.restlet.mta.internals;

import java.util.Date;

public class Tweet {
    private int id;
    private String content;
    private Date timestamp;

    public Tweet(int tweetId, String content) {
        this.content = content;
        this.timestamp = new Date();
    }

    // Getters et setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
