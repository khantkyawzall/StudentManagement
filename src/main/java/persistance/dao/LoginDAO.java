package persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistance.dto.UserResponseDTO;

public class LoginDAO {

	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}

	public boolean LoginCheck(String email, String password) {
		String sql = "SELECT * FROM user WHERE email=? AND password=? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (email.equals(rs.getString("email")) && password.equals(rs.getString("password"))) {
					return true;
				} else
					return false;

			}
		} catch (SQLException e) {
			System.out.println("Login Error" + e);

		}
		return false;
	}

	public boolean UserAdminLogin(String email, String password, String userrole) {
		String sql = "SELECT * FROM user WHERE email=? AND password=? AND userrole=? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, userrole);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (email.equals(rs.getString("email")) && password.equals(rs.getString("password"))
						&& userrole.equals(rs.getString("userrole"))) {
					return true;
				} else
					return false;

			}
		} catch (SQLException e) {
			System.out.println("Login Error" + e);

		}
		return false;
	}

	public int getUserId(String email, String password) {
		int result = 0;
		String sql = "SELECT * FROM user WHERE email=? AND password=? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt("id");
				return userId;
			}

		} catch (SQLException e) {
			System.out.println("Select User ID Error" + e);

		}
		return result;

	}

	public UserResponseDTO loginUser(String email, String password) {
		String sql = "SELECT id, userrole FROM user WHERE email=? AND password=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt("id");
				String userRole = rs.getString("userrole");
				return new UserResponseDTO(userId, userRole);
			}
		} catch (SQLException e) {
			System.out.println("Login Error: " + e);
		}
		return null;
	}
	

}
