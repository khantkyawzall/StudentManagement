package persistance.dto;

import java.util.List;

public class StudentRequestDTO {

	private int id;
	private String name;
	private String dob;
	private String gender;
	private String phone;
	private String education;
	private byte[] photo;
	private String course;
	
	private List<CourseRequestDTO> courses;

	private List<String> course_id;

	public StudentRequestDTO() {
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

	
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public List<CourseRequestDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseRequestDTO> courses) {
		this.courses = courses;
	}

	public List<String> getCourse_id() {
		return course_id;
	}

	public void setCourse_id(List<String> course_id) {
		this.course_id = course_id;
	}

}
