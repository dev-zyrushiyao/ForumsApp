package com.codingdojo.ForumsApp.services;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codingdojo.ForumsApp.auth.UserModel;
import com.codingdojo.ForumsApp.repository.UserRepo;
import com.codingdojo.ForumsApp.repository.UserRoleRepo;




@Service
public class UserService {
	
	@Autowired
	private UserRoleRepo userRoleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	public UserModel crud_registerAccount(UserModel userModel , RedirectAttributes redirectAttributes) {
//		
//		if(!userModel.getPassword().equals(userModel.getConfirmPassword())) {
//			System.out.println("System Message: Password and confirm password did not match");
//			redirectAttributes.addFlashAttribute("passwordError", "Error: password and confirm password did not match");
//			
//			return null;
//		}else if(crud_emailValidation(userModel.getEmail()) != null ) {
//			System.out.println("Searched " + userModel.getEmail() + ": " + crud_emailValidation(userModel.getEmail()));
//			System.out.println("System Message: email is already taken");
//			redirectAttributes.addFlashAttribute("emailError", "Error: email is already taken" );
//
//			return null;
//		}else {
//			System.out.println("System Message: Registration Success!");
//			String hashed = BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt());
//			redirectAttributes.addFlashAttribute("registrationSuccess", "Registration is successful!");
//			userModel.setPassword(hashed);
//			
//			System.out.println(userModel.getPassword());
//			System.out.println(userModel.getPassword().length());
//			
//			return userRepo.save(userModel);
//		}
//		// Validation -registration end
//	}
	

	
	public UserModel findUserById(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	 // 1. Saves a client with USER role
    public void saveWithUserRole(UserModel user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoleRepo.findByName("ROLE_USER"));
        userRepo.save(user);
    }
     
     // 2. Saves a client with a ADMIN role
    public void saveUserWithAdminRole(UserModel user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
    }    
    
    // 3. finds a user by their userName
    public UserModel findByUsername(String userName) {
        return userRepo.findByUserName(userName);
    }
    
	

	
}
