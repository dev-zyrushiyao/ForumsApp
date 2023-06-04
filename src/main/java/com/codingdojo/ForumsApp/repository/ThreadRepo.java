package com.codingdojo.ForumsApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.models.ThreadModel;

@Repository
public interface ThreadRepo extends CrudRepository<ThreadModel, Long> {
	List<ThreadModel> findAll();
	
	//Search Threads of particular SubTopic
	List<ThreadModel> findByForumSubTopic(ForumSubTopic forumSubTopic);
	
	
	
}
