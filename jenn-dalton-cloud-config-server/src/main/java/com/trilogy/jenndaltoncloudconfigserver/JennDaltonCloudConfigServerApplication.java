package com.trilogy.jenndaltoncloudconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class JennDaltonCloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JennDaltonCloudConfigServerApplication.class, args);
	}

}
