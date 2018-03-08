package com.itheima.Constant;

import org.apache.commons.dbutils.ResultSetHandler;

public class Constant {
//	激活状态
	public static final int USER_ACTIVED = 1;
	public static final int USER_NOT_ACTIVED = 0;
//	商品类别
	public static final String STORE_CATEGORY_KEY = "store_category_key";
//	热门商品和最新商品
	public static final int HOT_PRODUCT = 1;
	public static final int NOT_HOT_PRODUCT = 0;
//	每页商品个数
	public static final int PAGE_PRODUCT_SIZE = 12;
//	订单状态
	public static final int UN_PAY = 0;
	public static final int WAIT_SEND_GOODS = 1;
	public static final int WAIT_CONFIRM_GOODS = 2;
	public static final int FINISHED = 3;
	public static final int PAGE_ORDER_SIZE = 2;
	
	
}
