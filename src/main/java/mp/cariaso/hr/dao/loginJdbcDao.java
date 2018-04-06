/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.cariaso.hr.dao;

import mp.cariaso.hr.datasource.ConnectionFactory;
import mp.cariaso.hr.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author paolo
 */
@Repository
public class loginJdbcDao implements LoginDao {

    @Autowired
    private ConnectionFactory connectionFactory;


    @Override
    public User login(String userName, String password) throws SQLException {

        String query = "select * from users where userName=? and password=?";

        Connection conn = connectionFactory.getConnection();

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, userName);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        rs.next();

        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setFullName(rs.getString("fullName"));
        user.setUserName(rs.getString("userName"));

        return user;
    }
}
