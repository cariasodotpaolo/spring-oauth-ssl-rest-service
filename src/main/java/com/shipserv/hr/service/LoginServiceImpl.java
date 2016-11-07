/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shipserv.hr.service;

import com.shipserv.hr.dao.UserDao;
import com.shipserv.hr.entity.UserEntity;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mpcariaso
 */
@Service
public class LoginServiceImpl implements LoginService {
    
    @Autowired
    private UserDao userDao;
    
    @Override
    public UserEntity login(String userName) throws SQLException  {
        
        return userDao.getUserByUserName(userName);
    }
    
}
