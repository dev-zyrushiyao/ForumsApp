package com.codingdojo.ForumsApp.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.CommentModel;
import com.codingdojo.ForumsApp.models.ThreadModel;

@Repository
public interface CommentRepo extends CrudRepository<CommentModel, Long> {
	
	List<CommentModel> findAll();
	
	List<CommentModel> findByThreadTopic(ThreadModel threadTopic);
	
}
