package com.example.togepi.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "index")
@Data
public class Elasticsearch {
	
	@Id
	private Long id;
	
	private String name;
}
