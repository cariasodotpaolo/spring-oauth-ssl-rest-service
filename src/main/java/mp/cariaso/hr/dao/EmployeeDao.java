package mp.cariaso.hr.dao;

import java.sql.SQLException;
import java.util.List;

import mp.cariaso.hr.domain.Employee;
import mp.cariaso.hr.entity.EmployeeEntity;

public interface EmployeeDao {

	public List<EmployeeEntity> list(String status) throws SQLException;

	public EmployeeEntity getEmployee(long id) throws SQLException;

	public EmployeeEntity addEmployee(Employee newEmployee) throws SQLException;

	public EmployeeEntity updateEmployee(Employee employee) throws SQLException;

	public EmployeeEntity deleteEmployee(long employeeId) throws SQLException;
}