package com.codingdojo.ForumsApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.ForumsApp.models.ForumMainTopic;
import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.repository.MainTopicRepo;


@Service
public class MainTopicService {
	
	@Autowired
	private MainTopicRepo mainTopicRepo;
	
	public ForumMainTopic createTopic(ForumMainTopic forumMainTopic) {
		return this.mainTopicRepo.save(forumMainTopic);
	}
	
	public ForumMainTopic updateTopic(ForumMainTopic forumMainTopic) {
		return this.mainTopicRepo.save(forumMainTopic);
	}
	
	public void deleteTopic(Long id) {
		this.mainTopicRepo.deleteById(id);
	}
	
	public ForumMainTopic findTopicById(Long id) {
		Optional<ForumMainTopic> optional = this.mainTopicRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public List<ForumMainTopic> findAllTopic() {
		return this.mainTopicRepo.findAll();
	}
	
	public ForumMainTopic findMainForumByTitle(String title) {
		return this.mainTopicRepo.findByTitle(title);
	}
	
//	public List<ForumMainTopic> findSubTopicOf(List<ForumSubTopic> subTopic) {
//		return this.mainTopicRepo.findByForumSubTopics(subTopic);
//	}
}
