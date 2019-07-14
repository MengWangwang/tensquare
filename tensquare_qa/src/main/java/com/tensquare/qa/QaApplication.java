package com.tensquare.qa;
import com.common.entity.Result;
import com.common.util.JwtUtil;
import com.tensquare.qa.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import com.common.util.IdWorker;
@SpringBootApplication
@EnableEurekaClient  //客户端标识
@EnableDiscoveryClient //发现服务
@EnableFeignClients //用Feign的方式发现服务
public class QaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new JwtUtil();
	};

}
