package com.cs.controller.kafka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestPostController {
  
  @PostMapping("testPost/{id}")
  public @ResponseBody String handleRequest(@RequestBody String payLoad, @PathVariable String id)
  {
    System.out.println("*************        Test Post Controller  : "+ payLoad +"      *************");
    
    return id;
    
  }
  
}
