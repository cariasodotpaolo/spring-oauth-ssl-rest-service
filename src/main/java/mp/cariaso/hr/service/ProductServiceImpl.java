/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.cariaso.hr.service;

import mp.cariaso.hr.dao.ProductDao;
import mp.cariaso.hr.domain.Product;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mpcariaso
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;


    @Override
    public List<Product> getProducts(String category)  throws SQLException {

        return productDao.getProducts(category);
    }

    @Override
    public Product getProductById(int productId) throws SQLException  {

        return productDao.getProductById(productId);
    }

    @Override
    public Product addProduct(Product newProduct) throws SQLException  {

        return productDao.addProduct(newProduct);
    }
}
