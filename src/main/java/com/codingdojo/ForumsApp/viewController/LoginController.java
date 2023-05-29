package com.codingdojo.ForumsApp.viewController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.eclipse.jdt.internal.compiler.env.IModule.IService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.ForumsApp.auth.UserModel;
import com.codingdojo.ForumsApp.auth.UserValidator;
import com.codingdojo.ForumsApp.services.UserService;



@Controller
public class LoginController {
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserService userService;

	  //SPRING SECURITY VIA FORM
    @GetMapping("/registration")
    public String registerForm(@ModelAttribute("user") UserModel userModel) {
        return "registrationPage.jsp";
    }
    
 //Controller POST and Form:POST NEED to be the same URL as AUTHENTICATION @BEAN at WebSecurityConfig [.antMatchers()] otherwise, spring app will ignore any link and redirect to login page
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserModel userModel,
    		BindingResult result, Model modelView, HttpSession session , RedirectAttributes redirectAttributes) {
    	
    	//Register as USER	
    	userValidator.validate(userModel, result);
        if (result.hasErrors()) {
        	
            return "registrationPage.jsp";
        }else {
        	UserModel userNameChecker = userService.findByUsername(userModel.getUserName());
        	System.out.println("Username checker: " + userNameChecker);
        	
        	//if a username exist(not null) : display object of users data - Not saving the object of user instead just redirect page
        	//if a username does not exist yet == null - Register a User
        	if(userNameChecker !=null ) {
        		redirectAttributes.addFlashAttribute("registrationMessageError", "Error: Username already taken");
        		
        		return "redirect:/registration";
        	}else {
        		redirectAttributes.addFlashAttribute("registrationMessageSuccess", "Registration success!");
           	 	userService.saveWithUserRole(userModel);
   		     
           	 	return "redirect:/registration";
        	}
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
        		
        		return "redirect:/registration";
        	}else {
        	redirectAttributes.addFlashAttribute("registrationMessage", "Registration success!");
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
