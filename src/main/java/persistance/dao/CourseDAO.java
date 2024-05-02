package persistance.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import persistance.dto.*;


public class CourseDAO {
	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	public int insertCourse(CourseRequestDTO course) {
		int result=0;
		String sql="INSERT INTO course (name) values(?)";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			
			ps.setString(1,course.getName());
			result=ps.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e) {
			result=2;
			System.out.println("Duplicate Courses");
			
		}
		
		catch (SQLException e) {
			System.out.println("Insert Error"+e);
		}
		return result;
	}
	public List<CourseResponseDTO> getCourses() {
	    List<CourseResponseDTO> courses = new ArrayList<>();
	    String sql = "SELECT * FROM course";

	    try {
	      PreparedStatement ps = con.prepareStatement(sql);
	      ResultSet rs = ps.executeQuery();
	      while (rs.next()) {
	    	  CourseResponseDTO course = new CourseResponseDTO();
	        course.setId(rs.getInt("id"));
	        course.setName(rs.getString("name"));
	        courses.add(course);
	      }
	    } catch (SQLException e) {
	      System.out.println("Select Course Error: " + e);
	    }
	    return courses;
	  }
	public List<CourseResponseDTO> getSelectedCoursesByStudent(int id) {
	      List<CourseResponseDTO> chosenCourses = new ArrayList<>();
	      String sql = "SELECT c.* FROM course c " +
	                   "JOIN course_has_student shc ON c.id = shc.course_id " +
	                   "WHERE shc.student_id = ?";
	      try {
	          PreparedStatement ps = con.prepareStatement(sql);
	          ps.setInt(1, id);
	          ResultSet rs = ps.executeQuery();
	          while (rs.next()) {
	              CourseResponseDTO course = new CourseResponseDTO();
	              course.setId(rs.getInt("id"));
	              course.setName(rs.getString("name"));
	              chosenCourses.add(course);
	          }
	      } catch (SQLException e) {
	          System.out.println("SELECTED COURSES ERROR: " + e);
	          e.printStackTrace();
	      }
	      return chosenCourses;
	  }
}
