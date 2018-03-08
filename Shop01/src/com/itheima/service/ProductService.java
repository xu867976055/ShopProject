package com.itheima.service;

import java.util.List;

import com.itheima.bean.Category;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;


public interface ProductService {
	
	/**
	 * 查找热门商品,返回list集合
	 * @return
	 * @throws Exception
	 */
	List<Product> hotProduct() throws Exception;
	
	/**
	 * 查找最新商品,返回list集合
	 * @return
	 * @throws Exception
	 */
	List<Product> newProduct() throws Exception;
	
	/**
	 * 根据pid查找商品信息
	 * @return
	 * @throws Exception
	 */
	Product findByPid(String pid) throws Exception;

	/**
	 * 根据cid,curpage分页查找商品
	 * @return
	 * @throws Exception
	 */
	PageBean<Product> findByPage(int curPage, String cid) throws Exception;

	PageBean<Product> findAllProduct(int curPage) throws Exception;

	void add(Product product) throws Exception;

	void delProduct(String pid) throws Exception;

	void updateProduct(Product product) throws Exception;


	
	
}
