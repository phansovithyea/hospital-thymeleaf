package com.ibiztechno.app.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.CodeValue;
import com.ibiztechno.app.repository.sql.SqlRepository;

public class Dashboard {
	String Code;
	String Name;
	String ChartType;
	List<CodeValue> dashboardData;

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getChartType() {
		return ChartType;
	}

	public void setChartType(String chartType) {
		ChartType = chartType;
	}

	public List<CodeValue> getDashboardData() {
		return dashboardData;
	}

	public void setDashboardData(List<CodeValue> dashboardData) {
		this.dashboardData = dashboardData;
	}

}
