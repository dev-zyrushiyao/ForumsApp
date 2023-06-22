package com.codingdojo.ForumsApp.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.codingdojo.ForumsApp.auth.UserModel;

@Entity
@Table(name= "topic_comment")
public class CommentModel {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "This field should not be blank")
	@Size(min = 1 , max = 1000 , message = "This parameter only accept 1 ~ 1000 characters")
	private String comment;
	
	//Comment -> Thread
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "thread_topic_id")
	private ThreadModel threadTopic;
	
	//Comment -> UserModel
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_account_id")
	private UserModel userAccount;
	
	
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

	public CommentModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public CommentModel(
			@NotBlank(message = "This field should not be blank") @Size(min = 1, max = 500, message = "This parameter only accept 1 ~ 500 characters") String comment,
			ThreadModel threadTopic, UserModel userAccount) {
		super();
		this.comment = comment;
		this.threadTopic = threadTopic;
		this.userAccount = userAccount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

	public Date getCreatedAt() {
		return createdAt;
	}
	
	public String getCreatedAtFormatted() {
		String pattern = "dd/MMM/yyyy h:mm a";
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
	
	// One to Many
	public ThreadModel getThreadTopic() {
		return threadTopic;
	}
	
	public void setThreadTopic(ThreadModel threadTopic) {
		this.threadTopic = threadTopic;
	}
	
	// One to Many
	public UserModel getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(UserModel userAccount) {
		this.userAccount = userAccount;
	}
	

}
