/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.cariaso.hr.service;

import mp.cariaso.hr.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mpcariaso
 */
public interface ProductService {

    Product addProduct(Product newProduct) throws SQLException ;

    Product getProductById(int productId) throws SQLException ;

    List<Product> getProducts(String category) throws SQLException ;

}
