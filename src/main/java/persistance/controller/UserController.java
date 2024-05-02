package persistance.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import persistance.dao.StudentDAO;
import persistance.dao.UserDAO;
import persistance.dto.CourseResponseDTO;
import persistance.dto.StudentRequestDTO;
import persistance.dto.StudentResponseDTO;
import persistance.dto.UserRequestDTO;
import persistance.dto.UserResponseDTO;
import persistance.modal.StudentBean;
import persistance.modal.UserBean;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDAO userDAO;

	@GetMapping("/userregister")
	public Object showUserRegister(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var dto = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(dto.getName());
			model.addAttribute("userinfo", user);
			return new ModelAndView("userregister", "user", new UserBean());
		}

		return "redirect:/login";

	}

	@PostMapping("/userregister")
	public String processUserRegister(@ModelAttribute("user") UserBean user, Model model, BindingResult br,
			HttpServletRequest request, HttpSession session) throws IOException {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var dto = userDAO.getUserById(userId);
			UserBean userss = new UserBean();
			userss.setName(dto.getName());
			model.addAttribute("userinfo", userss);
		}
		if (br.hasErrors()) {
			return "userregister";
		}

		else if (user.getName().equals("") || user.getEmail().equals("") || user.getPassword().equals("")
				|| user.getConfirmpassword().equals("") || user.getUserrole().equals("")) {
			Integer userId2 = (Integer) session.getAttribute("userId");
			var dto = userDAO.getUserById(userId2);
			UserBean userss = new UserBean();
			userss.setName(dto.getName());
			model.addAttribute("userinfo", userss);
			request.setAttribute("error", "Please Fill Require Data!!!");
			return "userregister";
		} else if (user.getName().equals("") && user.getEmail().equals("") && user.getPassword().equals("")
				&& user.getConfirmpassword().equals("") && user.getUserrole().equals("")) {
			var dto = userDAO.getUserById(userId);
			UserBean userss = new UserBean();
			userss.setName(dto.getName());
			model.addAttribute("userinfo", userss);
			request.setAttribute("error1", "Please Check Your Data Again!!!");

			return "userregister";
		}

		else if (userDAO.isEmailExists(user.getEmail())) {
			request.setAttribute("error2", "Email already exists!");
			return "userregister";
		}

		UserRequestDTO dto = new UserRequestDTO();
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setConfirmpassword(user.getConfirmpassword());
		dto.setUserrole(user.getUserrole());

		int rs = userDAO.insertUser(dto);

		if (rs == 0) {
			model.addAttribute("error3", "Insert Fail Error!!!!");
		} else if (rs == 1) {
			model.addAttribute("success", "Register Successfully");
		}
		return "redirect:/user/userregister";
	}

	@GetMapping("/userlist")
	public String showStudentSearch(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var dto = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(dto.getName());
			model.addAttribute("user", user);
		}

		List<UserBean> student = new ArrayList<UserBean>();
		List<UserResponseDTO> userDTOs = userDAO.selectall();
		model.addAttribute("users", userDTOs);
		return "userlist";
	}

	@GetMapping("/updateuser/{id}")
	public ModelAndView showUpdateUser(@PathVariable int id, HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var dto = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(dto.getName());
			model.addAttribute("userinfo", user);
		}
		UserResponseDTO userResponseDTO = userDAO.getUserById(id);
		UserBean userup = new UserBean();
		userup.setId(userResponseDTO.getId());
		userup.setName(userResponseDTO.getName());
		userup.setEmail(userResponseDTO.getEmail());
		userup.setPassword(userResponseDTO.getPassword());
		userup.setConfirmpassword(userResponseDTO.getConfirmpassword());
		userup.setUserrole(userResponseDTO.getUserrole());

		model.addAttribute("userup", userup); // Add the userup object to the model

		model.addAttribute("id", id);

		return new ModelAndView("updateuser", "user", new UserBean());
	}

	@PostMapping("/updateuser")
	public String processupdateUser(@ModelAttribute("user") @Validated UserBean user, Model model,
			HttpServletRequest request) {
		System.out.println("Hiiiiiiiiiiiii");
		String name = user.getName();
		String email = user.getEmail();
		String pass = user.getPassword();
		String conp = user.getConfirmpassword();
		String userrole = user.getUserrole();

		if (name.equals("") || email.equals("") || pass.equals("") || conp.equals("") || userrole.equals("")) {

			request.setAttribute("error", "Plesae fill require data!!");
			return "updateuser";

		}

		else if (pass != conp) {
			request.setAttribute("error1", "Password And ConfirmPassword does not match");
			return "updateuser";

		}

		UserRequestDTO dto = new UserRequestDTO();
		dto.setId(user.getId());
		dto.setName(name);
		dto.setEmail(email);
		dto.setPassword(pass);
		dto.setConfirmpassword(conp);
		dto.setUserrole(userrole);

		userDAO.updateUser(dto);

		return "redirect:/user/userlist";
	}

	@GetMapping("/deleteuser/{id}")
	public String deleteUser(@PathVariable int id, HttpSession session, Model model) {
		UserDAO dao = new UserDAO();
		dao.deleteUser(id);
		return "redirect:/user/userlist";
	}

}
