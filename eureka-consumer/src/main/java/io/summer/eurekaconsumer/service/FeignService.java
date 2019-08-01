package io.summer.eurekaconsumer.service;

import io.summer.eurekaconsumer.service.impl.FeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author summer
 * @date 2019/7/30
 */
@Service
@FeignClient(name = "eureka-producer", fallback = FeignServiceImpl.class)
public interface FeignService {

  /**
   * Say hello.
   */
  @GetMapping("/hello")
  String hello(@RequestParam(value = "name") String name);

}
