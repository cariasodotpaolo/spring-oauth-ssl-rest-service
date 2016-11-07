/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shipserv.hr.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.shipserv.hr.security.oauth.UserDetailsServiceImpl;

/**
 *
 * @author mpcariaso
 */
@Repository
public class MySqlConnectionFactory implements ConnectionFactory {
    
	private static final Logger logger = LoggerFactory.getLogger(MySqlConnectionFactory.class);
	
    @Resource
    private DataSource dataSource;
    
    @Override
    public Connection getConnection() throws SQLException {
        
    	if(getDataSource() == null) {
    		logger.debug("datasource is NULL");
    	}
    	
        return getDataSource().getConnection();
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
