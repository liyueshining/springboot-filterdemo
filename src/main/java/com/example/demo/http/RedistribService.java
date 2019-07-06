package com.example.demo.http;

import com.example.demo.entity.HMessage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RedistribService {

    @GET
    Call<HMessage> getHelloWorld(@Url String url);
}
