package com.codingdojo.ForumsApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.ForumMainTopic;
import com.codingdojo.ForumsApp.models.ForumSubTopic;

@Repository
public interface MainTopicRepo extends CrudRepository<ForumMainTopic, Long> {
	
	List<ForumMainTopic> findAll();
	ForumMainTopic findByTitle(String title);
	
//	List<ForumMainTopic> findByForumSubTopics(List<ForumSubTopic> forumSubTopics);
}
