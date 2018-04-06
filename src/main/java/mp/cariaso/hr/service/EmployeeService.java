package mp.cariaso.hr.service;

import java.sql.SQLException;
import java.util.List;

import mp.cariaso.hr.domain.Employee;

public interface EmployeeService {

	public List<Employee> getEmployees(String status) throws SQLException;

	public Employee getEmployee(long employeeId) throws SQLException;

	public Employee addEmployee(Employee employee) throws SQLException;

	public Employee updateEmployee(Employee employee) throws SQLException;

	public Employee deleteEmployee(long employeeId) throws SQLException;

}