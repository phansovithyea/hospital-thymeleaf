package com.ibiztechno.app.provider.sampletype;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.springframework.format.annotation.DateTimeFormat;

public class Sampletype {
	@NotEmpty
	String TypeCode;
	String TypeShort;
	@NotEmpty
	String TypeDesc;
	boolean TypeStatus=true;
	String TypeGroup;
	
	public String getTypeCode() {
		return TypeCode;
	}
	public void setTypeCode(String typeCode) {
		TypeCode = typeCode;
	}
	
	public String getTypeShort() {
		return TypeShort;
	}
	public void setTypeShort(String typeShort) {
		TypeShort = typeShort;
	}
	public String getTypeDesc() {
		return TypeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		TypeDesc = typeDesc;
	}
	public boolean isTypeStatus() {
		return TypeStatus;
	}
	public void setTypeStatus(boolean typeStatus) {
		TypeStatus = typeStatus;
	}
	public String getTypeGroup() {
		return TypeGroup;
	}
	public void setTypeGroup(String typeGroup) {
		TypeGroup = typeGroup;
	}

	
}
