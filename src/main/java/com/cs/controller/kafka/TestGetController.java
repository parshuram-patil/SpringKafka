package com.cs.controller.kafka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestGetController {
  
  @GetMapping("testGet/{id}")
  public @ResponseBody String handleRequest(@PathVariable String id)
  {
    System.out.println("*************        Test Get Controller        *************");
    
    return id;
    
  }
  
}
