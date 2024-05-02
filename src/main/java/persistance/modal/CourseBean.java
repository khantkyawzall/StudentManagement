package persistance.modal;

import javax.validation.constraints.NotNull;

public class CourseBean {
	private int id;
	@NotNull
	private String name;
	public  CourseBean() {
		
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
}



