package com.shipserv.hr.service;

import java.sql.SQLException;
import java.util.List;

import com.shipserv.hr.domain.Employee;

public interface EmployeeService {

	public List<Employee> getEmployees(String status) throws SQLException;

	public Employee getEmployee(long employeeId) throws SQLException;

	public Employee addEmployee(Employee employee) throws SQLException;

	public Employee updateEmployee(Employee employee) throws SQLException;

	public Employee deleteEmployee(long employeeId) throws SQLException;

}