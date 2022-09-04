package com.toss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    BASE64Encoder base64Encoder() {
        return new BASE64Encoder();
    }

    @Bean
    BASE64Decoder base64Decoder() {
        return new BASE64Decoder();
    }
}
