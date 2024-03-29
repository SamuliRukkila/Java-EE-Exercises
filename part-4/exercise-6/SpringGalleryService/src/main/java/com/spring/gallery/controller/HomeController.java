package com.spring.gallery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.gallery.model.Gallery;

@RestController
@RequestMapping("/")
public class HomeController {
  
  @Autowired
  private RestTemplate restTemplate;
  
  @Autowired
  private Environment env;
  
  
  @RequestMapping("/")
  public String home() {
    // This is useful for debugging
    // When having multiple instance of gallery service running at different ports.
    // We load balance among them, and display which instance received the request.
    return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
  }
  
  
  @SuppressWarnings("unchecked")
  @RequestMapping("/{id}")
  public Gallery getGallery(@PathVariable final int id) {
    // create gallery object
    Gallery gallery = new Gallery();
    gallery.setId(id);
    
    // get list of available images 
    List<Object> images = restTemplate.getForObject("http://image-service/images/", List.class);
    gallery.setImages(images);
  
    return gallery;
  }
}
