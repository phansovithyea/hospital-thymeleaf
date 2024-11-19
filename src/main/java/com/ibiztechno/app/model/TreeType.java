package com.ibiztechno.app.model;

public class TreeType {
	String FormCode;
	String TreeCode;
	String TreeDesc;
	String DefCriteria;
	String BrCriteria;
	String SpecViewName;
	String ListType;
	String LinkListCode;
	String SpecReportCode;
	String LanType;
	String Criteria;
    String OrderBy;
    Integer PageNumber=1;
    String UserID;
	String RemoteAddr;
	
	public String getFormCode() {
		return FormCode;
	}
	public void setFormCode(String formCode) {
		FormCode = formCode;
	}
	public String getTreeCode() {
		return TreeCode;
	}
	public void setTreeCode(String treeCode) {
		TreeCode = treeCode;
	}
	public String getTreeDesc() {
		return TreeDesc;
	}
	public void setTreeDesc(String treeDesc) {
		TreeDesc = treeDesc;
	}
	public String getDefCriteria() {
		return DefCriteria;
	}
	public void setDefCriteria(String defCriteria) {
		DefCriteria = defCriteria;
	}
	public String getBrCriteria() {
		return BrCriteria;
	}
	public void setBrCriteria(String brCriteria) {
		BrCriteria = brCriteria;
	}
	public String getSpecViewName() {
		return SpecViewName;
	}
	public void setSpecViewName(String specViewName) {
		SpecViewName = specViewName;
	}
	public String getListType() {
		return ListType;
	}
	public void setListType(String listType) {
		ListType = listType;
	}
	public String getLinkListCode() {
		return LinkListCode;
	}
	public void setLinkListCode(String linkListCode) {
		LinkListCode = linkListCode;
	}
	public String getSpecReportCode() {
		return SpecReportCode;
	}
	public void setSpecReportCode(String specReportCode) {
		SpecReportCode = specReportCode;
	}
	public String getLanType() {
		return LanType;
	}
	public void setLanType(String lanType) {
		LanType = lanType;
	}
	public String getCriteria() {
		return Criteria;
	}
	public void setCriteria(String criteria) {
		Criteria = criteria;
	}
	public String getOrderBy() {
		return OrderBy;
	}
	public void setOrderBy(String orderBy) {
		OrderBy = orderBy;
	}
	public Integer getPageNumber() {
		return PageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		PageNumber = pageNumber;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getRemoteAddr() {
		return RemoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		RemoteAddr = remoteAddr;
	}
}
