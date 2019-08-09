package com.tsystems.ecm.dto;

import java.time.LocalDateTime;

public class UserSessionDto {

    private String sid;
    private LocalDateTime expiredDate;
    private String login;
    private String userName;

    public String getSid() {
        return sid;
    }

    public UserSessionDto(String sid, LocalDateTime expiredDate, String login, String userName) {
        this.sid = sid;
        this.expiredDate = expiredDate;
        this.login = login;
        this.userName = userName;
    }

    public UserSessionDto() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

