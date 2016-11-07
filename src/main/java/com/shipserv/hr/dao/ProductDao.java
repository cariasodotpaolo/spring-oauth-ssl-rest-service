/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shipserv.hr.dao;

import com.shipserv.hr.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mpcariaso
 */
public interface ProductDao {

    Product addProduct(Product newProduct) throws SQLException ;

    Product getProductById(int id) throws SQLException ;

    List<Product> getProducts(String category) throws SQLException ;

}
