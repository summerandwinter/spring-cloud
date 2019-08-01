package io.summer.eurekaconsumer.controller;

import io.summer.eurekaconsumer.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author summer
 * @date 2019/7/30
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
  private final FeignService feignService;
  @Autowired
  public HelloController(FeignService feignService) {
    this.feignService = feignService;
  }

  @RequestMapping("")
  public String index(String name) {
    return feignService.hello(name);
  }

}
