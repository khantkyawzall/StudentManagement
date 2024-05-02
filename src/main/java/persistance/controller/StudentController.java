package persistance.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import persistance.dao.CourseDAO;
import persistance.dao.StudentDAO;
import persistance.dao.UserDAO;
import persistance.dto.CourseResponseDTO;
import persistance.dto.StudentRequestDTO;
import persistance.dto.StudentResponseDTO;
import persistance.modal.StudentBean;
import persistance.modal.UserBean;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CourseDAO courseDAO;

	@GetMapping("/studentregister")
	public ModelAndView showStudentRegister(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var dto = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(dto.getName());
			model.addAttribute("user", user);
		}

		List<CourseResponseDTO> courses = courseDAO.getCourses();

		model.addAttribute("courses", courses);
		return new ModelAndView("studentregister", "student", new StudentBean());
	}

	@PostMapping("/studentregister")
	public String processStudentRegister(@ModelAttribute("student") StudentBean student, Model model,
			HttpServletRequest request, BindingResult br, RedirectAttributes redirectAttributes,HttpSession session) throws IOException {
		Integer userId = (Integer) session.getAttribute("userId");

	    // Check if the user is logged in
	    if (userId != null) {
	        // Retrieve user details and add to the model
	        var userDto = userDAO.getUserById(userId);
	        UserBean user = new UserBean();
	        user.setName(userDto.getName());
	        model.addAttribute("user", user);
	    }
		if (br.hasErrors()) {
			return "studentregister";
		}

		else if (student.getName().equals("") || student.getDob().equals("") || student.getGender().equals("")
				|| student.getPhone().equals("") || student.getEducation().equals("")) {
			List<CourseResponseDTO> courses = courseDAO.getCourses();
		    model.addAttribute("courses", courses);
			request.setAttribute("error", "Please Fill The Require Fields");
			return "studentregister";

		} else if (student.getName().equals("") && student.getDob().equals("") && student.getGender().equals("")
				&& student.getPhone().equals("") && student.getEducation().equals("")) {
			List<CourseResponseDTO> courses = courseDAO.getCourses();
		    model.addAttribute("courses", courses);
			request.setAttribute("error1", "Please Check Your Data Again");
			return "studentregister";

		}

		List<String> courses = student.getCourses();
	    model.addAttribute("courses", courses);

		System.out.println("course size" + ":" + courses.size());

		StudentRequestDTO studentdto = new StudentRequestDTO();
		studentdto.setName(student.getName());
		studentdto.setDob(student.getDob());
		studentdto.setGender(student.getGender());
		studentdto.setPhone(student.getPhone());
		studentdto.setEducation(student.getEducation());
		studentdto.setCourse_id(courses);
		try {
			studentdto.setPhoto(student.getPhoto().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		int rs = studentDAO.addStudent(studentdto);

		if (rs == 0) {
			redirectAttributes.addFlashAttribute("error2", "Failed to register. Please provide all required data.");
		} else if (rs == 1) {
			redirectAttributes.addFlashAttribute("success", "Register Successful");
		}
		

		return "redirect:/student/studentregister";
	}

	@GetMapping("/studentsearch")
	public String showStudentSearch(HttpSession session, Model model) throws UnsupportedEncodingException {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var dto = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(dto.getName());
			model.addAttribute("user", user);
		}

		List<StudentResponseDTO> studentDTOs = studentDAO.selectAllStudent();

		model.addAttribute("student", studentDTOs);
		return "studentsearch";
	}

	@GetMapping("/updatestudent/{id}")
	public ModelAndView showUpdateStudent(@PathVariable int id, HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			var dto = userDAO.getUserById(userId);
			UserBean user = new UserBean();
			user.setName(dto.getName());
			model.addAttribute("user", user);
		}
		StudentResponseDTO dto = studentDAO.getStudentById(id);

		List<CourseResponseDTO> courses = courseDAO.getCourses();
		 List<CourseResponseDTO> chosenCourses = courseDAO.getSelectedCoursesByStudent(id);
	      for (CourseResponseDTO course : courses) {
	          boolean isChecked = chosenCourses.stream()
	                                          .anyMatch(chosenCourse -> chosenCourse.getId() == course.getId());
	          course.setChosen(isChecked); 
	      }

		model.addAttribute("courses", courses);
		model.addAttribute("studentup", dto);


		model.addAttribute("id", id);

		return new ModelAndView("updatestudent", "student", new StudentBean());
	}

	@PostMapping("/updatestudent")
	public String updatestudent(@ModelAttribute("student") @Validated StudentBean student, BindingResult br,
			Model model) {
		System.out.println("Hiiiiiiiiiiiii");
		String name = student.getName();
		String dob = student.getDob();
		String gender = student.getGender();
		String phone = student.getPhone();
		String education = student.getEducation();
		List<String> courses = student.getCourse_id();

		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setId(student.getId());
		dto.setName(name);
		dto.setDob(dob);
		dto.setGender(gender);
		dto.setPhone(phone);
		dto.setEducation(education);
		dto.setCourse_id(courses);

		studentDAO.updateStudent(dto);

		return "redirect:/student/studentsearch";
	}

	@GetMapping("/deletestudent/{id}")
	public String deleteStudent(@PathVariable int id, HttpSession session, Model model) {
		StudentDAO dao = new StudentDAO();
		dao.deleteStudent(id);
		return "redirect:/student/studentsearch";
	}
}
