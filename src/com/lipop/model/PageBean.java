package com.lipop.model;
/**
 * 分页实体
 * @author zhonglunsheng
 *
 */
public class PageBean {
		private int page; //第几页
		private int pageSize;
		private int start;
		
		
		public PageBean(int page, int pageSize) {
			super();
			this.page = page;
			this.pageSize = pageSize;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getStart() {
			start = (page-1)*pageSize;
			return start;
		}
		
}
