package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistance.dto.UserRequestDTO;
import persistance.dto.UserResponseDTO;

public class UserDAO {

	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	
	public int insertUser(UserRequestDTO user) {
		int result = 0;
		String sql = "insert into user (name,email,password,confirmpassword,userrole) values(?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getConfirmpassword());
			ps.setString(5, user.getUserrole());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Insert Error" + e);
		}
		return result;
	}
	public UserResponseDTO getUserById(int id) {
		UserResponseDTO dto = new UserResponseDTO();
		String sql = "SELECT * FROM user WHERE id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setPassword(rs.getString("password"));
				dto.setConfirmpassword(rs.getString("confirmpassword"));
				dto.setUserrole(rs.getString("userrole"));
				
			}
		}catch(SQLException e) {
			System.out.println("Select User By Id" + e);
		}
		return dto;
	}
	public List<UserResponseDTO> selectall() {
		List<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		String sql = "select*from user";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserResponseDTO response = new UserResponseDTO();
				response.setId(rs.getInt("id"));
				System.out.println(rs.getInt("id"));
				response.setName(rs.getString("name"));
				System.out.println(rs.getString("name"));
				response.setEmail(rs.getString("email"));
				response.setPassword(rs.getString("password"));
				response.setConfirmpassword(rs.getString("confirmpassword"));
				response.setUserrole(rs.getString("userrole"));
				list.add(response);
			}

		} catch (SQLException e) {
			System.out.println("Select All Error" + e);
		}
		return list;
	}
	public int deleteUser(int id) {
		int result = 0;
		String sql = "delete from user where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Delete error" + e);
		}
		return result;
	}public int updateUser(UserRequestDTO user) {
		int result = 0;
		String sql = "update user set name=?,email=?,password=?,confirmpassword=?,userrole=? where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getName());
			System.out.println(user.getName());
			ps.setString(2, user.getEmail());
			System.out.println(user.getEmail());
			ps.setString(3, user.getPassword());
			System.out.println(user.getPassword());
			ps.setString(4, user.getConfirmpassword());
			ps.setString(5, user.getUserrole());
			ps.setInt(6, user.getId());
			System.out.println("id id user"+user.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Update error" + e);
		}
		return result;
	}
	public boolean isEmailExists(String email) {
	    boolean emailExists = false;
	    String sql = "SELECT COUNT(*) FROM user WHERE email=?";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            emailExists = (count > 0);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error checking email existence: " + e);
	    }
	    return emailExists;
	}
	


}
