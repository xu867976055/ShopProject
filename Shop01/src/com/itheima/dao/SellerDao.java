package com.itheima.dao;

import com.itheima.bean.Seller;

public interface SellerDao {

	Seller findSeller(String username, String password) throws Exception;


}
