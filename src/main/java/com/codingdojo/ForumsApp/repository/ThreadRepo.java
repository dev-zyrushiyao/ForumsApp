package com.codingdojo.ForumsApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.models.ThreadModel;

@Repository
public interface ThreadRepo extends PagingAndSortingRepository<ThreadModel, Long> {
	List<ThreadModel> findAll();
	//Search Threads of particular SubTopic
	List<ThreadModel> findByForumSubTopic(ForumSubTopic forumSubTopic);
	
	//Pagination Repo - ALSO EXTENDS CRUD REPO
	Page<ThreadModel> findAll(Pageable pageable);

}
