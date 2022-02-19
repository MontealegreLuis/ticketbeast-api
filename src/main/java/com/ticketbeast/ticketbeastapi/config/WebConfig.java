package com.ticketbeast.ticketbeastapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.montealegreluis.apiproblemspringboot.jackson.ApiProblemModule;
import com.ticketbeast.ticketbeastapi.mappers.StringValueObjectSerializer;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(new StringHttpMessageConverter());
    converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
    converters.add(new ByteArrayHttpMessageConverter());
  }

  @Bean
  public ObjectMapper objectMapper() {
    SimpleModule module = new SimpleModule().addSerializer(new StringValueObjectSerializer());

    return new ObjectMapper().registerModules(module, new ApiProblemModule());
  }
}
