package com.codingdojo.ForumsApp.viewController;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.ForumsApp.auth.UserModel;
import com.codingdojo.ForumsApp.models.UserDataModel;

import com.codingdojo.ForumsApp.services.UserDataService;
import com.codingdojo.ForumsApp.services.UserService;

import net.bytebuddy.asm.Advice.This;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDataService userDataService;
	
	//default for user
	@GetMapping(value = {"/", "/dashboard"})
	public String dashboardPage(Principal principal,  HttpServletRequest request, HttpSession session, Model modelView) {
		// 1 - TO load Username on the /dashboard page
        String username = principal.getName();
        modelView.addAttribute("currentUser", userService.findByUsername(username));
			
			return "user_dashboard.jsp";	
	}
		
	//admin access website after logging in (default user route dashboard , user role restricted)
	@GetMapping("/admin")
	public String adminPage(Principal principal, Model modelView , HttpSession session) {
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		return "admin_dashboard.jsp";
	}

	//Username as path
	@GetMapping("/user/profile/{userName}")
	public String profilePage(@PathVariable String userName, Model modelView , HttpSession session) {
		//to load primary info of account (Username and Created at via JSP)
		UserModel userModel = userService.findByUsername(userName);
		modelView.addAttribute("currentUser" , userModel);
		

		//to load the userData form (data bind) by user
		//if the UserModel has not yet userData then load the DataBind form 
		//Data checker is also used as conditional in JSP
		UserModel userModelDataChecker = userModel;
		modelView.addAttribute("userModelDataChecker", userModelDataChecker);
		if((userModelDataChecker.getUserData()) == null) {
			modelView.addAttribute("userDataForm" , new UserDataModel());
		}
		
		return "user_profile.jsp";
	}

	@PostMapping("/post/userdata/{userName}")
	public String createUserData(@PathVariable String userName, Model modelView, RedirectAttributes redirectAttributes,
		@Valid @ModelAttribute("userDataForm") UserDataModel userData , BindingResult result) {
		
		UserModel currentUser = this.userService.findByUsername(userName);
		
		if(result.hasErrors()) {

			modelView.addAttribute("currentUser" , currentUser);
			System.out.println("Info saving fail!");
			return "user_profile.jsp";
		}else {
			modelView.addAttribute("currentUser" , currentUser);
			redirectAttributes.addFlashAttribute("userDataMessage", "Profile Information Successfully saved");
			this.userDataService.createUserData(userData);
			
			return "redirect:/user/profile/" + currentUser.getUserName();
		}
	}
	
	//always use ID pathvariable else it will save instead of update [ID of User]
	@GetMapping("/update/user/id/{id}")
	public String updatePage(@PathVariable Long id, Model modelView , HttpSession session) {
		
		UserModel userModel = this.userService.findUserById(id);
		modelView.addAttribute("userDataUpdateForm" , userModel.getUserData());
		
		//to use as URL(FORM) of UpdatePage
		modelView.addAttribute("currentUser" , userModel);
		return "user_updateInfo.jsp";
	}
	
	//always use ID pathvariable else it will save instead of update [ID Data of User]
	//${currentUser.getUserData().getId() <-FORM Action URL
	@PutMapping("update/user/info/{id}") 
	public String updateInfo(@PathVariable Long id, Model modelView, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("userDataUpdateForm") UserDataModel userData , BindingResult result) {
		
		if(result.hasErrors()) {
			modelView.addAttribute("currentUser" , userService.findUserById(id));
			System.out.println("Update saving fail!");
			return "user_updateInfo.jsp";
		}else {
			//updates the data
			redirectAttributes.addFlashAttribute("updateUserDataMessage", "Data Successfully updated");

			this.userDataService.updateUserData(userData);
			return "redirect:/update/user/id/" + userData.getUserAccount().getId();	
		
		}
		
	}
	
	
}
