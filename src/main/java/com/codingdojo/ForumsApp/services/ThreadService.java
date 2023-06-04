package com.codingdojo.ForumsApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.models.ThreadModel;
import com.codingdojo.ForumsApp.repository.ThreadRepo;

@Service
public class ThreadService {
	
	@Autowired
	private ThreadRepo threadRepo;
	
	public ThreadModel createThread(ThreadModel threadModel) {
		return this.threadRepo.save(threadModel);
	}
	
	public ThreadModel updateThread(ThreadModel threadModel) {
		return this.threadRepo.save(threadModel);
	}
	
	public ThreadModel findThreadById(Long id) {
		return this.threadRepo.findById(id).orElse(null);
	}
	
	public List<ThreadModel> findAllThread() {
		return this.threadRepo.findAll();
	}
	
	public void deleteThreadById(Long id) {
		this.threadRepo.deleteById(id);
	}
	
	public void deleteAllThread() {
		this.threadRepo.deleteAll();
	}
	
	//Search Threads of particular SubTopic
	public List<ThreadModel> findByForumSubTopic(ForumSubTopic forumSubTopic){
		return this.threadRepo.findByForumSubTopic(forumSubTopic);
	}
	
}
