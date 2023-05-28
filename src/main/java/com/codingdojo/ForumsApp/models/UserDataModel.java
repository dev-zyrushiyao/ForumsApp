package com.codingdojo.ForumsApp.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.codingdojo.ForumsApp.auth.UserModel;

@Entity
@Table(name = "user_information")
public class UserDataModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=3 , max=20)
	private String firstName;
	
	@Size(min=3 , max=20)
	private String lastName;
	
	@Size(min=3 , max=20)
	private String location;
	
	@Size(min=3 , max=50)
	private String programmingLanguage;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_account_id")
	private UserModel userAccount;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public UserDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDataModel(@Size(min = 3, max = 20) String firstName, @Size(min = 3, max = 20) String lastName,
			@Size(min = 3, max = 20) String location, @Size(min = 3, max = 50) String programmingLanguage,
			Date createdAt, Date updatedAt, UserModel userAccount) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.location = location;
		this.programmingLanguage = programmingLanguage;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userAccount = userAccount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
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
	public UserModel getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserModel userAccount) {
		this.userAccount = userAccount;
	}
	
}

	