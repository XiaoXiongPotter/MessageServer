package com.dognessnetwork.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * = MessageServerApplication
 *
 * TODO Auto-generated class documentation
 *
 */
@SpringBootApplication
public class MessageServerApplication extends SpringBootServletInitializer{

    /**
     * TODO Auto-generated method documentation
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MessageServerApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
         return builder.sources(MessageServerApplication.class);
    }
}