package com.yaroslav.tinyurl.json;

import java.util.Date;
import java.util.Objects;

public class UserClick {
    String user;
    String tiny;
    String url;
    Date clickTime;

    public UserClick(String user, String tiny, String url, Date clickTime) {
        this.user = user;
        this.tiny = tiny;
        this.url = url;
        this.clickTime = clickTime;
    }

    public String getUser() {
        return user;
    }

    public String getTiny() {
        return tiny;
    }

    public String getUrl() {
        return url;
    }

    public Date getClickTime() {
        return clickTime;
    }

    @Override
    public String toString() {
        return "UserClick{" +
                "user='" + user + '\'' +
                ", tiny='" + tiny + '\'' +
                ", url='" + url + '\'' +
                ", clickTime=" + clickTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserClick userClick = (UserClick) o;
        return Objects.equals(user, userClick.user) &&
                Objects.equals(tiny, userClick.tiny) &&
                Objects.equals(url, userClick.url) &&
                Objects.equals(clickTime, userClick.clickTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, tiny, url, clickTime);
    }
}
