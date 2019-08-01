package io.summer.eurekaproducer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello Controller.
 * @author summer
 */
@RestController
@RefreshScope
@RequestMapping("/hello")
public class HelloController {

  @Value("${server.port}")
  private Integer port;
  @Value("${summer.hello}")
  private String active;
  @GetMapping("")
  public String hello(@RequestParam String name) {
    return "Hello " + name + ", I'm from port " + port + ", active " + active;
  }

}
