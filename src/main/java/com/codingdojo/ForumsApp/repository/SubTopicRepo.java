package com.codingdojo.ForumsApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.ForumSubTopic;

@Repository
public interface SubTopicRepo extends CrudRepository<ForumSubTopic, Long> {
	
	List<ForumSubTopic> findAll();

}
