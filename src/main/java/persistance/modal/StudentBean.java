package persistance.modal;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import persistance.dto.*;


public class StudentBean {
	private int id;
	private String name;
	private String dob;
	private String gender;
	private String phone;
	private String education;
	private MultipartFile photo;
	private List<String> courses;
	private List<String> course_id;
	private List<CourseRequestDTO> coursess;
	@Override
	public String toString() {
		return "StudentBean [courses=" + courses + "]";
	}
	private String stringphoto;
	public MultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	private String course;  
	
	public StudentBean(){
		
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
	

	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getStringphoto() {
		return stringphoto;
	}
	public void setStringphoto(String stringphoto) {
		this.stringphoto = stringphoto;
	}
	public List<String> getCourses() {
		return courses;
	}
	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
	public List<String> getCourse_id() {
		return course_id;
	}
	public void setCourse_id(List<String> course_id) {
		this.course_id = course_id;
	}
	public List<CourseRequestDTO> getCoursess() {
		return coursess;
	}
	public void setCoursess(List<CourseRequestDTO> coursess) {
		this.coursess = coursess;
	}
	

}
