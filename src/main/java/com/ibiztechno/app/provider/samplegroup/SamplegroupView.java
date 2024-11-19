package com.ibiztechno.app.provider.samplegroup;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

public class SamplegroupView {
	Long ID;
	String CompanyID;
	@NotEmpty
	String GroupCode;
	String GroupShort;
	@NotEmpty
	String GroupDesc;
	boolean GroupStatus;
	boolean RecStatus;
	String UserID;
	String RemoteAddr;
	
	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
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
	
	public boolean isGroupStatus() {
		return GroupStatus;
	}

	public void setGroupStatus(boolean groupStatus) {
		GroupStatus = groupStatus;
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