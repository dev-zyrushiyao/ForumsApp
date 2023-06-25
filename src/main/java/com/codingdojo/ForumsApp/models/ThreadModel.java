package com.codingdojo.ForumsApp.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.codingdojo.ForumsApp.auth.UserModel;

@Entity
@Table(name= "topic_thread")
public class ThreadModel {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "This field should not be blank")
	@Size(min = 5 , max = 100 , message = "This parameter accept 5 ~ 100 characters")
	private String title;
	
	@NotBlank(message = "This field should not be blank")
	@Size(min = 1 , max = 200 , message = "This parameter only accept 1 ~ 200 characters")
	private String content;
	
	//Subtopic -> Maintopic
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="topic_sub_id")
	private ForumSubTopic forumSubTopic;
	
	//Subtopic -> Thread
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="thread_id")
	private UserModel userThread;
	
	@OneToMany(mappedBy = "threadTopic"  , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<CommentModel> comments;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public ThreadModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThreadModel(
			@NotBlank(message = "This field should not be blank") @Size(min = 5, max = 100, message = "This parameter accept 4 ~ 15 characters") String title,
			@NotBlank(message = "This field should not be blank") @Size(min = 1, max = 200, message = "This parameter accept 4 ~ 15 characters") String content,
			ForumSubTopic forumSubTopic, UserModel userThread) {
		super();
		this.title = title;
		this.content = content;
		this.forumSubTopic = forumSubTopic;
		this.userThread = userThread;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Date getCreatedAt() {
		return createdAt;
	}
	
	public String getCreatedAtFormatted() {
		String pattern = "dd/MMM/yyyy · h:mm a";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String formattedDate = simpleDateFormat.format(createdAt);
		
		return formattedDate;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	// One To Many
	public ForumSubTopic getForumSubTopic() {
		return forumSubTopic;
	}
	
	public void setForumSubTopic(ForumSubTopic forumSubTopic) {
		this.forumSubTopic = forumSubTopic;
	}
	
	//One To Many
	public UserModel getUserThread() {
		return userThread;
	}

	public void setUserThread(UserModel userThread) {
		this.userThread = userThread;
	}
	
	//One to Many
	public List<CommentModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}
	
	
}
