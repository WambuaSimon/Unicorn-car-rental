package com.wizag.unicorn.network;


import com.wizag.unicorn.model.VehicleModel;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("documents")
    Call<VehicleModel> getFeaturedVehicles();

}
