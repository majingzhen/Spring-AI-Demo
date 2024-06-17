package com.matuto.springaidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAiDemoApplication {

    public static void main(String[] args) {
//        String proxy = "127.0.0.1";
//        int port = 7890;
//        System.setProperty("proxyHost", proxy);
//        System.setProperty("proxyPort", String.valueOf(port));
//        System.setProperty("proxyType", "4");
//        System.setProperty("proxySet", "true");

        SpringApplication.run(SpringAiDemoApplication.class, args);
    }

}
