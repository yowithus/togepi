package com.example.togepi.service;

import com.example.togepi.dto.http.ZomatoResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitApi {
	
	@GET("restaurant")
	Call<ZomatoResponseDto> getRestaurant(@Header("user-key") String userKey, @Query("res_id") Integer restaurantId);
}

