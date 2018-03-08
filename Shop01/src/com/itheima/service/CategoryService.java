package com.itheima.service;

import java.util.List;

import com.itheima.bean.Category;

public interface CategoryService {
	
	/**
	 * 查找所有分类
	 * @return
	 * @throws Exception
	 */
	String findAll() throws Exception;

	List<Category> findCategory() throws Exception;

	void addCategory(Category category) throws Exception;
}
