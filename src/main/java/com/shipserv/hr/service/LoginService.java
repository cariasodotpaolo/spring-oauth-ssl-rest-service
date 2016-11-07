/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shipserv.hr.service;

import com.shipserv.hr.entity.UserEntity;

import java.sql.SQLException;

/**
 *
 * @author mpcariaso
 */
public interface LoginService {

    UserEntity login(String userName) throws SQLException;
    
}
