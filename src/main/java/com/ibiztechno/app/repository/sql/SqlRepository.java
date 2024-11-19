package com.ibiztechno.app.repository.sql;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;

import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.ReportNameCode;

public interface SqlRepository {

	List<Map<String, Object>> GetList(String procedureName, @Nullable SqlParameterSource param) throws Exception;

	List<Map<Object, Object>> GetListObj(String procedureName, @Nullable SqlParameterSource param) throws Exception;

	List<Object> GetList( String procedureName, @Nullable SqlParameterSource param, Class c) throws Exception;

	List<CodeName> GetParam(String procedureName, @Nullable SqlParameterSource param) throws Exception;

	List<CodeName> GetParam1(String procedureName, @Nullable List<SqlParameter> params) throws Exception;

	List<ReportNameCode> GetParamReport(String procedureName, @Nullable SqlParameterSource param) throws Exception;

	Long GetLong(String procedureName, @Nullable SqlParameterSource param) throws Exception;

	String GetString(String procedureName, @Nullable SqlParameterSource param) throws Exception;

	String messageTrimmer(String Exception_message);
}