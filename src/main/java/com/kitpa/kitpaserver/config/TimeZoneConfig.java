package com.kitpa.kitpaserver.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    @PostConstruct
    public void initTimezone(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
