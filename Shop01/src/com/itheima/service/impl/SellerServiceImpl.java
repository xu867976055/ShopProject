package com.itheima.service.impl;

import com.itheima.bean.Seller;
import com.itheima.dao.SellerDao;
import com.itheima.dao.impl.SellerDaoImpl;
import com.itheima.service.SellerService;

public class SellerServiceImpl implements SellerService {

	@Override
	public Seller findSeller(String username, String password) throws Exception {
		SellerDao dao = new SellerDaoImpl();
		Seller seller = dao.findSeller(username,password);
		return seller;
	}

}
