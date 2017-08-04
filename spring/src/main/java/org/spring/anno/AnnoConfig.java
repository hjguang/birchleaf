package org.spring.anno;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.spring.service")
public class AnnoConfig {

	public AnnoConfig() {
		System.out.println("容器初始化-------------");
	}
}
