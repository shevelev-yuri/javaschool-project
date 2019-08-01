package com.tsystems.ecm.dto;

import java.time.LocalDateTime;

public class UserSession {

    private String sid;
    private LocalDateTime expiredDate;
    private String login;

    public String getSid() {
        return sid;
    }

    public UserSession(String sid, LocalDateTime expiredDate, String login) {
        this.sid = sid;
        this.expiredDate = expiredDate;
        this.login = login;
    }

    public UserSession() {
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

