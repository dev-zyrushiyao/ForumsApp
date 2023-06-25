package com.codingdojo.ForumsApp.viewController;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import com.codingdojo.ForumsApp.auth.UserRoleModel;
import com.codingdojo.ForumsApp.models.CommentModel;
import com.codingdojo.ForumsApp.models.ForumMainTopic;
import com.codingdojo.ForumsApp.models.ForumSubTopic;
import com.codingdojo.ForumsApp.models.ThreadModel;
import com.codingdojo.ForumsApp.models.UserDataModel;
import com.codingdojo.ForumsApp.services.CommentService;
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
	
	@Autowired
	private CommentService commentService;
	
	//default for user
	@GetMapping(value = {"/", "/dashboard" , "/forums"})
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
	
	//Post thread
	@PostMapping("/forums/{mainTopic}/{subTopic}/create/topic")
	public String postThread(Model modelView,RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("threadForm")ThreadModel threadModel, BindingResult result,
			@PathVariable String mainTopic , @PathVariable String subTopic){
		
		if(result.hasErrors()) {
			
			ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
			ForumSubTopic forumSubTopic = this.subTopicService.findTitle(subTopic);
			modelView.addAttribute("forumMainTopic", forumMainTopic);
			modelView.addAttribute("forumSubTopic", forumSubTopic);
			
			return "user_dashboard_thread_create.jsp";
		}else {
			//redirect to thread content after posting
			this.threadService.createThread(threadModel);
			String returnURL = String.format("forums/%s/%s/thread/%d", mainTopic, subTopic , threadModel.getId());
			return "redirect:/" + returnURL;
		
		}	
	}
	
	//PAGINATION SUBTOPICS THREAD
	//Sample Route: localhost:8080/forums/Java/Spring Boot/page/0
	@GetMapping("/forums/{mainTopic}/{subTopic}/page/{pageTarget}")
	public String paginationThread(Model modelView , Principal principal,
			@PathVariable String mainTopic , 
			@PathVariable String subTopic, 
			@PathVariable int pageTarget) {
		// Render User info
		String username = principal.getName();
        modelView.addAttribute("currentUser", userService.findByUsername(username));
		
        //Form: Button-New Thread link 
        ForumMainTopic mTopic = this.mainTopicService.findTitle(mainTopic);
        ForumSubTopic sTopic = this.subTopicService.findTitle(subTopic);
        modelView.addAttribute("mainTopic", mTopic);
        modelView.addAttribute("subTopic", sTopic);
        
        // System.out.println(sTopic.getThreadTopics().size());
        // sTopic.getThreadTopics().size();
        
        //Thread List
        List<ThreadModel> threadFinder = this.threadService.findByForumSubTopic(sTopic);
        Collections.reverse(threadFinder);
        modelView.addAttribute("threadFinder", threadFinder);
		       
		// default data size per page PAGINATION
		int pageSize = 10; 
		PageRequest pageRequest = PageRequest.of(pageTarget, pageSize);
		
		//To Paginate All data from the model
		//Page<ThreadModel> paginateThread = this.threadService.threadPagination(pageRequest);
		
		//ISSUE:BY DEFAULT PAGINATION WILL MAKE ALL THE THREADS FROM DIFFERENT TOPICS INTO ONE LIST [ALL DATA FROM THREAD MODEL] -> AS PAGE
		//ISSUE: SOLVED CONVERTING A SEPARATE LIST OF (SUB TOPIC THREAD)[ONE TO MANY] -> TO A PAGE
			//Source Fix #1: https://stackoverflow.com/a/37771947
			//Source Fix #2: https://stackoverflow.com/a/60522317
			//Source Fix #3: https://www.baeldung.com/spring-data-jpa-convert-list-page
		int start = (int) pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), threadFinder.size());
		Page<ThreadModel> threadPages = new PageImpl<ThreadModel>(threadFinder.subList(start, end), pageRequest, threadFinder.size());
		modelView.addAttribute("threadPages", threadPages);
		
		System.out.println((threadPages.getNumber() +1) + " " + threadPages.getTotalPages());
		
		System.out.println(threadPages.getNumber()+1 == threadPages.getTotalPages());
		System.out.println((threadPages.getNumber()+1) != threadPages.getTotalPages());
//		System.out.println()
		
//		System.out.println("Total Elements: " + threadPages.getTotalElements());
//		System.out.println("Current Page: " + threadPages.getNumber());
//		System.out.println("Last Page: " + threadPages.getTotalPages());
//		System.out.println("Elements Per Page: " + threadPages.getNumberOfElements());
		
		//pagination button
		modelView.addAttribute("listOfThread" , threadPages.getContent()); //.getContent returns the data as List of Iteration of JSP ; w/o it spring boot will throw an error JspTagException:[Don't know how to iterate over supplied "items" in &lt;forEach&gt;]
		modelView.addAttribute("totalPages", threadPages.getTotalPages());
		
		//To check the content will be shown as Pagination
//		for(ThreadModel threads : threadPages) {
//			System.out.println("page " + pageTarget + ": " + threads.getTitle());
//		}
		
		
		// Date Formatting
		
		
//		String datePatternThread = "dd/MMM/yyyy h:mm a";
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePatternThread);
//		
//		String datePosted = simpleDateFormat.format(threadPages.getContent().getUserThrea);
//		modelView.addAttribute("datePosted", datePosted);
		
		
        
		return "user_dashboard_thread.jsp";
	}
	
	//Thread content
	@GetMapping("/forums/{mainTopic}/{subTopic}/thread/{id}")
	public String threadPage(HttpSession session , @PathVariable String mainTopic, @PathVariable String subTopic, @PathVariable Long id ,
			Model modelView, Principal principal ) {
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		ThreadModel threadModel = this.threadService.findThreadById(id);
		modelView.addAttribute("threadModel", threadModel);
		
		//if a user tries to access unexisted ID thread-> redirects back to SubTopics Thread 
		//EXAMPLE: (/forums/Python/AI Development/thread/{unexistedRandomNumberId} -> redirect back to /forums/Python/AI Development
		if(threadModel == null) 
		{
			String returnURL = String.format("forums/%s/%s/page/0", mainTopic , subTopic);
			return "redirect:/" + returnURL;
		}
		
		//view all replies to a thread / add replies origin(username)
		List<CommentModel> threadReplies = this.commentService.findCommentsOnThread(threadModel);
		modelView.addAttribute("threadReplies", threadReplies);
		//modelView.addAttribute("dateCommented", threadReplies)
		
		// Date Formatting
		Date threadCreated = threadModel.getCreatedAt();
		
		String datePatternThread = "dd/MMM/yyyy h:mm a";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePatternThread);
		
		String datePosted = simpleDateFormat.format(threadCreated);
		modelView.addAttribute("datePosted", datePosted);
		
		// Date Formatt 
//		UserModel userModel = this.userService.findUserById(1L);
//    	DateFormat dateFormat = DateFormat.getDateInstance();
//    	System.out.println("Created at : " +  userModel.getCreatedAt());
//    	System.out.println("Date Format : " + dateFormat.format(userModel.getCreatedAt()));
//    	System.out.println("Date Format with substring:" + dateFormat.format(userModel.getCreatedAt()).substring(3, 10)); //substring param (int start , int end) 
//    	System.out.println("Date Format with time:" + DateFormat.getTimeInstance().format(userModel.getCreatedAt()));


		//reply form
		CommentModel commentModel = new CommentModel();
		modelView.addAttribute("threadReplyForm" , commentModel);
		
		//reply form URL POST
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		ForumSubTopic forumSubTopic = this.subTopicService.findTitle(subTopic);
		modelView.addAttribute("forumMainTopic", forumMainTopic);
		modelView.addAttribute("forumSubTopic", forumSubTopic);
		
		//used session for threadReply form post to avoid unintentional update a reply of any user.
		session.setAttribute("threadIdSession", id);
		
		return "thread_content.jsp";
	}
	
	//thread reply (no ID on route to prevent updating a comment when posted a reply)
	@PostMapping("/forums/{mainTopic}/{subTopic}/thread/new/reply")
	public String threadReply(HttpSession session , Model modelView , Principal principal , RedirectAttributes redirectAttributes , 
			@Valid @ModelAttribute("threadReplyForm")CommentModel commentModel , BindingResult result,
			@PathVariable String mainTopic,
			@PathVariable String subTopic) {
		
		if(result.hasErrors()) {
			String username = principal.getName();
			modelView.addAttribute("currentUser", userService.findByUsername(username));
			
			//Session thread ID to reload comments from the thread once it renders the JSP again
			ThreadModel threadModel = this.threadService.findThreadById((Long)session.getAttribute("threadIdSession"));
			modelView.addAttribute("threadModel", threadModel);
			
			//view all replies to a thread
			List<CommentModel> threadReplies = this.commentService.findCommentsOnThread(threadModel);
			modelView.addAttribute("threadReplies", threadReplies);
			
			//display threadstarter - role 
			List<UserRoleModel> userRole = userService.findByUsername(threadModel.getUserThread().getUserName()).getRoles();
			modelView.addAttribute("userRole", userRole);
			
			//reply form URL POST
			ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
			ForumSubTopic forumSubTopic = this.subTopicService.findTitle(subTopic);
			modelView.addAttribute("forumMainTopic", forumMainTopic);
			modelView.addAttribute("forumSubTopic", forumSubTopic);
			return "thread_content.jsp";
		}else {
			this.commentService.createComment(commentModel);
			String returnURL = String.format("forums/%s/%s/thread/%d" , mainTopic , subTopic , (Long)session.getAttribute("threadIdSession"));
			return "redirect:/" + returnURL;
		}	
	}
	
	@GetMapping("/admin/forums/{mainTopic}/{subTopic}/thread/{id}/update/reply/{replyId}")
	public String updateReplyPage(Principal principal , Model modelView , CommentModel commentModel , 
			@PathVariable String mainTopic,
			@PathVariable String subTopic,
			@PathVariable Long id,
			@PathVariable Long replyId ) {
		
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		commentModel = this.commentService.findReplyId(replyId);
		modelView.addAttribute("updateReplyForm", commentModel);
		modelView.addAttribute("commentModel", this.commentService.findReplyId(replyId));
		
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		ForumSubTopic forumSubTopic = this.subTopicService.findTitle(subTopic);
		modelView.addAttribute("forumMainTopic", forumMainTopic);
		modelView.addAttribute("forumSubTopic", forumSubTopic);
		
		ThreadModel threadModel = this.threadService.findThreadById(id);
		modelView.addAttribute("threadModel", threadModel);
		
		return "thread_updateReply.jsp";
	}
	
	@PutMapping("/admin/forums/{mainTopic}/{subTopic}/thread/{threadId}/update/info/reply/{id}")
	public String updateReply(Principal principal , Model modelView , @Valid @ModelAttribute("updateReplyForm") CommentModel commentModel , BindingResult result , 
			@PathVariable String mainTopic,
			@PathVariable String subTopic,
			@PathVariable Long threadId ,
			@PathVariable Long id ) {
		
		if(result.hasErrors()) {
			String username = principal.getName();
			modelView.addAttribute("currentUser", userService.findByUsername(username));
			
//			commentModel = this.commentService.findReplyId(id);
//			modelView.addAttribute("updateReplyForm", commentModel);
			modelView.addAttribute("commentModel", this.commentService.findReplyId(id));
			
			ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
			ForumSubTopic forumSubTopic = this.subTopicService.findTitle(subTopic);
			modelView.addAttribute("forumMainTopic", forumMainTopic);
			modelView.addAttribute("forumSubTopic", forumSubTopic);
		
			ThreadModel threadModel = this.threadService.findThreadById(threadId);
			modelView.addAttribute("threadModel", threadModel);

			return "thread_updateReply.jsp";
		}else {
			this.commentService.updateComment(commentModel);
			String returnURL = String.format("forums/%s/%s/thread/%d" , mainTopic , subTopic , threadId);
			return "redirect:/" + returnURL;
		}
		
	}
	
	//delete replies
	@DeleteMapping("/admin/forums/{mainTopic}/{subTopic}/thread/{id}/delete/reply/{replyId}")
	public String deleteReply(
			@PathVariable String mainTopic,
			@PathVariable String subTopic,
			@PathVariable Long id,
			@PathVariable Long replyId) {
		
		this.commentService.deleteCommentId(replyId);
		String returnURL = String.format("forums/%s/%s/thread/%d" , mainTopic , subTopic , id);
		return "redirect:/" + returnURL;
	}
	
	//Thread content update page
	@GetMapping("/admin/forums/update/thread/id/{id}")
	public String updateThreadPage(Model modelView , @PathVariable Long id , Principal principal) {
		ThreadModel threadModel = this.threadService.findThreadById(id);
		modelView.addAttribute("threadUpdateForm", threadModel);
		
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		//go back link to Edit - back to thread viewing
		ForumMainTopic forumMainTopic = threadModel.getForumSubTopic().getForumMainTopics();
		ForumSubTopic forumSubTopic = threadModel.getForumSubTopic();
		modelView.addAttribute("ForumMainTopic", forumMainTopic);
		modelView.addAttribute("forumSubTopic", forumSubTopic);
		
		return "user_dashboard_thread_update.jsp";
	}
	
	//Thread content update 
	@PutMapping("/admin/forums/update/thread/info/id/{id}")
	public String updateThread(Model modelView, @PathVariable Long id , 
		@Valid @ModelAttribute("threadUpdateForm")ThreadModel threadModel , BindingResult result) {
		
		if(result.hasErrors()) {
//			threadModel = this.threadService.findThreadById(id);
//			modelView.addAttribute("threadUpdateForm", threadModel);
//			System.out.println("error thread title update");
			return "user_dashboard_thread_update.jsp";
		}else {
			this.threadService.updateThread(threadModel);
			
			ForumMainTopic mainTopic = threadModel.getForumSubTopic().getForumMainTopics();
			ForumSubTopic subTopic = threadModel.getForumSubTopic();
			
			//redirects back to viewing the thread
			String returnURL = String.format("forums/%s/%s/thread/%d", mainTopic.getTitle() , subTopic.getTitle() , threadModel.getId());
			return "redirect:/" + returnURL ;
		}
	}
	
	//Delete Thread + comments
	@DeleteMapping("/admin/forums/delete/thread/id/{id}")
	public String deleteThread(@PathVariable Long id) {
		
		ThreadModel threadModel = this.threadService.findThreadById(id);
		ForumMainTopic mainTopic = threadModel.getForumSubTopic().getForumMainTopics();
		ForumSubTopic subTopic = threadModel.getForumSubTopic();
		
		String mTopic = mainTopic.getTitle();
		String sTopic = subTopic.getTitle();
		
		//redirect URL
		String returnURL = String.format("forums/%s/%s/page/0", mTopic , sTopic);
		
		//delete thread
		this.threadService.deleteThreadById(id);
		
		return "redirect:/" + returnURL;
	}
	
	//PROFILE VIEWING
	@GetMapping("/user/profile/{userNameProfile}")
	public String profilePage(Model modelView , Principal principal , 
			@PathVariable String userNameProfile , UserModel userModel) {
		
		//If you are the currentUser show Update profile button else display nothing (JSP)
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		// Date Formatting
		Date accountCreated = userService.findByUsername(username).getCreatedAt();
		String datePatternJoined = "MMMM yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePatternJoined);
		
		String dateJoined = simpleDateFormat.format(accountCreated);
		modelView.addAttribute("dateJoined", dateJoined);
				
		// Rendering to JSP
		userModel = this.userService.findByUsername(userNameProfile);
		modelView.addAttribute("userModel", userModel);
		
		
		//user thread (created thread)
		List<ThreadModel> userThread = userModel.getThread();
		modelView.addAttribute("userThread", userThread);
		
		//user comments (comments on different threads)
		List<CommentModel> userComments = userModel.getTopicComment();
		Collections.reverse(userComments);
		modelView.addAttribute("userComments", userComments);
		
		return "user_profile.jsp";
	}	
	
	//always use ID pathvariable else it will save instead of update [ID of User]
	@GetMapping("/update/user/profile/id/{id}")
	public String updatePage(Principal principal , @PathVariable Long id, Model modelView , HttpSession session) {
		
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		UserModel userModel = this.userService.findUserById(id);
	
	//if userLogged in is NOT equal to UserModel(to Edit a data) redirect back to userLogged profile
	if(!username.equals(userModel.getUserName())) {
			return "redirect:/user/profile/" + userService.findByUsername(username).getUserName();
	}else {
		//if user already has userData ; else add a new user Data (disguse as userDataUpdateForm)
			if((userModel.getUserData()) != null) {
				modelView.addAttribute("userDataUpdateForm" , userModel.getUserData());	
			}else {
				//Create new User Data disguise as update 
				modelView.addAttribute("userDataForm" , new UserDataModel());
			}
				
			//to use as URL(FORM) of UpdatePage (for else-IF and else-ELSE)
			modelView.addAttribute("currentUser" , userModel);
			return "user_updateInfo.jsp";
		}
	}
	
	//always use ID pathvariable else it will save instead of update [ID Data of User]
	//${currentUser.getUserData().getId() <-FORM Action URL
	@PutMapping("/update/user/info/{id}") 
	public String updateInfo(Principal principal, @PathVariable Long id, Model modelView, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("userDataUpdateForm") UserDataModel userData , BindingResult result) {
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));

		if(result.hasErrors()) {
			modelView.addAttribute("currentUser" , userService.findUserById(id));
			return "user_updateInfo.jsp";
		}else {
			
			this.userDataService.updateUserData(userData);
			return "redirect:/user/profile/" + userData.getUserAccount().getUserName();	
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
			
			this.userDataService.updateUserData(userData);
			return "redirect:/user/profile/" + userData.getUserAccount().getUserName();	
		}
	}

	//-----------ADMIN - Dashboard-------------//
	//admin access website after logging in (default user route dashboard , user role restricted)
	@GetMapping("/admin")
	public String adminPage(Principal principal, Model modelView , HttpSession session) {
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
			
		return "admin_dashboard.jsp";
	}

	
	//-----------ADMIN - MAIN TOPIC-------------//
	@GetMapping("/admin/view/main/topic")
	public String viewMainTopic(Principal principal, Model modelView) {
		
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		modelView.addAttribute("forumMainTopic", this.mainTopicService.findAllTopic());
		
		return "admin_viewMainTopic.jsp";
//		return "pf_admin_viewMainTopic.jsp";
	}
	
	@GetMapping("/admin/create/main/topic")
	public String MainTopicPage(Principal principal, Model modelView , ForumMainTopic forumMainTopic) {
		modelView.addAttribute("mainTopicForm", forumMainTopic );
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		return "admin_mainTopic.jsp";
	}
	
	
	@PostMapping("/admin/create/new/main/topic")
	public String createMainTopic(RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("mainTopicForm")ForumMainTopic forumMainTopic , BindingResult result, Model modelView, Principal principal) {
		
		if(result.hasErrors()) {
			modelView.addAttribute("mainTopicForm", forumMainTopic );
			String username = principal.getName();
			modelView.addAttribute("currentUser", userService.findByUsername(username));
			return "admin_mainTopic.jsp";
		}else {
			ForumMainTopic mainTopicDataChecker = this.mainTopicService.findTitle(forumMainTopic.getTitle());
			if(mainTopicDataChecker != null) {
				redirectAttributes.addFlashAttribute("mainTopicErrorMessage", "ERROR: MainTopic already exist");
//				System.out.println("ERROR: MainTopic already exist");
				return "redirect:/admin/create/main/topic";
			}else {
				this.mainTopicService.createTopic(forumMainTopic);	
				redirectAttributes.addFlashAttribute("mainTopicMessage", "New Main Topic Added!");
				return "redirect:/";
			}
			
		}
	}
	
	@GetMapping("/admin/update/main/topic/id/{id}")
	public String editMainTopicPage(@PathVariable Long id, Model modelView , ForumMainTopic forumMainTopic, Principal principal) {
		
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		forumMainTopic = this.mainTopicService.findTopicById(id);
		modelView.addAttribute("updateMainTopicForm", forumMainTopic);
		return "admin_mainTopicUpdate.jsp";
	}
	
	@PutMapping("/admin/update/info/main/topic/id/{id}")
	public String updateMainTopic(Principal principal ,Model modelView ,@PathVariable Long id , 
			@Valid @ModelAttribute("updateMainTopicForm")ForumMainTopic forumMainTopic , 
			BindingResult result , RedirectAttributes redirectAttributes) {
			
		if(result.hasErrors()) {
			String username = principal.getName();
			modelView.addAttribute("currentUser", userService.findByUsername(username));
			
			return "admin_mainTopicUpdate.jsp";
		}else {
			redirectAttributes.addFlashAttribute("updateTopic" , "Topic has been successfully updated!");
			this.mainTopicService.updateTopic(forumMainTopic);
			return "redirect:/admin/view/main/topic/";	
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
	public String subtopicPage(@PathVariable String mainTopic , Model modelView, Principal principal) {
		
		// Renders the currentUser object
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		//to used as Path variable for [Add sub Topic] route
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		modelView.addAttribute("forumMainTopic", forumMainTopic);
		
		// returns as list of all Subtopics of Maintopic(title)
		List<ForumSubTopic> mainSubTopics = forumMainTopic.getForumSubTopics();
		modelView.addAttribute("listOfSubTopics", mainSubTopics);
		
		return "admin_viewSubTopic.jsp";
	}
	
	@GetMapping("/admin/create/{mainTopic}/sub/topic")
	public String SubTopicPage(@PathVariable String mainTopic, Model modelView, Principal principal) {
		
		// Renders the currentUser object
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		modelView.addAttribute("subTopicForm", new ForumSubTopic());
		
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		modelView.addAttribute("MainTopicName", forumMainTopic);
		return "admin_subTopic.jsp";
	}
	
	@PostMapping("/admin/create/{mainTopic}/new/sub/topic")
	public String createSubTopic(@PathVariable String mainTopic ,Model modelView , RedirectAttributes redirectAttributes ,
			@Valid @ModelAttribute("subTopicForm")ForumSubTopic forumSubTopic , BindingResult result) {
		
		//forumMainTopic for return url
		ForumMainTopic forumMainTopic = this.mainTopicService.findTitle(mainTopic);
		
		if(result.hasErrors()) {
			modelView.addAttribute("MainTopicName", forumMainTopic);
			return "admin_subTopic.jsp";
		}else {
			ForumSubTopic subTopicDataChecker = this.subTopicService.findTitle(forumSubTopic.getTitle());
			if(subTopicDataChecker != null) {
				redirectAttributes.addFlashAttribute("subTopicErrorMessage", "ERROR: SubTopic already exist");
//				System.out.println("ERROR: SubTopic already exist");
				return "redirect:/admin/create/" + forumMainTopic.getTitle() + "/sub/topic";
			}else {
				redirectAttributes.addFlashAttribute("subTopicMessage", "Subtopic Added!");
				this.subTopicService.createTopic(forumSubTopic);
				//return "redirect:/admin/create/" + forumMainTopic.getTitle() + "/sub/topic";
				return "redirect:/forums/"+forumMainTopic.getTitle();
			}
		}
	}
	
	@GetMapping("/admin/update/sub/topic/id/{id}")
	public String updateSubTopicPage(@PathVariable Long id , Model modelView, Principal principal) {
		
		// Renders the currentUser object
		String username = principal.getName();
		modelView.addAttribute("currentUser", userService.findByUsername(username));
		
		ForumSubTopic forumSubTopic = this.subTopicService.findId(id);
		modelView.addAttribute("updateSubTopicForm", forumSubTopic);
		return "admin_subTopicUpdate.jsp";
	}
	
	@PutMapping("/admin/update/info/sub/topic/id/{id}")
	public String updateSubTopic(@PathVariable Long id , Model modelView ,RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("updateSubTopicForm") ForumSubTopic forumSubTopic, BindingResult result) {
		
		System.out.println(forumSubTopic.getForumMainTopics().getTitle());
		
		if(result.hasErrors()) {
			return "admin_subTopicUpdate.jsp";
		}else {
			redirectAttributes.addFlashAttribute("updateTopicMessage", "SubTopic has been successfully updated!");
			this.subTopicService.updateTopic(forumSubTopic);
			return "redirect:/admin/view/" + forumSubTopic.getForumMainTopics().getTitle() + "/subtopic";
		}
	}
	
	@DeleteMapping("/admin/delete/sub/topic/id/{id}")
	public String deleteSubTopic(@PathVariable Long id, ForumSubTopic forumSubTopic) {
		forumSubTopic = this.subTopicService.findId(id);
		this.subTopicService.deleteId(id);
	
		return "redirect:/admin/view/" + forumSubTopic.getForumMainTopics().getTitle() + "/subtopic";
	}
}
