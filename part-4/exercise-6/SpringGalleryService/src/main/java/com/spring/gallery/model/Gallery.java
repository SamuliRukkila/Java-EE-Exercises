package com.spring.gallery.model;

import java.util.List;

public class Gallery {
  
  private int id;
  private List<Object> images;
  
  
  /* KONSTRUKTORIT */
  
  public Gallery() {}

  public Gallery(int galleryId) {
    this.id = galleryId;
  }

  
  /* SETTERIT JA GETTERIT */
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Object> getImages() {
    return images;
  }

  public void setImages(List<Object> images) {
    this.images = images;
  }
  
}