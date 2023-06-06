package com.codingdojo.ForumsApp.viewController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.ForumsApp.auth.UserModel;
import com.codingdojo.ForumsApp.auth.UserValidator;
import com.codingdojo.ForumsApp.models.UserDataModel;
import com.codingdojo.ForumsApp.services.UserDataService;
import com.codingdojo.ForumsApp.services.UserService;



@Controller
public class LoginController {
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDataService userDataService;

	  //SPRING SECURITY VIA FORM
    @GetMapping("/registration")
    public String registerForm(@ModelAttribute("user") UserModel userModel) {
        return "registrationPage.jsp";
    }
    
 //Controller POST and Form:POST NEED to be the same URL as AUTHENTICATION @BEAN at WebSecurityConfig [.antMatchers()] otherwise, spring app will ignore any link and redirect to login page
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserModel userModel,
    		BindingResult result, Model modelView, HttpSession session , RedirectAttributes redirectAttributes) {
    	
    	UserModel userNameChecker = userService.findByUsername(userModel.getUserName());
    	
    	//Register as USER	
    	userValidator.validate(userModel, result);
        if (result.hasErrors()) {
            return "registrationPage.jsp";
    	
        //if a username exist(not null) : display object of users data - Not saving the object of user instead just redirect page
        }else {
        	if(userModel.getUserName().contains(" ") || userModel.getPassword().contains(" ")) {
        		//if the username parameter contains white space (do not save the object / register the user)
        		redirectAttributes.addFlashAttribute("registrationMessageError", "Error: username/password contains white-space");
        		return "redirect:/registration";
        	}else if(userNameChecker != null ) {    		
        		//userNameChecker List[has an object] = Username already taken
        		redirectAttributes.addFlashAttribute("registrationMessageError", "Error: Username already taken");
            	return "redirect:/registration";	
        	}else {
                //if a username does not exist yet == null - Register a User
    			userService.saveWithUserRole(userModel);
        		return "redirect:/registration/complete/profile/" + userModel.getUserName();	
        	}
        	
        }
        		 
    }
        	
    	
    
    
	//Complete Registration Page (GET)
	@GetMapping("/registration/complete/profile/{userName}")
	public String completeProfilePage(RedirectAttributes redirectAttributes , @PathVariable String userName, Model modelView , HttpSession session) {
		//to load user account ID on JSP
		UserModel userModel = userService.findByUsername(userName);

			if(userModel == null) {
				//Security measure: if someone tries to access the url and the ${userName} is not found(null) then redirect to /registration 
				//to prevent unwanted saving of UserData from a nonexistent user
				redirectAttributes.addFlashAttribute("registrationMessageError", "Access Denied!");
				return "redirect:/registration";
			}else {
			//if the UserModel doesn't have yet a userData(null) then load the DataBind form / else User already completed profile -> redirect to /registration
				UserModel userModelDataChecker = userModel;
				modelView.addAttribute("userModelDataChecker", userModelDataChecker);
			//Data checker is also used as conditional in JSP
				if((userModelDataChecker.getUserData()) == null) {
					modelView.addAttribute("userDataForm" , new UserDataModel());
					modelView.addAttribute("currentUser", userModel);
					return "user_complete_profile.jsp";
				}else {
					redirectAttributes.addFlashAttribute("registrationMessageError", "Access Denied!");
					return "redirect:/registration";
				}
		}
			
	}
	

	//Complete Registration (POST)
	@PostMapping("/registration/post/userdata/{userName}")
	public String createUserData(@PathVariable String userName, Model modelView, RedirectAttributes redirectAttributes,
		@Valid @ModelAttribute("userDataForm") UserDataModel userData , BindingResult result) {
		
		UserModel currentUser = this.userService.findByUsername(userName);
		if(result.hasErrors()) {

			modelView.addAttribute("currentUser" , currentUser);
			System.out.println("Info saving fail!");
			return "user_complete_profile.jsp";
		}else {
			modelView.addAttribute("currentUser" , currentUser);
			redirectAttributes.addFlashAttribute("registrationMessageSuccess", "Profile Information Successfully saved");
			this.userDataService.createUserData(userData);
			
			return "redirect:/registration";
		}
	}
    
    //Admin registration route
    @GetMapping("/registration_admin")
    public String registerAdminForm(@ModelAttribute("user") UserModel userModel) {
        return "registrationPageAdmin.jsp";
    }
    
    @PostMapping("/registration_admin")
    public String registrationAdmin(@Valid @ModelAttribute("user") UserModel userModel,
    		BindingResult result, Model modelView, HttpSession session , RedirectAttributes redirectAttributes) {

    	//Register as ADMIN
    	userValidator.validate(userModel, result);
    	if (result.hasErrors()) {
    		
    		return "registrationPageAdmin.jsp";
        }else {
        	UserModel userNameChecker = userService.findByUsername(userModel.getUserName());
        	System.out.println("Username checker: " + userNameChecker);
        	
        	//if a username exist(not null) : display object of users data - Not saving the object of user instead just redirect page
        	//if a username does not exist yet == null - Register a User
        	if(userNameChecker !=null ) {
        		redirectAttributes.addFlashAttribute("registrationMessageError", "Error: Username already taken");
        		
        		return "redirect:/registration_admin";
        	}else {
	        	redirectAttributes.addFlashAttribute("registrationMessage", "Registration success! <br> <a href='/login'>GO Back</a>");
		        userService.saveUserWithAdminRole(userModel);
		        return "redirect:/registration_admin";
        	}
        }
        
    }
    
    
    //Invokes loadUserByUsername() from UserDetailsService class.
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        
    	//Spring Security redirect the client to the /login?error url
    	if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
    	
    	//Spring Security redirects them to /login?logout url
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginPage.jsp"; 
     
    }
    
}
