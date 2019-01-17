package com.salva.lecture.api.models;

public class UserAuth {

    public User user;
    public String token;
    public Exception ex;
    private String exMessage;

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Exception getEx() {
        return ex;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public void setExMessage(String exMessage) {
        this.exMessage = exMessage;
    }

    public String getExMessage() {
        return exMessage;
    }

    public UserAuth(Exception ex) {
        this.ex = ex;
        this.exMessage = ex.getMessage();
    }

    public UserAuth(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
