package com.topcoder.onlinereview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class OnlineReviewApplication {

  public static void main(String[] args) {
    SpringApplication.run(OnlineReviewApplication.class, args);
  }
}
