package com.codingdojo.ForumsApp.auth;

import java.nio.MappedByteBuffer;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.codingdojo.ForumsApp.models.CommentModel;
import com.codingdojo.ForumsApp.models.ThreadModel;
import com.codingdojo.ForumsApp.models.UserDataModel;

@Entity
@Table(name = "user_account")
public class UserModel {
		
		//Before you create an account
		//Inject command below on MySQL WorkBench ROLE TABLE (double click schema first) or make API services to add Roles via POSTMAN 
		//INSERT INTO roles (name) VALUES ('ROLE_USER');
		//INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
	
	  	@Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
	  	
	  	@Size(min=4, max = 40, message = "Username parameter accept 4 ~ 40 characters" )
	    private String userName;
	    
	  	//removed max = 60 because of Bcrypt
	  	@Size(min=4 , message="Password parameter accept 4 ~ 40 characters")
	    private String password;
	    
	    @Transient
	    private String passwordConfirmation;
	    
	    //UserModel -> UserData
	    @OneToOne(mappedBy = "userAccount" , cascade = CascadeType.ALL,
	    		fetch = FetchType.LAZY)
	    private UserDataModel userData;
	    
	    //UserModel -> Thread
	    @OneToMany(mappedBy = "userThread" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	    private List<ThreadModel> thread;
	    
	    //UserModel -> Thread comment
	    @OneToMany(mappedBy = "userAccount" , fetch = FetchType.LAZY)
	    private List<CommentModel> topicComment;
	    
	    //User <-> Roles
	    @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(
	        name = "users_roles", 
	        joinColumns = @JoinColumn(name = "user_id"), 
	        inverseJoinColumns = @JoinColumn(name = "role_id"))
	    private List<UserRoleModel> roles;
	    
	    @Column(updatable=false)
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date createdAt;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date updatedAt;
	    
	    @PrePersist
	    protected void onCreate(){
	        this.createdAt = new Date();
	    }
	    @PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    }
	    
	
	    
	    public UserModel() {
	    }
		public UserModel(String userName, String password, String passwordConfirmation) {
			super();
			this.userName = userName;
			this.password = password;
			this.passwordConfirmation = passwordConfirmation;
		}
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		//username instead of email because its default of spring security
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPasswordConfirmation() {
			return passwordConfirmation;
		}
		public void setPasswordConfirmation(String passwordConfirmation) {
			this.passwordConfirmation = passwordConfirmation;
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
		
		//One to One
		public UserDataModel getUserData() {
			return userData;
		}
		public void setUserData(UserDataModel userData) {
			this.userData = userData;
		}
		
		//One to Many
		public List<ThreadModel> getThread() {
			return thread;
		}
		public void setThread(List<ThreadModel> thread) {
			this.thread = thread;
		}
		
		//One to Many
		public List<CommentModel> getTopicComment() {
			return topicComment;
		}
		public void setTopicComment(List<CommentModel> topicComment) {
			this.topicComment = topicComment;
		}
		
		//Many to Many
		public List<UserRoleModel> getRoles() {
			return roles;
		}
		
		public void setRoles(List<UserRoleModel> roles) {
			this.roles = roles;
		}
	    
	
}
