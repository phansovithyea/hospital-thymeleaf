package com.ibiztechno.app.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ListType {

    String ID;
    String FormCode;
    String ListViewCode;
    String ListViewDesc;
    String DefCriteria;
    String BrCriteria;
    String SpecViewName;
    String ListType;
    String LinkListCode;
    String SpecReportCode;
    String ImportType;
    String ImportSource;
    String ExportType;
    String UserFlag;
    String CreatedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate CreatedDate;
    String UpdatedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate UpdatedDate;
    String ExportFlag;
    String CreatedFrom;
    String UpdatedFrom;
    String FormType;
    String ImportSP;
    String ExportBy;
    String IncludeTag;
    String SourceType;
    String ListViewDescK;
    String LanType;
    String TableName;
    String AppFlag;
    String AppType;
    String ImportSourceType;
    String AuthStatus;
    String WebGroup;
    String WebModule;
    String IconCode;
    Integer RowsPerPage;
    String Pagination;
    String RecStatus;
    String Type;
    String ViewDesc;
    String ViewDescK;
    String RTypeName;
    String RTypeNameK;
    String ReportName;
    String ReportGroupName;
    String ReportCatName;
    String LinkListDesc;
    String ListTypeDesc;
    String ExportTypeDesc;
    String FormTypeDesc;
    String ImportTypeDesc;
    String SourceTypeDesc;
    String SourceTypeShort;
    String ImportSourceTypeDesc;
    String ImportSourceTypeShort;
    String LanName;
    String Criteria;
    String OrderBy;
    Integer PageNumber=1;
    String UserID;
	String RemoteAddr;
    
    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public String getFormCode() {
        return FormCode;
    }
    public void setFormCode(String formCode) {
        FormCode = formCode;
    }
    public String getListViewCode() {
        return ListViewCode;
    }
    public void setListViewCode(String listViewCode) {
        ListViewCode = listViewCode;
    }
    public String getListViewDesc() {
        return ListViewDesc;
    }
    public void setListViewDesc(String listViewDesc) {
        ListViewDesc = listViewDesc;
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
    public String getImportType() {
        return ImportType;
    }
    public void setImportType(String importType) {
        ImportType = importType;
    }
    public String getImportSource() {
        return ImportSource;
    }
    public void setImportSource(String importSource) {
        ImportSource = importSource;
    }
    public String getExportType() {
        return ExportType;
    }
    public void setExportType(String exportType) {
        ExportType = exportType;
    }
    public String getUserFlag() {
        return UserFlag;
    }
    public void setUserFlag(String userFlag) {
        UserFlag = userFlag;
    }
    public String getCreatedBy() {
        return CreatedBy;
    }
    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
    public LocalDate getCreatedDate() {
        return CreatedDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        CreatedDate = createdDate;
    }
    public String getUpdatedBy() {
        return UpdatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }
    public LocalDate getUpdatedDate() {
        return UpdatedDate;
    }
    public void setUpdatedDate(LocalDate updatedDate) {
        UpdatedDate = updatedDate;
    }
    public String getExportFlag() {
        return ExportFlag;
    }
    public void setExportFlag(String exportFlag) {
        ExportFlag = exportFlag;
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
    public String getFormType() {
        return FormType;
    }
    public void setFormType(String formType) {
        FormType = formType;
    }
    public String getImportSP() {
        return ImportSP;
    }
    public void setImportSP(String importSP) {
        ImportSP = importSP;
    }
    public String getExportBy() {
        return ExportBy;
    }
    public void setExportBy(String exportBy) {
        ExportBy = exportBy;
    }
    public String getIncludeTag() {
        return IncludeTag;
    }
    public void setIncludeTag(String includeTag) {
        IncludeTag = includeTag;
    }
    public String getSourceType() {
        return SourceType;
    }
    public void setSourceType(String sourceType) {
        SourceType = sourceType;
    }
    public String getListViewDescK() {
        return ListViewDescK;
    }
    public void setListViewDescK(String listViewDescK) {
        ListViewDescK = listViewDescK;
    }
    public String getLanType() {
        return LanType;
    }
    public void setLanType(String lanType) {
        LanType = lanType;
    }
    public String getTableName() {
        return TableName;
    }
    public void setTableName(String tableName) {
        TableName = tableName;
    }
    public String getAppFlag() {
        return AppFlag;
    }
    public void setAppFlag(String appFlag) {
        AppFlag = appFlag;
    }
    public String getAppType() {
        return AppType;
    }
    public void setAppType(String appType) {
        AppType = appType;
    }
    public String getImportSourceType() {
        return ImportSourceType;
    }
    public void setImportSourceType(String importSourceType) {
        ImportSourceType = importSourceType;
    }
    public String getAuthStatus() {
        return AuthStatus;
    }
    public void setAuthStatus(String authStatus) {
        AuthStatus = authStatus;
    }
    public String getWebGroup() {
        return WebGroup;
    }
    public void setWebGroup(String webGroup) {
        WebGroup = webGroup;
    }
    public String getWebModule() {
        return WebModule;
    }
    public void setWebModule(String webModule) {
        WebModule = webModule;
    }
    public String getIconCode() {
        return IconCode;
    }
    public void setIconCode(String iconCode) {
        IconCode = iconCode;
    }
    public Integer getRowsPerPage() {
        return RowsPerPage;
    }
    public void setRowsPerPage(Integer rowsPerPage) {
        RowsPerPage = rowsPerPage;
    }
    public String getPagination() {
        return Pagination;
    }
    public void setPagination(String pagination) {
        Pagination = pagination;
    }
    public String getRecStatus() {
        return RecStatus;
    }
    public void setRecStatus(String recStatus) {
        RecStatus = recStatus;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public String getViewDesc() {
        return ViewDesc;
    }
    public void setViewDesc(String viewDesc) {
        ViewDesc = viewDesc;
    }
    public String getViewDescK() {
        return ViewDescK;
    }
    public void setViewDescK(String viewDescK) {
        ViewDescK = viewDescK;
    }
    public String getRTypeName() {
        return RTypeName;
    }
    public void setRTypeName(String rTypeName) {
        RTypeName = rTypeName;
    }
    public String getRTypeNameK() {
        return RTypeNameK;
    }
    public void setRTypeNameK(String rTypeNameK) {
        RTypeNameK = rTypeNameK;
    }
    public String getReportName() {
        return ReportName;
    }
    public void setReportName(String reportName) {
        ReportName = reportName;
    }
    public String getReportGroupName() {
        return ReportGroupName;
    }
    public void setReportGroupName(String reportGroupName) {
        ReportGroupName = reportGroupName;
    }
    public String getReportCatName() {
        return ReportCatName;
    }
    public void setReportCatName(String reportCatName) {
        ReportCatName = reportCatName;
    }
    public String getLinkListDesc() {
        return LinkListDesc;
    }
    public void setLinkListDesc(String linkListDesc) {
        LinkListDesc = linkListDesc;
    }
    public String getListTypeDesc() {
        return ListTypeDesc;
    }
    public void setListTypeDesc(String listTypeDesc) {
        ListTypeDesc = listTypeDesc;
    }
    public String getExportTypeDesc() {
        return ExportTypeDesc;
    }
    public void setExportTypeDesc(String exportTypeDesc) {
        ExportTypeDesc = exportTypeDesc;
    }
    public String getFormTypeDesc() {
        return FormTypeDesc;
    }
    public void setFormTypeDesc(String formTypeDesc) {
        FormTypeDesc = formTypeDesc;
    }
    public String getImportTypeDesc() {
        return ImportTypeDesc;
    }
    public void setImportTypeDesc(String importTypeDesc) {
        ImportTypeDesc = importTypeDesc;
    }
    public String getSourceTypeDesc() {
        return SourceTypeDesc;
    }
    public void setSourceTypeDesc(String sourceTypeDesc) {
        SourceTypeDesc = sourceTypeDesc;
    }
    public String getSourceTypeShort() {
        return SourceTypeShort;
    }
    public void setSourceTypeShort(String sourceTypeShort) {
        SourceTypeShort = sourceTypeShort;
    }
    public String getImportSourceTypeDesc() {
        return ImportSourceTypeDesc;
    }
    public void setImportSourceTypeDesc(String importSourceTypeDesc) {
        ImportSourceTypeDesc = importSourceTypeDesc;
    }
    public String getImportSourceTypeShort() {
        return ImportSourceTypeShort;
    }
    public void setImportSourceTypeShort(String importSourceTypeShort) {
        ImportSourceTypeShort = importSourceTypeShort;
    }
    public String getLanName() {
        return LanName;
    }
    public void setLanName(String lanName) {
        LanName = lanName;
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
