package com.example.togepi.dao;

import com.example.togepi.entity.Elasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticsearchDao extends ElasticsearchRepository<Elasticsearch, String> {
	
	List<Elasticsearch> findByName(String name);
}
