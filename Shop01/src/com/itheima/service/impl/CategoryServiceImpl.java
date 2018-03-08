package com.itheima.service.impl;

import java.util.List;

import com.itheima.Constant.Constant;
import com.itheima.bean.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.service.CategoryService;
import com.itheima.utils.JedisUtils;
import com.itheima.utils.JsonUtil;
import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public String findAll() throws Exception {
	
	/*	String data = null;
		Jedis jedis = null;
		try {
//		获得jedis对象
			jedis = JedisUtils.getJedis();
//		从redis中获取数据
			data = getFromRedis(jedis);
			if(data != null){
				System.out.println("redis服务器有数据，从redis中获得");
			}else{
				System.out.println("resdis第一次没数据，先从mysql获取");
				data = getFromMysql();
				saveToRedis(jedis,data);
			}
		} catch (Exception e) {
			System.out.println("服务器没开，从mysql获取");
			data = getFromMysql();
			e.printStackTrace();
		}finally{
			JedisUtils.closeJedis(jedis);
		}*/
		String data = getFromMysql();
		return data;
	}
	

	private void saveToRedis(Jedis jedis, String data) {
		if(jedis != null){
			jedis.set(Constant.STORE_CATEGORY_KEY, data);
		}
	}


	//	从redis中获取数据
	private String getFromRedis(Jedis jedis) {
		if(jedis != null){
			String data =jedis.get(Constant.STORE_CATEGORY_KEY);
			return data;
		}
		return null;
	}

	private String getFromMysql() throws Exception {
		
		CategoryDao dao = new CategoryDaoImpl();
		List<Category> list = dao.findAll();
		String data = JsonUtil.list2json(list);
		return data;
	}


	@Override
	public List<Category> findCategory() throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		List<Category> list = dao.findCategory();
		return list;
	}


	@Override
	public void addCategory(Category category) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		//调用dao,插入数据库
		dao.addCategory(category);
		//更新redis缓存的数据(直接清空),这样会直接从mysql中获取
		Jedis jedis = null;
		jedis = JedisUtils.getJedis();
		if(jedis!=null){
			jedis.del(Constant.STORE_CATEGORY_KEY);
		}
		
	}

}
