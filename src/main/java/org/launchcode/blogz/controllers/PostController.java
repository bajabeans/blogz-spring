package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		
		//get request parameters
		//validate parameters -- both body and title
		//if valid, create new Post
		//if not, send back to form with errors
		
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		HttpSession thisSession = request.getSession();
		User author = getUserFromSession(thisSession);
		
		if(body != null && title !=null){
			Post newPost = new Post(title, body, author);
			postDao.save(newPost);
			return "redirect:index"; // TODO - this redirect should go to the new post's page
		}
		else
		{
			//add error
			return "newpost"; 
		}
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		
		// get given post
		
		//pass post into template
		
		Post singlePost = postDao.findByUid(uid);
		model.addAttribute("post", singlePost);
		
		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		
		//get all of user posts
		
		//pass into template
		User un = userDao.findByUsername(username);
		//int authorID = un.getUid();
		
		List<Post> posts = un.getPosts();
		
		
		
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
