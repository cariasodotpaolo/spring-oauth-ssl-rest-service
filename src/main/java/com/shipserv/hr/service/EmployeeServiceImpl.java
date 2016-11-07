/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shipserv.hr.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipserv.hr.dao.EmployeeDao;
import com.shipserv.hr.domain.Employee;
import com.shipserv.hr.entity.EmployeeEntity;

/**
 *
 * @author mpcariaso
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeDao employeeDao;
    
    
    
    @Override
	public List<Employee> getEmployees(String status)  throws SQLException {
        
        List<EmployeeEntity> entities = employeeDao.list(status);
        
        List<Employee> employees = new ArrayList<>();
        
        for(EmployeeEntity e: entities) {
        	employees.add(entityMapper(e));
        }
        
        return employees;
    }
    
    
    @Override
	public Employee getEmployee(long employeeId) throws SQLException  {
        
        return entityMapper(employeeDao.getEmployee(employeeId));
    }
    
    
    @Override
	public Employee addEmployee(Employee employee) throws SQLException  {
        
        return entityMapper(employeeDao.addEmployee(employee));
    }
    
    
    @Override
	public Employee updateEmployee(Employee employee) throws SQLException  {
        
        return entityMapper(employeeDao.updateEmployee(employee));
    }
    
    @Override
	public Employee deleteEmployee(long employeeId) throws SQLException  {
        
        return entityMapper(employeeDao.deleteEmployee(employeeId));
    }
    
    private Employee entityMapper(EmployeeEntity entity) {
    	
    	Employee employee = new Employee();
    	
    	employee.setAddress(entity.getAddress());
    	employee.setContactNumber(entity.getContactNumber());
    	employee.setId(entity.getId());
    	employee.setName(entity.getName());
    	employee.setStatus(entity.getStatus());
    	
    	return employee;
    }
}
