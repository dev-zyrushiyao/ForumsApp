package com.codingdojo.ForumsApp.viewController;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.ForumsApp.auth.UserModel;
import com.codingdojo.ForumsApp.models.ForumMainTopic;
import com.codingdojo.ForumsApp.models.UserDataModel;
import com.codingdojo.ForumsApp.services.MainTopicService;
import com.codingdojo.ForumsApp.services.UserDataService;
import com.codingdojo.ForumsApp.services.UserService;




@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private MainTopicService mainTopicService;
	
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
	
	@GetMapping("/admin/create/main/topic")
	public String MainTopicPage(Model modelView , ForumMainTopic mainTopic) {
		modelView.addAttribute("mainTopicForm", mainTopic );
		
		return "admin_mainTopic.jsp";
	}
	
	//add a topic using GET
	@GetMapping("/admin/create/new/main/topic")
	public String createMainTopic(RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("mainTopicForm")ForumMainTopic forumMainTopic , BindingResult result) {
		
		if(result.hasErrors()) {
			return "admin_mainTopic.jsp";
		}else {
			redirectAttributes.addFlashAttribute("mainTopicMessage", "New Main Topic Added!");
			this.mainTopicService.createTopic(forumMainTopic);
			return "redirect:/admin/create/main/topic";
		}
	}
	
	@GetMapping("/admin/view/main/topic")
	public String viewMainTopic(Model modelView) {
		modelView.addAttribute("forumMainTopic", this.mainTopicService.findAllTopic());
		return "admin_viewMainTopic.jsp";
	}
	
	//TO ADD EDIT and DELETE Main TOPICS
	@GetMapping("/admin/update/main/topic/id/{id}")
	public String editMainTopicPage(@PathVariable Long id, Model modelView , ForumMainTopic forumMainTopic) {
		
	 forumMainTopic = this.mainTopicService.findTopicById(id);
		modelView.addAttribute("updateMainTopicForm", forumMainTopic);
		return "admin_mainTopicUpdate.jsp";
	}
	
	@PutMapping("/admin/update/info/main/topic/id/{id}")
	public String updateMainTopic(@PathVariable Long id , 
			@Valid @ModelAttribute("updateMainTopicForm")ForumMainTopic forumMainTopic , 
			BindingResult result , RedirectAttributes redirectAttributes) {
			
		if(result.hasErrors()) {
			return "admin_mainTopicUpdate.jsp";
		}else {
			redirectAttributes.addFlashAttribute("updateTopic" , "Topic has been successfully updated!");
			this.mainTopicService.updateTopic(forumMainTopic);
			return "redirect:/admin/update/main/topic/id/" + forumMainTopic.getId();
		}
	}
	
	//delete mapping for debugging
	//@DeleteMapping not working but working on anchor tag as GET mapping
	@GetMapping("/admin/delete/main/topic/id/{id}")
	public String deleteMainTopic(@PathVariable Long id) {
			
		this.mainTopicService.deleteTopic(id);
		return "redirect:/admin/view/main/topic";
	}
	
	
	
	
}
