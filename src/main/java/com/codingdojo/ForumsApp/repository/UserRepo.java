package com.codingdojo.ForumsApp.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.auth.UserModel;




@Repository
public interface UserRepo extends CrudRepository<UserModel, Long> {
	
	//For Spring Security
	UserModel findByUserName(String userName);
	
	
	

	
}
