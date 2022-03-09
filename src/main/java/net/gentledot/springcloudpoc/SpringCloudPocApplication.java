package net.gentledot.springcloudpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudPocApplication.class, args);
    }

}
