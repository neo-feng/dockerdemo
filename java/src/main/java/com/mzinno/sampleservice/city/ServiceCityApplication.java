package com.mzinno.sampleservice.city;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@ComponentScan({"com.mzinno.sampleservice.city",
        "com.mzinno.sampleservice.citycommon.mapper",
        "com.mzinno.sampleservice.models.config"})
@MapperScan("com.mzinno.sampleservice.citycommon.mapper")
public class ServiceCityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCityApplication.class, args);
    }
}