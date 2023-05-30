package com.codingdojo.ForumsApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.ForumMainTopic;

@Repository
public interface MainTopicRepo extends CrudRepository<ForumMainTopic, Long> {
	
	List<ForumMainTopic> findAll();
}
