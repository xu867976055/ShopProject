package com.itheima.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.bean.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.utils.C3P0Utils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<>(Category.class));
	}

	@Override
	public List<Category> findCategory() throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<>(Category.class));
		return list;
	}

	@Override
	public void addCategory(Category category) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into category values(?,?)";
		runner.update(sql, category.getCid(),category.getCname());
		
	}

}
