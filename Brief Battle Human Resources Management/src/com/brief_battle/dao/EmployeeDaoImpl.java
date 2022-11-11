package com.brief_battle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.brief_battle.utility.DBUtil;

public class EmployeeDaoImpl implements EmployeeDao{

	@Override
	public String changePassword(int id, String newPass) {
		String message = "Wrong employee Id";
		try(Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update employee set password=? where employeeId=?");
			ps.setString(1, newPass);
			ps.setInt(2, id);
			
			int x = ps.executeUpdate();
			if( x != 0) {
				message = "Password changed successfully";
			}
		}catch(SQLException e) {
			e.getMessage();
		}
		
		return message;
	}

}
