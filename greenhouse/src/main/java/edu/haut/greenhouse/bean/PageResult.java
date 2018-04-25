package edu.haut.greenhouse.bean;

import java.util.Arrays;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * 与分页助手结合使用
 * @author user1
 *
 */
public class PageResult {

	private long total;
	
	private List<?> rows;
	
	private int pageNum;
	
	private int pages;
	
	private int pageSize;
	
	 //第一页
    private int firstPage;
    //前一页
    private int prePage;
    //下一页
    private int nextPage;
    //最后一页
    private int lastPage;

    //是否为第一页
    private boolean isFirstPage = false;
    //是否为最后一页
    private boolean isLastPage = false;
    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;
    //导航页码数
    private int navigatePages;
    //所有导航页号
    private int[] navigatepageNums;
    
    
    public PageResult(PageInfo pageInfo, List list) {
    	this.total = pageInfo.getTotal();
    	this.pageNum = pageInfo.getPageNum();
    	this.pages = pageInfo.getPages();
    	this.pageSize = pageInfo.getPageSize();
    	this.firstPage = pageInfo.getFirstPage();
    	this.prePage = pageInfo.getPrePage();
    	this.nextPage = pageInfo.getNextPage();
    	this.lastPage = pageInfo.getLastPage();
    	this.isFirstPage = pageInfo.isIsFirstPage();
    	this.isLastPage = pageInfo.isIsLastPage();
    	this.hasNextPage = pageInfo.isHasNextPage();
    	this.hasPreviousPage = pageInfo.isHasPreviousPage();
    	this.rows = list;
    	
    }

	public long getTotal() {
		return total;
	}


	public void setTotal(long total) {
		this.total = total;
	}


	public List<?> getRows() {
		return rows;
	}


	public void setRows(List<?> rows) {
		this.rows = rows;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getPages() {
		return pages;
	}


	public void setPages(int pages) {
		this.pages = pages;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getFirstPage() {
		return firstPage;
	}


	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}


	public int getPrePage() {
		return prePage;
	}


	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}


	public int getLastPage() {
		return lastPage;
	}


	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}


	public boolean isFirstPage() {
		return isFirstPage;
	}


	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}


	public boolean isLastPage() {
		return isLastPage;
	}


	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}


	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}


	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}


	public boolean isHasNextPage() {
		return hasNextPage;
	}


	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}


	public int getNavigatePages() {
		return navigatePages;
	}


	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}


	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}


	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	@Override
	public String toString() {
		return "PageResult [total=" + total + ", rows=" + rows + ", pageNum=" + pageNum + ", pages=" + pages
				+ ", pageSize=" + pageSize + ", firstPage=" + firstPage + ", prePage=" + prePage + ", nextPage="
				+ nextPage + ", lastPage=" + lastPage + ", isFirstPage=" + isFirstPage + ", isLastPage=" + isLastPage
				+ ", hasPreviousPage=" + hasPreviousPage + ", hasNextPage=" + hasNextPage + ", navigatePages="
				+ navigatePages + ", navigatepageNums=" + Arrays.toString(navigatepageNums) + "]";
	}
    
    
}
