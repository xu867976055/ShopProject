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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itheima.bean.Category;
import com.itheima.bean.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UploadUtils;
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//整体思路: 1, 获得请求参数, 把请求参数封装成Product对象,最终要保存到数据库 
		//2. 保存商品的图片到服务器
		try {
			
			Product product = new Product();
			Map<String, Object> map = new HashMap<String, Object>();
			//1.创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2.创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//3.解析请求,获得文件项集合
			List<FileItem> list = upload.parseRequest(request);
			//4.遍历文件项集合
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){
//					不是文件
					//4.1获得表单里面name的值,(name的值应该和javaBean的属性是一致)
					String fieldName = fileItem.getFieldName();
//					//4.2获得value
					String value = fileItem.getString("utf-8");
					//4.3添加到map集合中
					map.put(fieldName, value);
				}else{
//					是文件a.把文件路径封装到Product对象b.保存文件到服务器
//					把文件路径封装到Product  products/8/9/06F518F53D314209ACD0A1C419E28168.jpg)封装 到Product对象  2. 保存文件到服务器
					
//					5.1获取文件名
					String name = fileItem.getName();
//					5.2获取真实文件名(去掉盘符)
					String realName = UploadUtils.getRealName(name);
//					5.3获得uuid名字
					String uuidName = UploadUtils.getUUIDName(realName);
//					5.4获得两层目录
					String dir = UploadUtils.getDir();
//					5.5获得表单name属性值
					String fieldName = fileItem.getFieldName();
//					(pimage,路径)
					map.put(fieldName, "products"+dir+"/"+uuidName);
					
//					保存文件到服务器
//					6.1获得文件流
					InputStream is = fileItem.getInputStream();
//					6.2动态获得products的绝对路径
//					 E:\worksoft\tomcat\apache-tomcat-7.0.52_28_32\webapps\store_28\products
					String realPath = request.getServletContext().getRealPath("/products");
//					6.3在products里面生成两层目录
					File fileDir = new File(realPath, dir);
					if(!fileDir.exists()){
						fileDir.mkdirs();
					}
					
					//d, 根据文件夹和文件创建输出流
					OutputStream os =  new FileOutputStream(new File(fileDir, uuidName));
//					删除临时文件
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
			
//			System.out.println(cid);
			category.setCid(cid);
			product.setCategory(category);
//			8.调用业务把product添加到数据库
			ProductService service = new ProductServiceImpl();
			service.add(product);
			
//			9.再查询展示
			response.sendRedirect(request.getContextPath()+"/adminProductServlet?method=findAllProduct&curPage=1");
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
