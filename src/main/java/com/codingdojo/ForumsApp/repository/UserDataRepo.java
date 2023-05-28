package com.codingdojo.ForumsApp.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.ForumsApp.models.UserDataModel;

@Repository
public interface UserDataRepo extends CrudRepository<UserDataModel, Long> {
	

	
	
	
}
