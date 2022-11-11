package com.brief_battle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.brief_battle.model.Employee;
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

	@Override
	public Employee getDetails(int id) {
		Employee emp = null;
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from employee where employeeId=?");
			ps.setInt(1, id);
			
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			int id1 = rs.getInt("employeeId");
			String firstName = rs.getString("firstName");
			String middleName = rs.getString("middleName");
			String lastName = rs.getString("lastName");
			String birthDate = rs.getString("dateOfBirth");
			String address = rs.getString("address");
			String gender = rs.getString("Gender");
			String mobileno = rs.getString("mobileNumber");
			String email = rs.getString("email");
			int deptId = rs.getInt("deptId");
			String password = rs.getString("password");
			String leaveApply = rs.getString("leaveApply");
			
			emp = new Employee(id1, firstName, middleName, lastName, birthDate, address, gender, mobileno, email, deptId, password,leaveApply);
			
		}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return emp;
	}

	@Override
	public String updateAddress(int id ,String newAddress) {
		String message = "Adress updation failed";
		try (Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update employee set address=? where employeeId=?");
			ps.setString(1, newAddress);
			ps.setInt(2, id);
			
			int x = ps.executeUpdate();
			
			if(x !=0) {
				message = "Adress updated successfully";
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		
		return message;
	}

	@Override
	public String updateMobileNumber(int id, String newMobile) {
		String message = "Mobile number updation failed";
		try (Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update employee set  mobileNumber=? where employeeId=?");
			ps.setString(1, newMobile);
			ps.setInt(2, id);
			
			int x = ps.executeUpdate();
			
			if(x !=0) {
				message = "Mobile number updated successfully";
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		
		return message;
	}

	@Override
	public String applyLeave(int id,String condition) {
		String message  = "leave application failed";
		
		try (Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update employee set leaveApplied=? where employeeId=?");
			ps.setString(1, condition);
			ps.setInt(2, id);
			
			int x = ps.executeUpdate();
			if(x != 0) {
				message = "Leave application submitted successfully";
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return message;
	}

}
