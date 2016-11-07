/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shipserv.hr.dao;

import com.shipserv.hr.datasource.ConnectionFactory;
import com.shipserv.hr.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author paolo
 */
@Repository
public class ProductJdbcDao implements ProductDao {
    
    @Autowired
    private ConnectionFactory connectionFactory;
     
    @Override
    public List<Product> getProducts(String category) throws SQLException { 
        
        String query = "";
        
        if(category == null || category.isEmpty() || "ALL".equals(category.toUpperCase())) {
            query = "select * from products";
        }
        else {
            query = "select * from products where category='" + category + "'";
        }
       
        Connection conn = connectionFactory.getConnection();
        
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        return mapAsList(rs);
    }
    
    @Override
    public Product getProductById(int id) throws SQLException  {
        
        String query = "select * from products where id=?";
        
        Connection conn = connectionFactory.getConnection();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        return mapAsObject(rs);
    }
    
    @Override
    public Product addProduct(Product newProduct) throws SQLException  {
        
        String query = "insert into products values (?,?,?,?,?,?)";
        
        Connection conn = connectionFactory.getConnection();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, newProduct.getId());
        ps.setString(2, newProduct.getCode());
        ps.setDouble(3, newProduct.getPrice());
        ps.setString(4, newProduct.getSize());
        ps.setInt(5, newProduct.getStock());
        ps.setString(6, newProduct.getCategory());
        
        boolean success = ps.execute();
        
        if (success) {
            return mapAsObject(ps.getResultSet());
        } else
            return null;
    }
    
    private List<Product> mapAsList(ResultSet rs) throws SQLException {
        
        List<Product> products = new ArrayList<>();
        
        while(rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setCode(rs.getString("code"));
            product.setPrice(rs.getDouble("price"));
            product.setSize(rs.getString("size"));
            product.setStock(rs.getInt("stock"));
            product.setCategory(rs.getString("category"));
            products.add(product);
        }
        
        return products;
    }
    
    private Product mapAsObject(ResultSet rs) throws SQLException {
        
        rs.next();
        
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setCode(rs.getString("code"));
        product.setPrice(rs.getDouble("price"));
        product.setSize(rs.getString("size"));
        product.setStock(rs.getInt("stock"));
        product.setCategory(rs.getString("category"));
            
        return product;
    }
    
    
    
}
