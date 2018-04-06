/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.cariaso.hr.dao;

import mp.cariaso.hr.entity.UserEntity;

import java.sql.SQLException;

/**
 *
 * @author mpcariaso
 */
public interface UserDao {

    UserEntity getUserByUserName(String userName) throws SQLException;

}
