package com.example.togepi.dao;

import com.example.togepi.entity.Redis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisDao extends JpaRepository<Redis, String> {
}