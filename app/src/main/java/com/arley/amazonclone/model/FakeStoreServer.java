package com.arley.amazonclone.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FakeStoreServer {

    @GET("/products/")
    Call<List<Product1>> getProducts();

}
