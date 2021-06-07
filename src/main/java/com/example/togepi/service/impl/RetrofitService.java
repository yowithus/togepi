package com.example.togepi.service.impl;

import com.example.togepi.dto.http.ZomatoResponseDto;
import com.example.togepi.service.HttpService;
import com.example.togepi.service.RetrofitApi;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

@Service("retrofitHttpService")
@Slf4j
public class RetrofitService implements HttpService {
	
	private static final int RESTAURANT_ID = 17842104;
	private static final String USER_KEY = "a544f36cbd73b8f3da2aebe2575056ed";
	private static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";
	
	@Override
	public ZomatoResponseDto getRestaurant() {
		final RetrofitApi retrofitApi = getRetrofit().create(RetrofitApi.class);
		try {
			final Call<ZomatoResponseDto> call = retrofitApi.getRestaurant(USER_KEY, RESTAURANT_ID);
			final Response<ZomatoResponseDto> response = call.execute();
			return response.body();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	private Retrofit getRetrofit() {
		final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		final OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.addInterceptor(logging)
			.connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS)
			.build();
		
		return new Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build();
	}
}
