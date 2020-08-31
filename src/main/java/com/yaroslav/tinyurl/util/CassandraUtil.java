package com.yaroslav.tinyurl.util;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.yaroslav.tinyurl.json.UserClick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CassandraUtil {
    @Autowired
    CqlSession cassandra;

    public void insertClick(UserClick click) {
        cassandra.execute("insert into clicks.user_clicks  (username, tiny, url, click_time) " +
                String.format("values ('%s','%s', '%s',dateof(now()));",click.getUser(), click.getTiny(),click.getUrl()));
    }

    public List<UserClick> getClicks(String username, String tiny) {
        ResultSet rs =  cassandra.execute("select url, click_time from clicks.user_clicks where username = '" + username + "' and tiny = '" + tiny + "'");
        return rs.all().stream().map(row -> new UserClick(username, tiny,
                row.getString("url"), Date.from(row.getInstant("click_time")))).collect(Collectors.toList());
    }

    public void dropClickTable() {
        cassandra.execute("drop table clicks.user_clicks;");
    }

    @PostConstruct
    public void createClickTable() {
        cassandra.execute("create table IF NOT EXISTS " +
                "clicks.user_clicks( username text, tiny text, url text, click_time timestamp, PRIMARY KEY((username, tiny),click_time) )  WITH CLUSTERING ORDER BY (click_time DESC)");
    }


}
