package com.yaroslav.tinyurl.config;

import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CassandraConfig {
    @Bean("cassandraSession")
    public CqlSession getCassandraSession() throws URISyntaxException {
        return CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(getClass().getResource("/secure-connect-yaroslavdb.zip").toURI()))
                .withAuthCredentials("ygessen","AZSo5088")
                .withKeyspace("clicks")
                .build();
    }
}
