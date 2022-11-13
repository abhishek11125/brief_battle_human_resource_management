package com.brief_battle.intro;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.brief_battle.dao.AdminDao;
import com.brief_battle.dao.AdminDaoImpl;
import com.brief_battle.dao.EmployeeDao;
import com.brief_battle.dao.EmployeeDaoImpl;
import com.brief_battle.model.Department;
import com.brief_battle.model.Employee;
import com.brief_battle.utility.DBUtil;

public class IntroToApp {

	public static void main(String[] args) {
		boolean adminFlag = false;
		
		Scanner sc = new Scanner(System.in);
		IntroToApp intro = new IntroToApp();
		
		System.out.println("Welcome to Brief Battle Human Resource Services");
		
		System.out.println("1. To login as Admin \n2. To login as Employee");
		int val = sc.nextInt();
		
		String email = "admin121@gmail.com";
		String password = "12345";
		
		switch (val) {
		case 1:
		{
			sc.nextLine();
			System.out.println("Enter email ID: ");
			String entEmail = sc.nextLine();
			System.out.println("Enter password: ");
			String entPass = sc.nextLine();
			if(entEmail.equals(email) && entPass.equals(password)) {
				System.out.println("You are logged in as Admin");
				adminFlag = true;
				if(adminFlag) {
					intro.adminUseCase();
				}
			}else {
				System.out.println("Wrong Credentials");
			}
			break;
		}
		case 2:
		{
			sc.nextLine();
			System.out.println("Enter email ID: ");
			String empEmail = sc.nextLine();
			System.out.println("Enter password: ");
			String empPass = sc.nextLine();
			int res = intro.employeeAccess(empEmail,empPass);
			if(res >= 0) {
				intro.employeeUseCase(res);
			}else {
				System.out.println("Wrong Credentials");
			}
			break;
		}

		default:
		{
			System.out.println("Wrong entry");
			break;
		}
		}
		
	}
	
	public int employeeAccess(String empEmail, String empPass) {
		int res = -1;
		try(Connection conn  = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("select * from employee where email=? AND password=?");
			ps.setString(1, empEmail);
			ps.setString(2, empPass);
			
			ResultSet rs =  ps.executeQuery();
			
			if(rs.next()) {
				 res  = rs.getInt("employeeId");
				return res;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	//Admin Related functions
	public void adminUseCase() {
		Scanner sc = new Scanner(System.in);
		AdminDao admin = new AdminDaoImpl();
		System.out.println("Welcome to admin console");
		int val = 0;
		System.out.println("1. Register Employee\n2. Transfer Employee\n3. Update & View Department\n4. Add New Department\n5. Check Leave Requests\n0. Logout");
		val = sc.nextInt();
		
		switch (val) {
		
		case 1:
		{
			System.out.println("Enter employee id number: ");
			int id = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter first name: ");
			String firstName = sc.nextLine();
			System.out.println("Enter middle name: ");
			String middleName = sc.nextLine();
			System.out.println("Enter last name: ");
			String lastName = sc.nextLine();
			System.out.println("Enter date of birth in (YYYY/MM/DD) format: ");
			String birthDate = sc.nextLine();
			System.out.println("Enter address : ");
			String address = sc.nextLine();
			System.out.println("Enter gender: ");
			String gender = sc.nextLine();
			System.out.println("Enter mobile number: ");
			String mobileNumber = sc.nextLine();
			System.out.println("Enter email ID: ");
			String email = sc.nextLine();
			System.out.println("Enter department ID: ");
			int deptId = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();
			System.out.println("Enter leave condition: ");
			String leave = sc.nextLine();
			
			Employee employee = new Employee(id, firstName, middleName, lastName, birthDate, address, gender, mobileNumber, email, deptId, password,leave);
			String res = admin.addEmployee(employee);
			System.out.println(res);
			adminUseCase();
			break;
		}
		case 2:
		{
			System.out.println("Enter employee id: ");
			int id = sc.nextInt();
			System.out.println("Enter new department id: ");
			int newDept =sc.nextInt();
			String res = admin.transferEmployee(id,newDept);
			System.out.println(res);
			adminUseCase();
			break;
		}
		case 3:
		{
			System.out.println("1. To update location\n2. To update department name\n3. To view departments");
			int val1 = sc.nextInt();
			if(val1==1) {
				System.out.println("Enter department id: ");
				int deptId = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter new location: ");
				String newLoc = sc.nextLine();
				String res = admin.updateDeptLocation(deptId, newLoc);
				System.out.println(res);
			}else if(val1==2) {
				System.out.println("Enter new department name: ");
				String NewDeptName = sc.next();
				System.out.println("Enter department ID: ");
				int Id = sc.nextInt();
				String res = admin.updateDeptId(NewDeptName, Id);
				System.out.println(res);
			}else if(val1==3) {
				List<Department> dept = admin.getAllDeprtments();
				if(dept != null) {
					System.out.println(dept);
				}else {
					System.out.println("No any department found");
				}
			}else {
				System.out.println("Wrong input");
			}
			adminUseCase();
			
			break;
		}
		case 4:
		{
			System.out.println("Enter department ID: ");
			int departmentId = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter department name: ");
			String departmentName = sc.nextLine();
			System.out.println("Enter department location: ");
			String location = sc.nextLine();
			Department dept = new Department(departmentId, departmentName, location);
			String res = admin.addDepartment(dept);
			System.out.println(res);
			adminUseCase();
			break;
		}
		case 5:
		{
			String in = "Yes";
			int id = admin.getLeaveAppliedEmployee(in);
			if(id==-1) {
				System.out.println("No employee applied for leave");
				adminUseCase();
				break;
			}
			System.out.println("1. To accept leave\n2. To deny leave");
			int val2 = sc.nextInt();
			String condition = "Rejected";
			if(val2==1) {
				condition = "Accepted";
				String res = admin.updateLeaveStatus(id, condition);
				System.out.println(res);
			}else if(val2==2) {
				String res = admin.updateLeaveStatus(id, condition);
				System.out.println(res);
			}else {
				System.out.println("Wrong input");
			}
			adminUseCase();
			break;
		}

		default:
		{
			System.out.println("Thank You!");
			break;
		}
		}
	}
	//Employee related functions
	public void employeeUseCase(int id) {
		Scanner sc = new Scanner(System.in);
		EmployeeDao emp = new EmployeeDaoImpl();
		System.out.println("1. To change password\n2. To view and update profile\n3. To apply for leave\n0. Logout");
		int val = sc.nextInt();
		switch (val) {
		case 1:
		{
			System.out.println("Enter new password: ");
			String newPass = sc.next();
			String res = emp.changePassword(id, newPass);
			System.out.println(res);
			employeeUseCase(id);
			break;
		}
		case 2:
		{
			System.out.println("1. To view profile\n2. To update address\n3. To update mobile number");
			int val1 = sc.nextInt();
			if(val1==1) {
			Employee emp1 = emp.getDetails(id);
			if(emp1 != null) {
				System.out.println(emp1);
			}
			}else if(val1==2) {
				System.out.println("Enter new address: ");
				String newAddress = sc.next();
				String res = emp.updateAddress(id, newAddress);
				System.out.println(res);
			}else if(val1==3) {
				System.out.println("Enter new mobile number: ");
				String newMobile = sc.next();
				String res = emp.updateMobileNumber(id, newMobile);
				System.out.println(res);
			}
			employeeUseCase(id);
			
			break;
		}
		case 3:
		{
			String condition = "No";
			System.out.println("Enter 1 to apply leave");
			int x = sc.nextInt();
			if(x == 1) {
				 condition = "Yes";
			}
			String res = emp.applyLeave(id, condition);
			System.out.println(res);
			employeeUseCase(id);
			break;
		}
			
		default:
		{
			System.out.println("Thank you!");
			break;
		}
		}

	}

}
