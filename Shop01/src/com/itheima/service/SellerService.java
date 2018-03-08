package com.itheima.service;

import com.itheima.bean.Seller;

public interface SellerService {

	Seller findSeller(String username, String password) throws Exception;

}
