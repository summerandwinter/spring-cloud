package io.summer.eurekaproducer.controller;

import io.summer.eurekaproducer.util.DeviceUtil;
import java.util.List;
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
  private String hello;
  @GetMapping("")
  public String hello(@RequestParam String name) {
    List<String> ips = DeviceUtil.getLocalAddress();
    String ip = "unknown";
    if (ips.size() > 0) {
      ip = ips.get(0);
    }
    return "Hello " + name + ", I'm from " + ip + ", here is the greeting from github " + hello;
  }

}
