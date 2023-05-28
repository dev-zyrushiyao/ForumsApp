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

	
//	@GetMapping("/dashboard")
//	public String dashboardPage(Model modelView, HttpSession session) {
//		UserModel userModel = userService.crud_idValidation((Long)session.getAttribute("userIdSession"));
//		session.setAttribute("userLoggedSession", userModel);
//		modelView.addAttribute("userModel" , userModel);
//		
//		
//		return "dashboard.jsp";
//	}
//	//FIXED:TEXTBOX/INFO VIEW BUG NOT UPDATING WITH NEW INFO BECAUSE OF SESSION saved as OBJECT
//	@GetMapping("/user/profile/{userName}")
//	public String profilePage(@PathVariable String userName, Model modelView , HttpSession session) {
//		//to load primary info of account (Username and Created at)
//		UserModel userModel = userService.findUserName(userName);
//		modelView.addAttribute("userLogged" , userModel);
//		
//
//		//to load the userData by user
//		//if the UserModel has not yet userData then load the DataBind form
//		UserModel userModelDataChecker = (UserModel) session.getAttribute("userLoggedSession");
//		modelView.addAttribute("userModelDataChecker", userModelDataChecker);
//		if((userModelDataChecker.getUserData()) == null) {
//			modelView.addAttribute("userDataForm" , new UserDataModel());
//		}
//		
//		return "profile.jsp";
//		
//		
//	}
//	
//	@PostMapping("/post/userdata")
//	public String createUserData(Model modelView, HttpSession session , RedirectAttributes redirectAttributes,
//		@Valid @ModelAttribute("userDataForm") UserDataModel userData , BindingResult result) {
//		
//		if(result.hasErrors()) {
//			UserModel userModel = (UserModel)session.getAttribute("userLoggedSession");
//			modelView.addAttribute("userLogged" , userModel);
//			System.out.println("Info saving fail!");
//			return "profile.jsp";
//		}else {
//			this.userDataService.createUserData(userData);
//			System.out.println("data saved!");
//			
//			return "redirect:/dashboard";
//			
//		}
//		
//	}
//	
//	//FIXED:TEXTBOX BUG NOT UPDATING WITH NEW INFO BECAUSE OF SESSION saved as OBJECT
//	@GetMapping("/update/user/id/{id}")
//	public String updatePage(@PathVariable Long id, Model modelView , HttpSession session) {
//		UserModel userModel = this.userService.findUserId(id);
//		modelView.addAttribute("userDataUpdateForm" , userModel.getUserData());
//		
//		//to use as URL(FORM) of UpdatePage
//		modelView.addAttribute("userLogged" , session.getAttribute("userLoggedSession"));
//		return "updateInfo.jsp";
//	}
//	
//	@PutMapping("update/user/info/{id}")
//	public String updateInfo(
//			@PathVariable Long id ,Model modelView, HttpSession session, RedirectAttributes redirectAttributes,
//			@Valid @ModelAttribute("userDataUpdateForm") UserDataModel userData , BindingResult result) {
//		
//		
//		//UserModel to use on if else and URL return for ELSE
//		if(result.hasErrors()) {
//			UserModel userModel = userService.findUserId(id);
//			modelView.addAttribute("userLogged" , userModel);
//			System.out.println("Update saving fail!");
//			return "updateInfo.jsp";
//		}else {
//			this.userDataService.updateUserData(userData);
//			
//			//return ROUTE URL
//			UserModel userModel = userService.findUserId(id);
//			
//			return "redirect:/user/profile/" + userModel.getUserName();	
//		
//		}
//		
//	}
	
	
}
