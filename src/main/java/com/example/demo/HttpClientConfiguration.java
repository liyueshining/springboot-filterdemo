package com.example.demo;

import com.example.demo.http.RedistribService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

@Configuration
public class HttpClientConfiguration {

    @Autowired
    Retrofit retrofit;

    @Bean
    public RedistribService getService(){
        return retrofit.create(RedistribService.class);
    }

}
