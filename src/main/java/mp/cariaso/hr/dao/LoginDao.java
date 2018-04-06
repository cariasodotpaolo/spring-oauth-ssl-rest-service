/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.cariaso.hr.dao;

import mp.cariaso.hr.domain.User;

import java.sql.SQLException;

/**
 *
 * @author mpcariaso
 */
public interface LoginDao {

    User login(String userName, String password) throws SQLException ;

}
