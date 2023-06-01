package com.codingdojo.ForumsApp.models;

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


@Entity
@Table(name="topic_sub")
public class ForumSubTopic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank
	@Size(min = 4 , max = 30 )
	private String title;
	
	@NotBlank
	@Size(min = 4 , max = 40 )
	private String description;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	//Main Topic -> Sub Topic
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="topic_main_id")
	private ForumMainTopic forumMainTopics;
	
	//Sub Topic -> Thread
	@OneToMany(mappedBy = "forumSubTopic" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ThreadModel> threadTopics;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	

	public ForumSubTopic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumSubTopic(
			@NotBlank(message = "This field should not be blank") @Size(min = 4, max = 15, message = "This parameter accept 4 ~ 15 characters") String title,
			@NotBlank(message = "This field should not be blank") @Size(min = 4, max = 20, message = "This parameter accept 4 ~ 20 characters") String description,
			ForumMainTopic forumMainTopics) {
		super();
		this.title = title;
		this.description = description;
		this.forumMainTopics = forumMainTopics;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
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

	public ForumMainTopic getForumMainTopics() {
		return forumMainTopics;
	}

	public void setForumMainTopics(ForumMainTopic forumMainTopics) {
		this.forumMainTopics = forumMainTopics;
	}
	
	
	
	
}
