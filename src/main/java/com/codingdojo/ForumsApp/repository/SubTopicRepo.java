package com.codingdojo.ForumsApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.ForumMainTopic;
import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.models.ThreadModel;

@Repository
public interface SubTopicRepo extends CrudRepository<ForumSubTopic, Long> {
	
	List<ForumSubTopic> findAll();
	List<ForumSubTopic> findByForumMainTopics(ForumMainTopic forumMainTopics);
	
	Optional<ForumSubTopic> findByTitle(String title);

}
