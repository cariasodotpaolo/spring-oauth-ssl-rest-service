/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shipserv.hr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shipserv.hr.datasource.ConnectionFactory;
import com.shipserv.hr.entity.UserEntity;

/**
 *
 * @author paolo
 */
@Repository
public class UserJdbcDao implements UserDao {
    
	private static final Logger logger = LoggerFactory.getLogger(UserJdbcDao.class);
	
    @Autowired
    private ConnectionFactory connectionFactory;
    
    private String ROLE_DELIMITER="|";
 
    
    @Override
    public UserEntity getUserByUserName(String userName) throws SQLException {
        
        String query = "select * from users where userName=?";
        
        Connection conn = connectionFactory.getConnection();
        
        if (connectionFactory != null) logger.debug("connectionFactory exists");
        else logger.debug("connectionFactory is NULL");
        
        if( conn == null) logger.error("Connection is NULL!");
        
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, userName);
        
        ResultSet rs = ps.executeQuery();
        
        boolean hasNext = rs.next();
        
        UserEntity user = new UserEntity();
        List<String> roles = new ArrayList<>();
        
        if (hasNext) {        
        user.setUserId(rs.getInt("id"));
        user.setFullName(rs.getString("fullName"));
        user.setUserName(rs.getString("userName"));
        
        StringTokenizer roleTokenizer = new StringTokenizer(rs.getString("roles"), ROLE_DELIMITER);
        
	        while(roleTokenizer.hasMoreTokens()) {
	        	roles.add(roleTokenizer.nextToken());
	        }  
        
	    user.setRoles(roles);
        }
        
        logger.debug("User ID: " + user.getUserId() + "\n" +
        		     "UserName: " + user.getUserName() + "\n" +
        			 "User Roles: " + user.getRoles());
        return user;
    }
}
