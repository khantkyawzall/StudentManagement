package persistance.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import persistance.dao.CourseDAO;
import persistance.dao.UserDAO;
import persistance.dto.CourseRequestDTO;
import persistance.modal.CourseBean;
import persistance.modal.UserBean;

@Controller
@RequestMapping("/useruser")
public class CourseForUserController {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CourseDAO courseDAO;

	@GetMapping("/courseregisterForUser")
	public ModelAndView showCourseRegister(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var userDTO = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(userDTO.getName());
			model.addAttribute("user", user);
		}
		return new ModelAndView("courseregisterForUser", "course", new CourseBean());
	}

	@PostMapping("/courseregisterForUser")
	public String registerCourse(@ModelAttribute("course") CourseBean course, HttpSession session,
			RedirectAttributes redirectAttributes, BindingResult result, Model model, HttpServletRequest request) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var userDTO = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(userDTO.getName());

			if (course.getName() == null || course.getName().isEmpty()) {
				result.rejectValue("name", "error.course", "Course name cannot be empty");
				model.addAttribute("user", user);
				return "courseregisterForUser";

			} else {

				CourseRequestDTO dto = new CourseRequestDTO();
				dto.setName(course.getName());
				int rs = courseDAO.insertCourse(dto);
				if (rs == 2) {
					redirectAttributes.addFlashAttribute("error1", "Course Name Already Exits!!!");
				} else {
					redirectAttributes.addFlashAttribute("success", "Course registered successfully");

				}

			}
			return "redirect:/useruser/courseregisterForUser";
		}
		return "redirect:/useruser/courseregisterForUser";
	}
}
