package com.ibiztechno.app.model;

public class ReportView {

	String ID;
    String MasterCode;
    String ReportCode;
    String ReportName;
    String FileName;
    String ReportFormat;
    String PeriodID;
    String PeriodMonth;
    String FomatTypeExt;
    
    
    
    public String getPeriodMonth() {
		return PeriodMonth;
	}
	public void setPeriodMonth(String periodMonth) {
		PeriodMonth = periodMonth;
	}
	public String getPeriodID() {
		return PeriodID;
	}
	public void setPeriodID(String periodID) {
		PeriodID = periodID;
	}
	public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    
    public String getMasterCode() {
        return MasterCode;
    }
    public void setMasterCode(String masterCode) {
    	MasterCode = masterCode;
    }
    public String getReportCode() {
        return ReportCode;
    }
    public void setReportCode(String reportCode) {
    	ReportCode = reportCode;
    }
    public String getReportName() {
        return ReportName;
    }
    public void setReportName(String reportName) {
    	ReportName = reportName;
    }
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getReportFormat() {
		return ReportFormat;
	}
	public void setReportFormat(String reportFormat) {
		ReportFormat = reportFormat;
	}
	public String getFomatTypeExt() {
		return FomatTypeExt;
	}
	public void setFomatTypeExt(String fomatTypeExt) {
		FomatTypeExt = fomatTypeExt;
	}
    
    
}
