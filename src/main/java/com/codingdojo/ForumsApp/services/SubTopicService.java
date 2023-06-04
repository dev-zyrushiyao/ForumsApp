package com.codingdojo.ForumsApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.ForumsApp.models.ForumMainTopic;
import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.models.ThreadModel;
import com.codingdojo.ForumsApp.repository.SubTopicRepo;

import net.bytebuddy.asm.Advice.Return;

@Service
public class SubTopicService {
	
	@Autowired
	private SubTopicRepo subTopicRepo ;
	
	public ForumSubTopic createTopic(ForumSubTopic forumSubTopic) {
		return this.subTopicRepo.save(forumSubTopic);
	}
	
	public ForumSubTopic updateTopic(ForumSubTopic forumSubTopic) {
		return this.subTopicRepo.save(forumSubTopic);
	}
	
	public List<ForumSubTopic> findAll() {
		return this.subTopicRepo.findAll();
	}
	
	public ForumSubTopic findId(Long id) {
		return this.subTopicRepo.findById(id).orElse(null);
	}
	
	public void deleteId(Long id) {
		this.subTopicRepo.deleteById(id);
	}
	
	public List<ForumSubTopic> findSubTopicByMainTopic(ForumMainTopic forumMainTopic){
		return this.subTopicRepo.findByForumMainTopics(forumMainTopic);
	}
	
	public ForumSubTopic findTitle(String title) {
		Optional<ForumSubTopic> optional = this.subTopicRepo.findByTitle(title);
			if(optional.isPresent()) {
				return optional.get();
			}else {
				return null;
			}
	}
	
}
