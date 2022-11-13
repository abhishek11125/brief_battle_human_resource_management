package com.brief_battle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.brief_battle.model.Department;
import com.brief_battle.model.Employee;
import com.brief_battle.utility.DBUtil;

public class AdminDaoImpl implements AdminDao{

	@Override
	public String addEmployee(Employee employee) {
		String message = "Details not saved";
		
		try(Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("insert into employee values(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, employee.getEmployeeID());
			ps.setString(2, employee.getFirstName());
			ps.setString(3, employee.getMiddleName());
			ps.setString(4, employee.getLastName());
			ps.setString(5,employee.getDateOfBirth());
			ps.setString(6,employee.getAddress());
			ps.setString(7, employee.getGender());
			ps.setString(8, employee.getMobileNumber());
			ps.setString(9, employee.getEmail());
			ps.setInt(10, employee.getDeptId());
			ps.setString(11, employee.getpassword());
			ps.setString(12, employee.getleaveApply());
			
		int x = ps.executeUpdate();
		
		if(x != 0) {
			message = "Data added successfully";
		}
			
			
		}catch(SQLException e) {
			e.getMessage();
		}
		
		return message;
	}

	@Override
	public String addDepartment(Department department) {
		String message = "Department not added";
		
		try(Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("insert into department values(?,?,?)");
			ps.setInt(1, department.getDepartmentId());
			ps.setString(2, department.getDepartmentName());
			ps.setString(3, department.getLocation());
			
			int x = ps.executeUpdate();
			
			if(x !=0) {
				message = "Department added successfully";
			}
		}catch(SQLException e) {
			e.getMessage();
		}
		
		return message;
	}

	@Override
	public String transferEmployee(int id,int newDept) {
		String message = "Transfer failed";
		
		try(Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update employee set deptId=? where employeeId=?");
			ps.setInt(1, newDept);
			ps.setInt(2, id);
			
			
			int x = ps.executeUpdate();
			if(x != 0) {
				message = "Transferred Successfully";
			}
		}catch(SQLException e) {
			e.getMessage();
		}
		
		return message;
	}

	@Override
	public String updateDeptLocation(int deptId, String newLoc) {
		String message = "Location updation failed";
		try(Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update department set location=? where departmentId = ?");
			ps.setString(1, newLoc);
			ps.setInt(2, deptId);
			
			int x = ps.executeUpdate();
			if(x != 0) {
				message = "Location updated successfully";
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return message;
	}

	@Override
	public String updateDeptId(String newDeptName, int Id) {
		String message = "Department name updation failed";
		
		try (Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update department set departmentName=? where departmentId=?;");
			ps.setString(1, newDeptName);
			ps.setInt(2, Id);
			
			int x = ps.executeUpdate();
			if(x != 0) {
				message = "Department name updated successfully";
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		
		return message;
	}

	@Override
	public List<Department> getAllDeprtments() {
		Department dept = null;
		List<Department> deptList = new ArrayList<>();
		try (Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("select * from department");
			ResultSet rs =  ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("departmentId");
				String deptName = rs.getString("departmentName");
				String location = rs.getString("location");
				
				dept = new Department(id, deptName, location);
				deptList.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return deptList;
	}

	@Override
	public int getLeaveAppliedEmployee(String condition) {
		int id = -1;
		try (Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("select employeeId from employee where leaveApplied=?");
			ps.setString(1, condition);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt("employeeId");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return id;
	}

	@Override
	public String updateLeaveStatus(int id, String condition) {
		String message = "Leave status updation failed";
		try (Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("update employee set leaveApplied=? where employeeId=?");
			ps.setString(1, condition);
			ps.setInt(2, id);
			
		int x = ps.executeUpdate();
		if(x != 0) {
			System.out.println("leave status updated successfully");
		}
		} catch (SQLException e) {
			e.getMessage();
		}
		return message;
	}

}
