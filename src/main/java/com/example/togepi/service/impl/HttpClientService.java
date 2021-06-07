package com.example.togepi.service.impl;

import com.example.togepi.dto.http.ZomatoResponseDto;
import com.example.togepi.service.HttpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("httpClientService")
@Slf4j
public class HttpClientService implements HttpService {
	
	private static final int RESTAURANT_ID = 17842104;
	private static final String USER_KEY = "a544f36cbd73b8f3da2aebe2575056ed";
	private static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public ZomatoResponseDto getRestaurant() {
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		
		final HttpGet httpGet = new HttpGet(BASE_URL + "restaurant?res_id=" + RESTAURANT_ID);
		httpGet.setHeader("user-key", USER_KEY);
		
		try {
			final CloseableHttpResponse response = httpClient.execute(httpGet);
			final HttpEntity httpEntity = response.getEntity();
			final String responseBody = EntityUtils.toString(httpEntity);
			return objectMapper.readValue(responseBody, ZomatoResponseDto.class);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
