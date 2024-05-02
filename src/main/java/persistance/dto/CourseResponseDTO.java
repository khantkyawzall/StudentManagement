package persistance.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseResponseDTO {
	private int id;
	private String name;
	/*
	 * private List<CourseResponseDTO> courses=new ArrayList<CourseResponseDTO>();
	 */	private boolean chosen;
	
	
	/*
	 * public List<CourseResponseDTO> getCourses() { return courses; } public void
	 * setCourses(List<CourseResponseDTO> courses) { this.courses = courses; }
	 */
	public  CourseResponseDTO() {
		
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
	public boolean isChosen() {
	    return chosen;
	}

	public void setChosen(boolean chosen) {
	    this.chosen = chosen;
	}

	
	

}
