package org.spring.anno;

import org.spring.service.ObjService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConfigTest {

	public static void main(String[] args) {
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.register(AnnoConfig.class);
//		ctx.refresh();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ObjService objService = ctx.getBean(ObjService.class);
		objService.say();
	}

}
