package com.ibiztechno.app.provider.sample;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

public class Sample {
	@NotEmpty
	String TempCode="";
	String TempShort;
	@NotEmpty
	String TempName="";
	String TempNameU;
	Integer TempOrder;
	String TempRemark;
	String TempPass;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate TempDate;
	//java.sql.Date TempDate;
	boolean TempStatus = false;
	Double TempNumeric;
	Long TempInt;
	Integer TempSmallint;
	BigDecimal TempMoney;
	String TempWebsite;
	String TempType;
	String TypeGroup;
	String TempPhone;
	String TempSSN;
	String TempFax;
	@Email
	String TempEmail;
	String UserID;
	String RemoteAddr;
	
	LocalDateTime CreatedDate;
	String CreatedBy;
	String CreatedFrom;
	LocalDateTime UpdatedDate;
	String UpdatedBy;
	String UpdatedFrom;
	String AuthStatus;
	Integer RecordMod;
	String Facebook;
	String GoogleMap;

	public String getTempCode() {
		return TempCode;
	}

	public void setTempCode(String tempCode) {
		TempCode = tempCode;
	}

	public String getTempShort() {
		return TempShort;
	}

	public void setTempShort(String tempShort) {
		TempShort = tempShort;
	}

	public String getTempName() {
		return TempName;
	}

	public void setTempName(String tempName) {
		TempName = tempName;
	}

	public String getTempNameU() {
		return TempNameU;
	}

	public void setTempNameU(String tempNameU) {
		TempNameU = tempNameU;
	}

	public Integer getTempOrder() {
		return TempOrder;
	}

	public void setTempOrder(Integer tempOrder) {
		TempOrder = tempOrder;
	}

	public String getTempRemark() {
		return TempRemark;
	}

	public void setTempRemark(String tempRemark) {
		TempRemark = tempRemark;
	}

	public String getTempPass() {
		return TempPass;
	}

	public void setTempPass(String tempPass) {
		TempPass = tempPass;
	}

	public LocalDate getTempDate() {
		return TempDate;
	}

	public void setTempDate(LocalDate tempDate) {
		TempDate = tempDate;
	}

	public String getTempWebsite() {
		return TempWebsite;
	}

	public void setTempWebsite(String tempWebsite) {
		TempWebsite = tempWebsite;
	}

	public String getTempType() {
		return TempType;
	}
	public void setTempType(String tempType) {
		TempType = tempType;
	}
	
	public boolean isTempStatus() {
		return TempStatus;
	}

	public void setTempStatus(boolean tempStatus) {
		TempStatus = tempStatus;
	}

	public Double getTempNumeric() {
		return TempNumeric;
	}

	public void setTempNumeric(Double tempNumeric) {
		TempNumeric = tempNumeric;
	}

	public Long getTempInt() {
		return TempInt;
	}

	public void setTempInt(Long tempInt) {
		TempInt = tempInt;
	}

	public Integer getTempSmallint() {
		return TempSmallint;
	}

	public void setTempSmallint(Integer tempSmallint) {
		TempSmallint = tempSmallint;
	}
	
	public BigDecimal getTempMoney() {
		return TempMoney;
	}

	public void setTempMoney(BigDecimal tempMoney) {
		TempMoney = tempMoney;
	}
	

	public String getTypeGroup() {
		return TypeGroup;
	}

	public void setTypeGroup(String typeGroup) {
		TypeGroup = typeGroup;
	}

	public String getTempPhone() {
		return TempPhone;
	}

	public void setTempPhone(String tempPhone) {
		TempPhone = tempPhone;
	}

	public String getTempSSN() {
		return TempSSN;
	}

	public void setTempSSN(String tempSSN) {
		TempSSN = tempSSN;
	}

	public String getTempFax() {
		return TempFax;
	}

	public void setTempFax(String tempFax) {
		TempFax = tempFax;
	}

	public String getTempEmail() {
		return TempEmail;
	}

	public void setTempEmail(String tempEmail) {
		TempEmail = tempEmail;
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

	public LocalDateTime getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		CreatedDate = createdDate;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getCreatedFrom() {
		return CreatedFrom;
	}

	public void setCreatedFrom(String createdFrom) {
		CreatedFrom = createdFrom;
	}

	public LocalDateTime getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public String getUpdatedFrom() {
		return UpdatedFrom;
	}

	public void setUpdatedFrom(String updatedFrom) {
		UpdatedFrom = updatedFrom;
	}

	public String getAuthStatus() {
		return AuthStatus;
	}

	public void setAuthStatus(String authStatus) {
		AuthStatus = authStatus;
	}

	public Integer getRecordMod() {
		return RecordMod;
	}

	public void setRecordMod(Integer recordMod) {
		RecordMod = recordMod;
	}

	public String getFacebook() {
		return Facebook;
	}

	public void setFacebook(String facebook) {
		Facebook = facebook;
	}

	public String getGoogleMap() {
		return GoogleMap;
	}

	public void setGoogleMap(String googleMap) {
		GoogleMap = googleMap;
	}
	
}
