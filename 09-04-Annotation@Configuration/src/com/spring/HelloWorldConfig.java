package com.spring;
import org.springframework.context.annotation.*;

import com.spring.HelloWorld;

@Configuration
public class HelloWorldConfig {

   @Bean(initMethod = "init", destroyMethod = "destroy" )
   @Scope("singleton")
   public HelloWorld helloWorld(){ return new HelloWorld(); }
}