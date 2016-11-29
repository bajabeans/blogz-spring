package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		
		//get parameters from request
		//validate parameters (username, password, verify -- in user class)
		//if validated, create new user, and store in session -- *setSession from abstract controller
		//session accessed by -- Session thisSession = request.getSession():
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		if(User.isValidUsername(username) && User.isValidPassword(password) && password.equals(verify))
		//if(password.equals(verify))
		{
			User newUser = new User(username, password);
			userDao.save(newUser);
			
			HttpSession thisSession = request.getSession();
			setUserInSession(thisSession, newUser);
			return "redirect:blog/newpost";
		}
		if(User.isValidUsername(username)== false)
		{
			String unError = "Not valid username";
			model.addAttribute("username_error", unError);
			return "signup";
		}
		if(User.isValidPassword(password)== false)
		{
			String pwError = "Not valid password";
			model.addAttribute("password_error", pwError);
			return "signup";
		}
		if(!password.equals(verify))
		{
			String verError = "Passwords do not match";
			model.addAttribute("verify_error", verError);
			return "signup";
		}
		else
		{
			return "signup";
		}
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO -implement login
		
		//get parameters from request
		
		//get user by their username
		
		//check password is correct
		
		//log in (setting user in session)
		
		//NOTES: Login/Sign Up working in session -- verify does not work *working now
		//New post working in database, need to display
		// -- Still need errors
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		
		User un =userDao.findByUsername(username);
		if(un.isMatchingPassword(password))
		{
			HttpSession thisSession = request.getSession();
			setUserInSession(thisSession, un);
			return "redirect:blog/newpost";
		}
		else
		{
			return "signup";
		}
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
