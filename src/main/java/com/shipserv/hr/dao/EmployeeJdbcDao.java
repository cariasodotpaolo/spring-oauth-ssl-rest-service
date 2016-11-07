package com.shipserv.hr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.shipserv.hr.datasource.ConnectionFactory;
import com.shipserv.hr.domain.Employee;
import com.shipserv.hr.entity.EmployeeEntity;

@Repository
public class EmployeeJdbcDao implements EmployeeDao {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeJdbcDao.class);
	
	@Autowired
    private ConnectionFactory connectionFactory;
     
    
    /* (non-Javadoc)
	 * @see com.shipserv.hr.dao.EmployeeDao#list(java.lang.String)
	 */
    @Override
	public List<EmployeeEntity> list(String status) throws SQLException { 
    	
    	String query = "";
        
        if(status == null || status.isEmpty() || "ALL".equals(status.toUpperCase())) {
            query = "select * from employees";
        }
        else {
            query = "select * from employees where status='" + status + "'";
        }
       
        Connection conn = connectionFactory.getConnection();
        
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
         
        
        try {
        	return mapAsList(rs);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        } finally {        
        	conn.close();
        }
        
        return null;
    }
    
    
    /* (non-Javadoc)
	 * @see com.shipserv.hr.dao.EmployeeDao#getEmployee(int)
	 */
    @Override
	public EmployeeEntity getEmployee(long id) throws SQLException  {
        
        String query = "select * from employees where id=?";
        
        Connection conn = connectionFactory.getConnection();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();       
        
        
        EmployeeEntity entity = mapAsObject(rs);
        
        try{
        return entity;
	    } catch (Exception e) {
	    	logger.error(e.getMessage(),e);
	    } finally {        
	    	conn.close();
	    }
    
    return null;
    
    }
    
    
    /* (non-Javadoc)
	 * @see com.shipserv.hr.dao.EmployeeDao#addEmployee(com.shipserv.hr.domain.EmployeeEntity)
	 */
    @Override
	public EmployeeEntity addEmployee(Employee newEmployee) throws SQLException  {
        
        String query = "INSERT INTO `shipservhr`.`employees` (`name`, `address`, `contactNumber`) VALUES (?,?,?)";
        
        Connection conn = connectionFactory.getConnection();
        
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        ps.setString(1, newEmployee.getName());
        ps.setString(2, newEmployee.getAddress());
        ps.setString(3, newEmployee.getContactNumber());
        
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        long insertedId = rs.getLong(1);
                
        EmployeeEntity entity = getEmployee(insertedId);
        
        
        try{
        return entity;
	    } catch (Exception e) {
	    	logger.error(e.getMessage(),e);
	    } finally {        
	    	conn.close();
	    }
	
	return null;
    }
    
    /* (non-Javadoc)
	 * @see com.shipserv.hr.dao.EmployeeDao#updateEmployee(com.shipserv.hr.domain.EmployeeEntity)
	 */
    @Override
	public EmployeeEntity updateEmployee(Employee employee) throws SQLException  {
    	
    	logger.debug("Updating employee with ID = " + employee.getId());
        
        String query = "update employees set name=?, address=?, contactNumber=? where id=?";
        
        Connection conn = connectionFactory.getConnection();
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getAddress());
        ps.setString(3, employee.getContactNumber());
        ps.setLong(4, employee.getId());
        
        ps.executeUpdate();
                
        EmployeeEntity entity = getEmployee(employee.getId());
                
        try{
        return entity;
	    } catch (Exception e) {
	    	logger.error(e.getMessage(),e);
	    } finally {        
	    	conn.close();
	    }

        return null;
    }
    
    /* (non-Javadoc)
	 * @see com.shipserv.hr.dao.EmployeeDao#deleteEmployee(long)
	 */
    @Override
	public EmployeeEntity deleteEmployee(long employeeId) throws SQLException  {
        
        String query = "update employees set status='INACTIVE' where id=?";
        
        Connection conn = connectionFactory.getConnection();
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setLong(1, employeeId);
        
        ps.execute();
        
        EmployeeEntity entity = getEmployee(employeeId);

        try{
	        return entity;
	    } catch (Exception e) {
	    	logger.error(e.getMessage(),e);
	    } finally {        
	    	conn.close();
	    }
	
        return null;
	}
    
    private List<EmployeeEntity> mapAsList(ResultSet rs) throws SQLException {
        
        List<EmployeeEntity> employees = new ArrayList<>();
        
        while(rs.next()) {
            EmployeeEntity employee = new EmployeeEntity();
            
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setAddress(rs.getString("address"));
            employee.setContactNumber(rs.getString("contactNumber"));
            employee.setStatus(rs.getString("status"));
            
            employees.add(employee);
        }
        
        return employees;
    }
    
    private EmployeeEntity mapAsObject(ResultSet rs) throws SQLException {
        
        rs.next();
        
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(rs.getLong("id"));
        employee.setName(rs.getString("name"));
        employee.setAddress(rs.getString("address"));
        employee.setContactNumber(rs.getString("contactNumber"));
        employee.setStatus(rs.getString("status"));
        
        logger.debug("Employee ID: " + employee.getId() + "\n" +
        			 "Employee Name: " + employee.getName());
        
        return employee;
    }

}
