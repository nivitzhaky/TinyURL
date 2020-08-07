package com.yaroslav.tinyurl.json;

import java.util.Objects;

public class NewTinyRequest {
    private String url;

    private String user;

    @Override
    public String toString() {
        return "NewTinyRequest{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTinyRequest that = (NewTinyRequest) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, user);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
