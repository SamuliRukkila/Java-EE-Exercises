package com.springwebflux.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BookRouter {

  @Bean
  public RouterFunction<ServerResponse> route(BookHandler handler) {
    return RouterFunctions
        .route(GET("/fbooks").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
        .andRoute(GET("/fbook/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), handler::findById)
        .andRoute(POST("/fbook").and(accept(MediaType.APPLICATION_JSON)), handler::save)
        .andRoute(DELETE("/fbook/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
  }
  
}
