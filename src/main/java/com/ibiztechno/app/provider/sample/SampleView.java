package com.ibiztechno.app.provider.sample;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class SampleView {
	String CompanyID;
	@NotEmpty
	String TempCode;
	@NotEmpty
	String TempName;
	String TempNameK;
	String TempOrder;
	String BranchAddress;
	String TempPhone;
	String TempSSN;
	String TempFax;
	@Email
	String TempEmail;
	Long TempInt;
	Integer TempSmallint;
	BigDecimal TempMoney;
	String TempRemark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate TempDate;
	//java.sql.Date TempDate;
	String Province;
	boolean TempStatus;
	double TempNumeric;
	LocalDateTime CreatedDate;
	String CreatedBy;
//	@DateTimeFormat(iso =  ISO.DATE_TIME)
	LocalDateTime UpdatedDate;
	String UpdatedBy;
	Boolean ExportFlag;
	String BranchFile;
	String TempType;
	String TypeDesc;
	String TypeGroup;
	String GroupDesc;
	String TempWebsite;
	String WebLocalAddr;
	String TempShort;

	String TempPass;
	java.sql.Date ExtStartDate;
	String ConTempCode;
	Long ID;
	String TempNameU;
	String AuthStatus;
	String provincee;
	String provincek;
	String districte;
	String districtk;
	String BranchCode;
	String Search;
	String ComName;
	String ComNameKh;
	Integer RecordMod;
	String CreatedFrom;
	String UpdatedFrom;
	String Facebook;
	String GoogleMap;
	
	//File
	String TFileID;
	String FileType;
	String FilePath;
	byte[] FileImage;
	String ContentType;
	
	public String getCompanyID() {
		return CompanyID;
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
		if (tempNumeric==null) {
			TempNumeric =0;
		}
		else {
			TempNumeric = tempNumeric;
		}
		
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getTempCode() {
		return TempCode;
	}

	public void setTempCode(String tempCode) {
		TempCode = tempCode;
	}

	public String getTempName() {
		return TempName;
	}

	public void setTempName(String tempName) {
		TempName = tempName;
	}

	public String getTempNameK() {
		return TempNameK;
	}

	public void setTempNameK(String tempNameK) {
		TempNameK = tempNameK;
	}

	public String getTempOrder() {
		return TempOrder;
	}

	public void setTempOrder(String tempOrder) {
		TempOrder = tempOrder;
	}

	public String getBranchAddress() {
		return BranchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		BranchAddress = branchAddress;
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

	public String getTempRemark() {
		return TempRemark;
	}

	public void setTempRemark(String tempRemark) {
		TempRemark = tempRemark;
	}

	public LocalDate getTempDate() {
		return TempDate;
	}

	public void setTempDate(LocalDate tempDate) {
		TempDate = tempDate;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
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

	public Boolean getExportFlag() {
		return ExportFlag;
	}

	public void setExportFlag(Boolean exportFlag) {
		ExportFlag = exportFlag;
	}

	public String getBranchFile() {
		return BranchFile;
	}

	public void setBranchFile(String branchFile) {
		BranchFile = branchFile;
	}

	public String getTempType() {
		return TempType;
	}

	public void setTempType(String tempType) {
		TempType = tempType;
	}

	public String getTypeGroup() {
		return TypeGroup;
	}

	public void setTypeGroup(String typeGroup) {
		TypeGroup = typeGroup;
	}

	public String getTempWebsite() {
		return TempWebsite;
	}

	public void setTempWebsite(String tempWebsite) {
		TempWebsite = tempWebsite;
	}

	public String getWebLocalAddr() {
		return WebLocalAddr;
	}

	public void setWebLocalAddr(String webLocalAddr) {
		WebLocalAddr = webLocalAddr;
	}

	public String getTempShort() {
		return TempShort;
	}

	public void setTempShort(String tempShort) {
		TempShort = tempShort;
	}

	public String getCreatedFrom() {
		return CreatedFrom;
	}

	public void setCreatedFrom(String createdFrom) {
		CreatedFrom = createdFrom;
	}

	public String getUpdatedFrom() {
		return UpdatedFrom;
	}

	public void setUpdatedFrom(String updatedFrom) {
		UpdatedFrom = updatedFrom;
	}

	public String getTempPass() {
		return TempPass;
	}

	public void setTempPass(String tempPass) {
		TempPass = tempPass;
	}

	public java.sql.Date getExtStartDate() {
		return ExtStartDate;
	}

	public void setExtStartDate(java.sql.Date extStartDate) {
		ExtStartDate = extStartDate;
	}

	public String getConTempCode() {
		return ConTempCode;
	}

	public void setConTempCode(String conTempCode) {
		ConTempCode = conTempCode;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getTempNameU() {
		return TempNameU;
	}

	public void setTempNameU(String tempNameU) {
		TempNameU = tempNameU;
	}

	public String getAuthStatus() {
		return AuthStatus;
	}

	public void setAuthStatus(String authStatus) {
		AuthStatus = authStatus;
	}

	public String getProvincee() {
		return provincee;
	}

	public void setProvincee(String provincee) {
		this.provincee = provincee;
	}

	public String getProvincek() {
		return provincek;
	}

	public void setProvincek(String provincek) {
		this.provincek = provincek;
	}

	public String getDistricte() {
		return districte;
	}

	public void setDistricte(String districte) {
		this.districte = districte;
	}

	public String getDistrictk() {
		return districtk;
	}

	public void setDistrictk(String districtk) {
		this.districtk = districtk;
	}

	public String getBranchCode() {
		return BranchCode;
	}

	public void setBranchCode(String branchCode) {
		BranchCode = branchCode;
	}

	public String getSearch() {
		return Search;
	}

	public void setSearch(String search) {
		Search = search;
	}

	public String getComName() {
		return ComName;
	}

	public void setComName(String comName) {
		ComName = comName;
	}

	public String getComNameKh() {
		return ComNameKh;
	}

	public void setComNameKh(String comNameKh) {
		ComNameKh = comNameKh;
	}

	public Integer getRecordMod() {
		return RecordMod;
	}

	public void setRecordMod(Integer recordMod) {
		RecordMod = recordMod;
	}

	public String getGroupDesc() {
		return GroupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		GroupDesc = groupDesc;
	}

	public void setTempNumeric(double tempNumeric) {
		TempNumeric = tempNumeric;
	}

	public String getTypeDesc() {
		return TypeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		TypeDesc = typeDesc;
	}
	
	//file
	
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

	public String getFileType() {
		return FileType;
	}

	public String getTFileID() {
		return TFileID;
	}

	public void setTFileID(String tFileID) {
		TFileID = tFileID;
	}

	public void setFileType(String fileType) {
		FileType = fileType;
	}

	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	public byte[] getFileImage() {
		return FileImage;
	}

	public void setFileImage(byte[] fileImage) {
		FileImage = fileImage;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	
	
}