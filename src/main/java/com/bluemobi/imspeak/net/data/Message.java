package com.bluemobi.imspeak.net.data;

import java.io.Serializable;

/**
 * Created by wangbin on 2015/2/15.
 */
public class Message implements Serializable{

    private static final long serialVersionUID = 1L;

    private String sessionId;

    private String from;

    private String to;

    private String content;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
