package com.codingdojo.ForumsApp.viewController;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.models.ThreadModel;
import com.codingdojo.ForumsApp.models.UserDataModel;
import com.codingdojo.ForumsApp.repository.ThreadRepo;
import com.codingdojo.ForumsApp.services.MainTopicService;
import com.codingdojo.ForumsApp.services.SubTopicService;
import com.codingdojo.ForumsApp.services.ThreadService;
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
	
	@Autowired
	private SubTopicService subTopicService;
	
	@Autowired
	private ThreadService threadService;
	
	//default for user
	@GetMapping(value = {"/", "/dashboard"})
	public String dashboardPage(Model modelView , Principal principal) {
		// 1 - TO load Username on the /dashboard page
        String username = principal.getName();
        modelView.addAttribute("currentUser", userService.findByUsername(username));
			
        List<ForumMainTopic> forumMainTopic = this.mainTopicService.findAllTopic();
        modelView.addAttribute("forumMainTopic", forumMainTopic);
        
			return "user_dashboard_maintopic.jsp";	
	}
	
	//view subtopics of maintopic
	@GetMapping("/forums/{mainTopic}")
	public String MainTopicSub(@PathVariable String mainTopic ,Model modelView , Principal principal) {
		// 1 - TO load Username on the /dashboard page
        String username = principal.getName();
        modelView.addAttribute("currentUser", userService.findByUsername(username));
		
        ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
        List<ForumSubTopic> subTopicsOfMain = this.subTopicService.findSubTopicByMainTopic(forumMainTopic);
        modelView.addAttribute("forumMainTopic", forumMainTopic);
        modelView.addAttribute("forumSubTopic", subTopicsOfMain);
        
        //Threads URL: /forums/${forumMainTopic.getTitle()}/${forumSubTopic.getTitle()
			return "user_dashboard_subtopic.jsp";	
	}
	
	//view threads of subtopic
	@GetMapping("/forums/{mainTopic}/{subTopic}")
	public String SubTopicThread(Model modelView , @PathVariable String mainTopic , @PathVariable String subTopic , 
			Principal principal) {
		String username = principal.getName();
        modelView.addAttribute("currentUser", userService.findByUsername(username));
        
        //Form: Button-New Thread link 
        ForumMainTopic mTopic = this.mainTopicService.findTitle(mainTopic);
        ForumSubTopic sTopic = this.subTopicService.findTitle(subTopic);
        modelView.addAttribute("mainTopic", mTopic);
        modelView.addAttribute("subTopic", sTopic);
        
        //Thread List
        List<ThreadModel> threadModel = this.threadService.findAllThread();
        Collections.reverse(threadModel);
        modelView.addAttribute("threadModel", threadModel);
		
		return "user_dashboard_thread.jsp";
	}
	
	@GetMapping("/forums/{mainTopic}/{subTopic}/new/thread")
	public String createThreadPage(Model modelView , ThreadModel threadModel , Principal principal ,
		  @PathVariable String mainTopic , @PathVariable String subTopic) {
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		//thread form
		modelView.addAttribute("threadForm", threadModel);
		
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		ForumSubTopic forumSubTopic = this.subTopicService.findTitle(subTopic);
		modelView.addAttribute("forumMainTopic", forumMainTopic);
		modelView.addAttribute("forumSubTopic", forumSubTopic);
		
		
		return "user_dashboard_thread_create.jsp";
	}
	
	//CHANGE: to change URL when posting. -> Direct to the thread content instead
	@PostMapping("/forums/{mainTopic}/{subTopic}/create/topic")
	public String postThread(RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("threadForm")ThreadModel threadModel, BindingResult result,
			@PathVariable String mainTopic , @PathVariable String subTopic){
		
		if(result.hasErrors()) {
			return "user_dashboard_thread_create.jsp";
		}else {
			
			this.threadService.createThread(threadModel);
			
			String returnURL = String.format("redirect:/forums/%s/%s", mainTopic, subTopic);
			return returnURL;
		}	
	}
		
	//admin access website after logging in (default user route dashboard , user role restricted)
	@GetMapping("/admin")
	public String adminPage(Principal principal, Model modelView , HttpSession session) {
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		return "admin_dashboard.jsp";
	}

	@GetMapping("/user/profile/{userName}")
	public String profilePage(Model modelView , @PathVariable String userName , UserModel userModel) {
		userModel = this.userService.findByUsername(userName);
		modelView.addAttribute("currentUser", userModel);
		return "user_profile.jsp";
	}	
	
	//always use ID pathvariable else it will save instead of update [ID of User]
	@GetMapping("/update/user/profile/id/{id}")
	public String updatePage(@PathVariable Long id, Model modelView , HttpSession session) {
		
		UserModel userModel = this.userService.findUserById(id);

		
		//if user already has userData ; else add a new user Data (disguse as userDataUpdateForm)
		if((userModel.getUserData()) != null) {
			System.out.println("user is NOT null");
			modelView.addAttribute("userDataUpdateForm" , userModel.getUserData());	
		}else {
			System.out.println("user is null");
			//Create new User Data disguise as update 
			modelView.addAttribute("userDataForm" , new UserDataModel());
		}
				
		//to use as URL(FORM) of UpdatePage
		modelView.addAttribute("currentUser" , userModel);
		return "user_updateInfo.jsp";
	}
	
	//always use ID pathvariable else it will save instead of update [ID Data of User]
	//${currentUser.getUserData().getId() <-FORM Action URL
	@PutMapping("/update/user/info/{id}") 
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
			return "redirect:/update/user/profile/id/" + userData.getUserAccount().getId();	
		}
	}
	
	@PutMapping("/update/user/add/info/{id}") 
	public String updateInfoDisguise(@PathVariable Long id, Model modelView, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("userDataForm") UserDataModel userData , BindingResult result) {
		
		if(result.hasErrors()) {
			modelView.addAttribute("currentUser" , userService.findUserById(id));
			System.out.println("Update saving fail!");
			return "user_updateInfo.jsp";
		}else {
			//updates the data
			redirectAttributes.addFlashAttribute("updateUserDataMessage", "Data Successfully updated");
			
			this.userDataService.updateUserData(userData);
			return "redirect:/update/user/profile/id/" + userData.getUserAccount().getId();	
		}
	}

	
	
	
	//-----------ADMIN - MAIN TOPIC-------------//
	@GetMapping("/admin/view/main/topic")
	public String viewMainTopic(Model modelView) {
		modelView.addAttribute("forumMainTopic", this.mainTopicService.findAllTopic());
		return "admin_viewMainTopic.jsp";
	}
	
	@GetMapping("/admin/create/main/topic")
	public String MainTopicPage(Model modelView , ForumMainTopic mainTopic) {
		modelView.addAttribute("mainTopicForm", mainTopic );
		
		return "admin_mainTopic.jsp";
	}
	
		//add a main/sub topic using GET 
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
	
	//need to use form:form otherwise the route and its return won't be recognize
	@DeleteMapping("/admin/delete/main/topic/id/{id}")
	public String deleteMainTopic(@PathVariable Long id) {
			
		this.mainTopicService.deleteTopic(id);	
		return "redirect:/admin/view/main/topic";
	}
	
	//-----------ADMIN - SUB TOPIC-------------//
	@GetMapping("/admin/view/{mainTopic}/subtopic")
	public String subtopicPage(@PathVariable String mainTopic , Model modelView) {
		
		//to used as Path variable for [Add sub Topic] route
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		modelView.addAttribute("forumMainTopic", forumMainTopic);
		
		// returns as list of all Subtopics of Maintopic(title)
		List<ForumSubTopic> mainSubTopics = forumMainTopic.getForumSubTopics();
		modelView.addAttribute("listOfSubTopics", mainSubTopics);
		
		return "admin_viewSubTopic.jsp";
	}
	
	@GetMapping("/admin/create/{mainTopic}/sub/topic")
	public String SubTopicPage(@PathVariable String mainTopic ,Model modelView) {
		
		modelView.addAttribute("subTopicForm", new ForumSubTopic());
		
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		modelView.addAttribute("MainTopicName", forumMainTopic);
		return "admin_subTopic.jsp";
	}
	
	@GetMapping("/admin/create/{mainTopic}/new/sub/topic")
	public String createSubTopic(@PathVariable String mainTopic ,Model modelView , RedirectAttributes redirectAttributes ,
			@Valid @ModelAttribute("subTopicForm")ForumSubTopic forumSubTopic , BindingResult result) {
		
		if(result.hasErrors()) {
			ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
			modelView.addAttribute("MainTopicName", forumMainTopic);
			return "admin_subTopic.jsp";
		}else {
			ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
			redirectAttributes.addFlashAttribute("subTopicMessage", "Subtopic Added!");
			this.subTopicService.createTopic(forumSubTopic);
			return "redirect:/admin/create/" + forumMainTopic.getTitle() + "/sub/topic";
		}
	}
	
	@GetMapping("/admin/update/sub/topic/id/{id}")
	public String updateSubTopicPage(@PathVariable Long id , Model modelView) {
		
		ForumSubTopic forumSubTopic = this.subTopicService.findId(id);
		modelView.addAttribute("updateSubTopicForm", forumSubTopic);
		return "admin_subTopicUpdate.jsp";
	}
	
	@PutMapping("/admin/update/info/sub/topic/id/{id}")
	public String updateSubTopic(@PathVariable Long id , Model modelView ,RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("updateSubTopicForm") ForumSubTopic forumSubTopic, BindingResult result) {
		
		if(result.hasErrors()) {
			return "admin_subTopicUpdate.jsp";
		}else {
			redirectAttributes.addFlashAttribute("updateTopicMessage", "SubTopic has been successfully updated!");
			this.subTopicService.updateTopic(forumSubTopic);
			return "redirect:/admin/update/sub/topic/id/" + forumSubTopic.getId();
		}
	}
	
	@DeleteMapping("/admin/delete/sub/topic/id/{id}")
	public String deleteSubTopic(@PathVariable Long id, ForumSubTopic forumSubTopic) {
		forumSubTopic = this.subTopicService.findId(id);
		this.subTopicService.deleteId(id);
	
		return "redirect:/admin/view/" + forumSubTopic.getForumMainTopics().getTitle() + "/subtopic";
	}
}
