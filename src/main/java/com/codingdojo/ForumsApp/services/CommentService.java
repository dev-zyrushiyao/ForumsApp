package com.codingdojo.ForumsApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.ForumsApp.models.CommentModel;
import com.codingdojo.ForumsApp.models.ThreadModel;
import com.codingdojo.ForumsApp.repository.CommentRepo;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	//create comment
	public CommentModel createComment(CommentModel commentModel) {
		return this.commentRepo.save(commentModel);
	}
	
	//update comment
	public CommentModel updateComment(CommentModel commentModel) {
		return this.commentRepo.save(commentModel);
	}
	
	//comment find by id
	public CommentModel findReplyId(Long id) {
		return this.commentRepo.findById(id).orElse(null);
	}
	
	//comment find all
	public List<CommentModel> findAllComment() {
		return this.commentRepo.findAll();
	}
	
	//delete comment by id
	public void deleteCommentId(Long id) {
		this.commentRepo.deleteById(id);
	}
	
	//purge all comment data
	public void deleteAllComments() {
		this.commentRepo.deleteAll();
	}	
	
	//find comments on a thread
	public List<CommentModel> findCommentsOnThread(ThreadModel threadTopic){
		return this.commentRepo.findByThreadTopic(threadTopic);
	}
}
