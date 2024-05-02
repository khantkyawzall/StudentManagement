package persistance.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import persistance.dao.LoginDAO;
import persistance.dao.UserDAO;
import persistance.dto.UserResponseDTO;
import persistance.modal.UserBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@Autowired
	private LoginDAO loginDAO;
	@Autowired
	UserDAO userDAO;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showLoginPage(Model model) {
		return new ModelAndView("login", "users", new UserBean());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("users") UserBean user, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		/*
		 * if (bindingResult.hasErrors()) { return "login"; }
		 */

		String email = user.getEmail();
		String password = user.getPassword();

		if (email.isEmpty() || password.isEmpty()) {
			request.setAttribute("error", "Please fill in both email and password fields");
			return "login";
		}

		boolean login = loginDAO.LoginCheck(email, password);
		if (!login) {
			request.setAttribute("error1", "Incorrect email or password");
			return "login";
		}
		/*
		 * if (loginDAO.isEmailExists(user.getEmail())) { model.addAttribute("error2",
		 * "Email already exists!"); return "register"; }
		 */
		int userId = loginDAO.getUserId(email, password);
		session.setAttribute("userId", userId);

		UserResponseDTO userResponseDTO = loginDAO.loginUser(email, password);
		if (userResponseDTO != null) {
			if (userResponseDTO.getUserRole().equals("Admin")) {
				return "redirect:/courseregister"; // Redirect to Admin course registration page
			} else if (userResponseDTO.getUserRole().equals("User")) {
				return "redirect:/useruser/courseregisterForUser"; // Redirect to User course registration page
			} else {
				request.setAttribute("error", "User role not found");
				return "login";
			}
		} else {
			request.setAttribute("error", "User role not found");
			return "login";
		}
	}
	
	@RequestMapping(value="/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView logout(HttpSession session) {  
		System.out.println("mviofreognsoiswedfno");
	    if (session != null) {
	        session.invalidate();
	    }
	    return new ModelAndView ("redirect:/");  // Redirect to login page after logout
	}

}
