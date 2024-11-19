package com.ibiztechno.app.model;

public class ReportNameCode {
	String reportcode;
	String reportdesc;

	public ReportNameCode() {
		super();
	}

	public ReportNameCode(String reportcode, String reportdesc) {
		super();
		this.reportcode = reportcode;
		this.reportdesc = reportdesc;
	}

	public String getReportcode() {
		return reportcode;
	}

	public void setReportcode(String reportcode) {
		this.reportcode = reportcode;
	}

	public String getReportdesc() {
		return reportdesc;
	}

	public void setReportdesc(String reportdesc) {
		this.reportdesc = reportdesc;
	}
}
