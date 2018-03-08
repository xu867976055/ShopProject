package com.itheima.dao;

import java.util.List;

import com.itheima.bean.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	List<Category> findCategory() throws Exception;

	void addCategory(Category category) throws Exception;
}
