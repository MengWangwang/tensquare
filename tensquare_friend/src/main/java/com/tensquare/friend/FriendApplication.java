package com.tensquare.friend;

import com.common.util.IdWorker;
import com.common.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class FriendApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
