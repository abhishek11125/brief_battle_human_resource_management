package com.brief_battle.dao;

import com.brief_battle.model.Employee;

public interface EmployeeDao {
	public String changePassword(int id, String newPass);
	
	public Employee getDetails(int id);
	
	public String updateAddress(int id, String newAddress);
	
	public String updateMobileNumber(int id, String newMobile);
	
	public String applyLeave(int id, String condition);

}
