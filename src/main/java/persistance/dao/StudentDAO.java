package persistance.dao;

import java.sql.Connection;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.tomcat.util.codec.binary.Base64;


import java.util.List;


import persistance.dto.*;
import persistance.dto.StudentResponseDTO;

public class StudentDAO {

	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}

	public int addStudent(StudentRequestDTO student) {

		int result = 0;
		String sql = "INSERT INTO student(name,dob,gender,phone,education,photo) VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, student.getName());
			ps.setString(2, student.getDob());
			ps.setString(3, student.getGender());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getEducation());
			Blob blob = new javax.sql.rowset.serial.SerialBlob(student.getPhoto());
			ps.setBlob(6, blob);
			result = ps.executeUpdate();

			ResultSet generatedKeys = ps.getGeneratedKeys();
			int studentId = 0;
			if (generatedKeys.next()) {
				studentId = generatedKeys.getInt(1);
			}

			if (result != 0) {
				for (String course_id : student.getCourse_id()) {
					sql = "INSERT INTO course_has_student(student_id,course_id) VALUES(?,?)";
					PreparedStatement psCourse = con.prepareStatement(sql);
					psCourse = con.prepareStatement(sql);
					psCourse.setInt(1, studentId);
					int id = Integer.parseInt(course_id);
					psCourse.setInt(2, id);
					result = psCourse.executeUpdate();

				}
			}
		} catch (SQLException e) {
			System.out.print("Insert error!!!   " + e);
		}
		return result;
	}

	public List<StudentResponseDTO> selectAllStudent() throws UnsupportedEncodingException {
	    List<StudentResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT * FROM student";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentResponseDTO response = new StudentResponseDTO();
	            response.setId(rs.getInt("id"));
	            response.setName(rs.getString("name"));
	            response.setDob(rs.getString("dob"));
	            response.setGender(rs.getString("gender"));
	            response.setPhone(rs.getString("phone"));
	            response.setEducation(rs.getString("education"));
	            Blob blob=(Blob) rs.getBlob("photo");
                byte[] bytes = blob.getBytes(1,(int) blob.length());
                byte[] encodeBase64=Base64.encodeBase64(bytes);
                response.setPhoto(new String(encodeBase64,"UTF-8"));

	            // Fetch courses for the student
	            int studentId = rs.getInt("id");
	            List<String> courses = getCourseByStudentById(studentId);
	            response.setCourses(courses);

	            list.add(response);
	        }
	    } catch (SQLException e) {
	        System.out.println("Select All Error: " + e);
	    }
	    return list;
	}


	public int deleteStudent(int id) {
	      int result = 0;
	      try {
	          // First, delete associated records in student_has_course table
	          String deleteCoursesSql = "DELETE FROM course_has_student WHERE student_id = ?";
	          PreparedStatement psDeleteCourses = con.prepareStatement(deleteCoursesSql);
	          psDeleteCourses.setInt(1, id);
	          result = psDeleteCourses.executeUpdate();
	          
	          // Then, delete the student record
	          String deleteStudentSql = "DELETE FROM student WHERE id = ?";
	          PreparedStatement psDeleteStudent = con.prepareStatement(deleteStudentSql);
	          psDeleteStudent.setInt(1, id);
	          result += psDeleteStudent.executeUpdate();
	      } catch (SQLException e) {
	          System.out.print("Delete error!!!   " + e); 
	      }
	      return result;
	  }

	public int updateStudent(StudentRequestDTO student) {
		int result = 0;
		String sql = "update student set name=?,dob=?,gender=?,phone=?,education=? where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student.getName());
			ps.setString(2, student.getDob());
			ps.setString(3, student.getGender());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getEducation());
			System.out.println("Student ID: " + student.getId());
			ps.setInt(6, student.getId());
			result = ps.executeUpdate();

			if (result != 0) {
				// Delete existing courses for the student
				sql = "DELETE FROM course_has_student WHERE student_id=?";
				PreparedStatement deleteCoursesStatement = con.prepareStatement(sql);
				deleteCoursesStatement.setInt(1, student.getId());
				deleteCoursesStatement.executeUpdate();

				// Insert new courses for the student
				sql = "INSERT INTO course_has_student(student_id,course_id) VALUES(?,?)";
				PreparedStatement psCourse = con.prepareStatement(sql);
				for (String course_id : student.getCourse_id()) {
					psCourse.setInt(1, student.getId());
					int id = Integer.parseInt(course_id);
					psCourse.setInt(2, id);
					result = psCourse.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Update Error" + ":" + e);
		}
		return result;

	}

	public StudentResponseDTO selectStudentById(int id) {
		StudentResponseDTO response = new StudentResponseDTO();
		String sql = "select*from student where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				response.setId(rs.getInt("id"));
				response.setName(rs.getString("name"));
				response.setDob(rs.getString("dob"));
				response.setGender(rs.getString("gender"));
				response.setPhone(rs.getString("Phone"));
				response.setEducation(rs.getString("education"));
				
				List<String> courses = getCourseByStudentById(id);
	            response.setCourses(courses);
			}
		} catch (SQLException e) {
			System.out.println("select by id error" + e.getMessage());
		}
		return response;
	}

	public List<String> getCourseByStudentById(int id) {
		List<String> courseNames = new ArrayList<>();
		String sql = "select c.name from course c \r\n" + "join course_has_student chs on c.id = chs.course_id\r\n"
				+ "join student s on chs.student_id = s.id\r\n" + "where s.id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String courseName = rs.getString("name");
				courseNames.add(courseName);
			}

		} catch (SQLException e) {
			System.out.println("select by studentid error" + e.getMessage());
		}

		return courseNames;
	}

	public StudentResponseDTO getStudentById(int id) {
	     StudentResponseDTO student=new StudentResponseDTO();
	      String sql="SELECT * FROM student WHERE id=?";
	      try {
	        PreparedStatement ps=con.prepareStatement(sql);
	        ps.setInt(1, id);
	        ResultSet rs=ps.executeQuery();
	        while(rs.next()) {
	          student.setId(rs.getInt("id"));
	          student.setName(rs.getString("name"));                
	          student.setDob(rs.getString("dob"));
	          student.setGender(rs.getString("gender"));
	          student.setPhone(rs.getString("phone"));
	          student.setEducation(rs.getString("education"));
	         
	                sql="SELECT c.* FROM course_has_student sc INNER JOIN course c ON sc.course_id = c.id WHERE sc.student_id =?";
	                ps=con.prepareStatement(sql);
	                ps.setInt(1, id);
	                ResultSet join_rs=ps.executeQuery();
	          List<CourseRequestDTO> courses=new ArrayList<CourseRequestDTO>();
	          while(join_rs.next()) {
	        	  CourseRequestDTO course=new CourseRequestDTO();
	            course.setId(join_rs.getInt("id"));
	            System.out.println( "HIHI HIHIHIH"+join_rs.getInt("id"));
	            course.setName(join_rs.getString("name"));
	            System.out.println( "HIHI HIHIHIH"+join_rs.getString("name"));
	            courses.add(course);    
	          }
	          student.setCoursess(courses);
	        }        
	      }catch(SQLException e) {
	        System.out.println("select by code error"+e);
	      }
	      return student;
	    }
	public List<CourseResponseDTO> getSelectedByStudent(int id) {
	      List<CourseResponseDTO> chosenCourses = new ArrayList<>();
	      String sql = "SELECT c.* FROM course c " +
	                   "JOIN course_has_student shc ON c.id = shc.course_id " +
	                   "WHERE shc.student_id = ?";
	      System.out.println("DAO work");
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
	          System.out.println("SELECT CHOSEN COURSES ERROR: " + e);
	          e.printStackTrace();
	      }
	      return chosenCourses;
	  }


}