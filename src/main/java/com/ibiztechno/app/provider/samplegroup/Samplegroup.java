package com.ibiztechno.app.provider.samplegroup;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.springframework.format.annotation.DateTimeFormat;

public class Samplegroup {
	Long ID;
	@NotEmpty
	String GroupCode;
	String GroupShort;
	@NotEmpty
	String GroupDesc;
	boolean RecStatus = false;
	String UserID;
	String RemoteAddr;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getGroupCode() {
		return GroupCode;
	}
	public void setGroupCode(String groupCode) {
		GroupCode = groupCode;
	}
	
	public String getGroupShort() {
		return GroupShort;
	}
	public void setGroupShort(String groupShort) {
		GroupShort = groupShort;
	}
	public String getGroupDesc() {
		return GroupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		GroupDesc = groupDesc;
	}
	public boolean isRecStatus() {
		return RecStatus;
	}
	public void setRecStatus(boolean recStatus) {
		RecStatus = recStatus;
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
