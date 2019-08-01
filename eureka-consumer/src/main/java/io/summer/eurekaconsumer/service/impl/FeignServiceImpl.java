package io.summer.eurekaconsumer.service.impl;

import io.summer.eurekaconsumer.service.FeignService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author summer
 */
@Component
public class FeignServiceImpl implements FeignService {

  @Override
  public String hello(@RequestParam(value = "name") String name) {
    return "Something bad happened";
  }
}
