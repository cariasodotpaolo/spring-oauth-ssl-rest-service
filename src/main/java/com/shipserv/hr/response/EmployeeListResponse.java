package com.shipserv.hr.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeListResponse {
	
	@JsonProperty("employees")
	private List<EmployeeResponse> employees = new ArrayList<>();

	public List<EmployeeResponse> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeResponse> employees) {
		this.employees = employees;
	}
	

}
