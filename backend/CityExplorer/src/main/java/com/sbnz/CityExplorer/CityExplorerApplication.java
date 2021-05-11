package com.sbnz.CityExplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@SpringBootApplication
public class CityExplorerApplication {
	
	public static void main(String[] args) {
		//ApplicationContext ctx = 
		SpringApplication.run(CityExplorerApplication.class, args);

//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//
//		StringBuilder sb = new StringBuilder("Application beans:\n");
//		for (String beanName : beanNames) {
//			sb.append(beanName + "\n");
//		}
//		System.out.println(sb.toString());
//		try {
//			KieContainer container = KieContainerConfiguration.kieContainer();
//		} catch (MavenInvocationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
