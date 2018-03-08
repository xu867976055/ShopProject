package com.itheima.dao;

import java.util.List;

import com.itheima.bean.Product;

public interface ProductDao {
	
	List<Product> hotProduct() throws Exception;
	List<Product> newProduct() throws Exception;
	Product findByPid(String pid) throws Exception;
	int findAllProduct(String cid) throws Exception;
	List<Product> selectLimit(String cid ,int a, int b) throws Exception;
	int findAllProduct() throws Exception;
	List<Product> findByPage(int a, int b) throws Exception;
	void add(Product product) throws Exception;
	void delProduct(String pid) throws Exception;
	void updateProduct(Product product) throws Exception;
}
