package com.codingdojo.ForumsApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.ForumsApp.models.UserDataModel;
import com.codingdojo.ForumsApp.repository.UserDataRepo;

@Service
public class UserDataService {
	
	@Autowired
	UserDataRepo userDataRepo;
	
	public UserDataModel createUserData(UserDataModel userData) {
		return this.userDataRepo.save(userData);
	}
	
	public UserDataModel updateUserData(UserDataModel userData) {
		return this.userDataRepo.save(userData);
	}
	
	public UserDataModel findUserDataById(Long id) {
		return this.userDataRepo.findById(id).orElse(null);
	}
}
