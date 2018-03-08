package com.itheima.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itheima.bean.Category;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UploadUtils;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet{
	
	/**
	 * 查询所有商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String findAllProduct(HttpServletRequest request,HttpServletResponse response){
		try {
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			ProductService service = new ProductServiceImpl();
			PageBean<Product> pageBean = service.findAllProduct(curPage);
			request.setAttribute("pageBean", pageBean);
			return "/admin/product/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 查询类别
	 * @param request
	 * @param response
	 * @return
	 */
	public String addUI(HttpServletRequest request,HttpServletResponse response){
		try {
			CategoryService service = new CategoryServiceImpl();
			List<Category> list = service.findCategory();
			request.setAttribute("list", list);
			return "/admin/product/add.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	
	/**
	 * 删除商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String delProduct(HttpServletRequest request,HttpServletResponse response){
		try {
			String pid = request.getParameter("pid");
//			System.out.println(pid);
			ProductService service = new ProductServiceImpl();
			service.delProduct(pid);
			
			response.sendRedirect(request.getContextPath()+"/adminProductServlet?method=findAllProduct&curPage=1");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 编辑商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String editProduct(HttpServletRequest request,HttpServletResponse response){
		try {
//			先根据pid查询product存到域对象中展示出来
			String pid = request.getParameter("pid");
			ProductService service = new ProductServiceImpl();
			CategoryService service2 = new CategoryServiceImpl();
			
			Product product = service.findByPid(pid);
			List<Category> list = service2.findCategory();
			
			request.getSession().setAttribute("list", list);
			request.getSession().setAttribute("p", product);
			
			response.sendRedirect(request.getContextPath()+"/admin/product/edit.jsp");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 更新商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String updateProduct(HttpServletRequest request,HttpServletResponse response){
		//整体思路: 1, 获得请求参数, 把请求参数封装成Product对象,更新数据库,注意图片的路径要一致,这样就可以覆盖了
				//2. 保存商品的图片到服务器
				try {
					
					Product product = new Product();
					Map<String, Object> map = new HashMap<String, Object>();
					DiskFileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);
					List<FileItem> list = upload.parseRequest(request);
					String path = "";
					//4.遍历文件项集合
					for (FileItem fileItem : list) {
						if(fileItem.isFormField()){
//							不是文件
							//4.1获得表单里面name的值,(name的值应该和javaBean的属性是一致)
							String fieldName = fileItem.getFieldName();
//							//4.2获得value
							String value = fileItem.getString("utf-8");
							//4.3添加到map集合中
							map.put(fieldName, value);
							System.out.println(fieldName);
							path += map.get("old_pass");
						}else{
//							
							String fieldName = fileItem.getFieldName();
							map.put(fieldName, path);
//							保存文件到服务器
//							6.1获得文件流
							InputStream is = fileItem.getInputStream();
							//d, 根据文件夹和文件创建输出流
							OutputStream os =  new FileOutputStream(new File(path));
//							删除临时文件
							fileItem.delete();
							
							IOUtils.copy(is, os);
							IOUtils.closeQuietly(is);
							
						}
						
					}
					//7, 把map的数据封装到product对象
					BeanUtils.populate(product, map);
					//7.1 手到封装pid, pdate, category
					product.setPid(UUIDUtils.getId());
					product.setPdate(new Date());
					Category category = new Category();
					String cid = (String) map.get("cid");
					System.out.println("cid"+cid);
					category.setCid(cid);
					product.setCategory(category);
//					8.调用业务把product添加到数据库
					ProductService service = new ProductServiceImpl();
					service.updateProduct(product);
					
//					9.再查询展示
					response.sendRedirect(request.getContextPath()+"/adminProductServlet?method=findAllProduct&curPage=1");
					
					
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		
//		System.out.println("pid:"+pid+"---"+"method:"+method);
		return null;
	}
}