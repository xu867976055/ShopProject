package com.itheima.service.impl;

import java.util.List;

import com.itheima.Constant.Constant;
import com.itheima.bean.Category;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> hotProduct() throws Exception {
		ProductDao dao = new ProductDaoImpl();
		return dao.hotProduct();
	}

	@Override
	public List<Product> newProduct() throws Exception {
		ProductDao dao = new ProductDaoImpl();
		return dao.newProduct();
	}

	@Override
	public Product findByPid(String pid) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		Product product = dao.findByPid(pid);
		return product;
	}

	@Override
	public PageBean<Product> findByPage(int curPage, String cid) throws Exception {
		PageBean<Product> pageBean = new PageBean<>();
//		获取当前类别下商品总个数
		ProductDao dao = new ProductDaoImpl();
		int totalSize = dao.findAllProduct(cid);
//		每页商品个数
		int pageSize = Constant.PAGE_PRODUCT_SIZE;
//		获取总页数
		int totalPage = 0;
		if(totalSize % pageSize==0 ){
			totalPage = totalSize/pageSize;
		}else{
			totalPage = totalSize/pageSize+1;
		}
//		添加当前页数
		pageBean.setCurPage(curPage);
//		添加每页商品个数
		pageBean.setPageSize(pageSize);
//		添加总页数
		pageBean.setTotalPage(totalPage);
//		添加商品总个数
		pageBean.setTotalSize(totalSize);
		
//		商品list
		int b = pageSize; 
		int a = (curPage-1)*b;
		
		List<Product> list = dao.selectLimit(cid,a,b);
//		添加商品list
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public PageBean<Product> findAllProduct(int curPage) throws Exception {
		PageBean<Product> pageBean = new PageBean<>();
		int pageSize = Constant.PAGE_PRODUCT_SIZE;
		ProductDao dao = new ProductDaoImpl();
		int totalSize = dao.findAllProduct();
		int totalPage = 0;
		if(totalSize % pageSize == 0){
			totalPage = totalSize/pageSize;
		}else{
			totalPage = totalSize/pageSize+1;
		}
		int b = pageSize;
		int a = (curPage-1)*b;
		List<Product> list = dao.findByPage(a,b);
		
		pageBean.setCurPage(curPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalPage(totalPage);
		pageBean.setTotalSize(totalSize);
		pageBean.setList(list);
		
		return pageBean;
	}

	@Override
	public void add(Product product) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		dao.add(product);
	}

	@Override
	public void delProduct(String pid) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		dao.delProduct(pid);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		dao.updateProduct(product);
	}


	

}
