package com.example.togepi.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash(value = "redis", timeToLive = 1800)
@Data
public class Redis {
	
	@Id
	private Long id;
	
	private String name;
}
