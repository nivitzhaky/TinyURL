package com.yaroslav.tinyurl.json;

import java.util.Objects;

public class NewTinyRequest {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTinyRequest that = (NewTinyRequest) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "NewTinyRequest{" +
                "url='" + url + '\'' +
                '}';
    }
}
