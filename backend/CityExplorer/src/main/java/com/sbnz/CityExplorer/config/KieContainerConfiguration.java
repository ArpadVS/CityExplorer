package com.sbnz.CityExplorer.config;

import java.io.File;
import java.util.Arrays;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KieContainerConfiguration {
//    @Bean
//    public static KieContainer kieContainer() throws MavenInvocationException {
//        mavenCleanAndInstall();
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kContainer = ks
//                .newKieContainer(ks.newReleaseId("com.sbnz", "CityExplorerKJAR", "1.0.0-SNAPSHOT"));
//        KieScanner kScanner = ks.newKieScanner(kContainer);
//        kScanner.start(3000);
//        return kContainer;
//    }
//    
//    
//
//    public static void mavenCleanAndInstall() throws MavenInvocationException {
//        InvocationRequest request = new DefaultInvocationRequest();
//        request.setPomFile(new File("../CityExplorerKJAR/pom.xml"));
//        request.setGoals(Arrays.asList("clean", "install"));
//
//        Invoker invoker = new DefaultInvoker();
//        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
//        invoker.execute(request);
//    }
}

