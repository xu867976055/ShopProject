package com.itheima.bean;

import java.util.List;

public class PageBean<T> {
	private List<T> list;
	private int curPage;
//	总页数
	private int totalPage;
//	每页个数
	private int pageSize;
//	总数量
	private int totalSize;
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	@Override
	public String toString() {
		return "PageBean [list=" + list + ", curPage=" + curPage + ", totalPage=" + totalPage + ", pageSize=" + pageSize
				+ ", totalSize=" + totalSize + "]";
	}
	
	
}