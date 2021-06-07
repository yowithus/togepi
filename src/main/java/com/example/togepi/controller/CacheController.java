package com.example.togepi.controller;

import com.example.togepi.dao.ElasticsearchDao;
import com.example.togepi.dao.RedisDao;
import com.example.togepi.entity.Elasticsearch;
import com.example.togepi.entity.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CacheController {
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private ElasticsearchDao elasticsearchDao;
	
	@PostMapping(value = "/cache/redis")
	public void redis() {
		final Redis redis = new Redis();
		redis.setName("asd");
		redisDao.save(redis);
	}
	
	@PostMapping(value = "/cache/elasticsearch")
	public void createElasticsearch() {
		final Elasticsearch elasticsearch = new Elasticsearch();
		elasticsearch.setName("asd");
		elasticsearchDao.save(elasticsearch);
	}
	
	@GetMapping(value = "/cache/elasticsearch")
	public List<Elasticsearch> getElasticsearch() {
		return elasticsearchDao.findByName("asd");
	}
}
