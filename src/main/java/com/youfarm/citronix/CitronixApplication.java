package com.youfarm.citronix;

import com.youfarm.citronix.common.config.FarmConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FarmConfigProperties.class)
public class CitronixApplication {

	private static final Logger log = LoggerFactory.getLogger(CitronixApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CitronixApplication.class, args);
	}


}
