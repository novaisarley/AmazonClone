package com.arley.amazonclone.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FakeStoreServer {

    @GET("/products/")
    Call<List<Product1>> getProducts();

}
