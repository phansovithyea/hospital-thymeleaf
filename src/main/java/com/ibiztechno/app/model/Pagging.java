package com.ibiztechno.app.model;

import java.util.List;
import java.util.Map;

public class Pagging {
	Integer pageNumber = 1;
	Integer pageSize = 10;
	Integer PageCount = 1;
	Integer totalRecord = 0;
	String search = "";
	Integer backNumber=1;
	Integer nextNumber=1;
	
	List<Map<String, Object>> datas;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		return totalRecord / pageSize;
	}

	public void setPageCount(Integer pageCount) {
		PageCount = pageCount;
	}

	public Integer getBackNumber() {
		return backNumber;
	}

	public void setBackNumber(Integer backNumber) {
		this.backNumber = backNumber;
	}

	public Integer getNextNumber() {
		return nextNumber;
	}

	public void setNextNumber(Integer nextNumber) {
		this.nextNumber = nextNumber;
	}

}
