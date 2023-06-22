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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name="topic_main")
public class ForumMainTopic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank(message = "This field should not be blank")
	@Size(min = 4 , max = 30 , message = "This parameter accept 4 ~ 30 characters")
	private String title;
	
	@NotBlank(message = "This field should not be blank")
	@Size(min = 4 , max = 100 , message = "This parameter accept 4 ~ 100 characters")
	private String description;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	//Maintopic -> Subtopic
	@OneToMany(mappedBy = "forumMainTopics" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ForumSubTopic> forumSubTopics;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	public ForumMainTopic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumMainTopic(String title, String description) {
		super();
		this.title = title;
		this.description = description;
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

	//One to Many
	public List<ForumSubTopic> getForumSubTopics() {
		return forumSubTopics;
	}

	public void setForumSubTopics(List<ForumSubTopic> forumSubTopics) {
		this.forumSubTopics = forumSubTopics;
	}
	
	
}
