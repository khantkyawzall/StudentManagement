package persistance.dto;

import java.util.List;

public class StudentResponseDTO {

	private int id;
	private String name;
	private String dob;
	private String gender;
	private String phone;
	private String education;
	
	private String photo;
	private List<CourseRequestDTO> coursess;
	
	private List<String> courses;

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String inputStream) {
		this.photo = inputStream;
	}

	private String course;

	public StudentResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	
	
	
	public List<CourseRequestDTO> getCoursess() {
		return coursess;
	}

	public void setCoursess(List<CourseRequestDTO> coursess) {
		this.coursess = coursess;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}



}
