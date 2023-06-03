package com.codingdojo.ForumsApp.models;

import java.util.Date;

import javax.persistence.CascadeType;
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

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "user_image")
public class ImageDataModel {
	
	//TO BE IMPLEMENTED IN THE FUTURUE
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String filePath;
	private String fileType;
	
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
	


	public ImageDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//userEntity not included on the database DB of ImageData
//	public ImageDataModel(String name, String filePath, String fileType, UserModel userEntity) {
//		super();
//		this.name = name;
//		this.filePath = filePath;
//		this.fileType = fileType;
//		this.userEntity = userEntity;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getFilePath() {
//		return filePath;
//	}
//
//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//	}
//
//	public String getFileType() {
//		return fileType;
//	}
//
//	public void setFileType(String fileType) {
//		this.fileType = fileType;
//	}
//
//	public Date getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public Date getUpdatedAt() {
//		return updatedAt;
//	}
//
//	public void setUpdatedAt(Date updatedAt) {
//		this.updatedAt = updatedAt;
//	}
//
//	public UserModel getUserEntity() {
//		return userEntity;
//	}
//
//	public void setUserEntity(UserModel userEntity) {
//		this.userEntity = userEntity;
//	}
//	
	
	
	
}
