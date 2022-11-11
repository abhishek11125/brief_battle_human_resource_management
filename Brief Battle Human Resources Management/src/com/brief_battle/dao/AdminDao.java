package com.brief_battle.dao;

import java.util.List;

import com.brief_battle.model.Department;
import com.brief_battle.model.Employee;

public interface AdminDao{
	public String addEmployee(Employee employee);
	
	public String addDepartment(Department department);
	
	public String transferEmployee(int id,int newDept);
	
	public String updateDeptLocation(int deptId, String newLoc);
	
	public String updateDeptId(String NewDeptName, int Id);
	
	public List<Department> getAllDeprtments();
	

}
