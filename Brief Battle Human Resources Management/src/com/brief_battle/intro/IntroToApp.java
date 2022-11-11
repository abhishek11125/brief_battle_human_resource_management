package com.brief_battle.intro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.brief_battle.dao.AdminDao;
import com.brief_battle.dao.AdminDaoImpl;
import com.brief_battle.model.Department;
import com.brief_battle.model.Employee;
import com.brief_battle.utility.DBUtil;

public class IntroToApp {

	public static void main(String[] args) {
		boolean empFlag = false;
		boolean adminFlag = false;
		
		Scanner sc = new Scanner(System.in);
		IntroToApp intro = new IntroToApp();
		
		System.out.println("Welcome to Brief Battle Human Resource Services");
		
		System.out.println("Enter 1 to login as Admin \n Enter 2 to login as Employee");
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
			sc.close();
			String res = intro.employeeAccess(empEmail,empPass);
			if(res != null) {
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
	
	public String employeeAccess(String empEmail, String empPass) {
		String res = null;
		try(Connection conn  = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("select * from employee where email=? AND password=?");
			ps.setString(1, empEmail);
			ps.setString(2, empPass);
			
			ResultSet rs =  ps.executeQuery();
			
			if(rs.next()) {
				 res  = rs.getString("firstName");
				return res;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void adminUseCase() {
		Scanner sc = new Scanner(System.in);
		AdminDao admin = new AdminDaoImpl();
		System.out.println("Welcome to admin console");
		int val = 0;
		System.out.println("1. Register Employee\n2. Transfer Employee\n3. Update & View Department\n4. Add New Department\n5. Check Leave Requests\n0. Exit");
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
			
			Employee employee = new Employee(id, firstName, middleName, lastName, birthDate, address, gender, mobileNumber, email, deptId, password);
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
				System.out.println(dept);
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
			
			break;
		}

		default:
		{
			System.out.println("Thank You");
			
			break;
		}
		}
		
	}
	public void employeeUseCase(String name) {
		System.out.println("Welcome "+name);
		
	}

}
