package com.ibiztechno.app.repository.sql;

import static org.springframework.jdbc.core.BeanPropertyRowMapper.newInstance;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.metadata.CallMetaDataContext;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.ReportNameCode;

@Repository
public class SqlRepositoryImpl implements SqlRepository {
	private static final Logger logger = LoggerFactory.getLogger(SqlRepositoryImpl.class);

	@Autowired
//	@Qualifier("ERP")
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcCall simpleJdbcCall;
	SqlParameterSource param;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> GetList(String procedureName, SqlParameterSource param) throws Exception {
		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
//			simpleJdbcCall.setAccessCallParameterMetaData(false);

			Map<String, Object> out = this.simpleJdbcCall.execute(param);

			if (out == null) {
				return Collections.emptyList();
			} else {
				return ((List<Map<String, Object>>) out.get("#result-set-1"));
			}
		} catch (Exception ex) {
			String msg = messageTrimmer(ex.getMessage());
			logger.error(ex.getMessage());
			throw new Exception(msg);
		}
	}
	
	@Override
	public List<Object> GetList(String procedureName, SqlParameterSource param, Class c)
			throws Exception {

		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName)
					.returningResultSet("retval", newInstance(c));
//			simpleJdbcCall.setAccessCallParameterMetaData(false);

			Map<String, Object> out = this.simpleJdbcCall.execute(param);

			if (out == null) {
				return Collections.emptyList();
			} else {
				return (List) out.get("retval");
			}
		} catch (Exception ex) {
			String msg = messageTrimmer(ex.getMessage());
			logger.error(ex.getMessage());
			throw new Exception(msg);
		}
	}

	@Override
	public Long GetLong(String procedureName, SqlParameterSource param) throws Exception {

		try {

			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);

//			simpleJdbcCall.setAccessCallParameterMetaData(false);
//		    .returningResultSet("retval", BeanPropertyRowMapper.newInstance(Long.class));

			Map<String, Object> out = this.simpleJdbcCall.execute(param);
			return Long.parseLong(out.get("retval").toString());
		} catch (Exception ex) {
			String msg = messageTrimmer(ex.getMessage());
			logger.error(ex.getMessage());
			throw new Exception(msg);
		}

	}

	@Override
	public List<ReportNameCode> GetParamReport(String procedureName, SqlParameterSource param) throws Exception {
		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName)
					.returningResultSet("retval", newInstance(ReportNameCode.class));
//	    simpleJdbcCall.setAccessCallParameterMetaData(false);

			Map<String, Object> out = this.simpleJdbcCall.execute(param);

			if (out == null) {
				return Collections.emptyList();
			} else {
				return (List) out.get("retval");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return Collections.emptyList();
	}

	@Override
	public List<CodeName> GetParam(String procedureName, SqlParameterSource param) throws Exception {
		try {

			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName)
					.returningResultSet("retval", newInstance(CodeName.class));

			// Call the function and retrieve the result
			Map<String, Object> out = this.simpleJdbcCall.execute(param);

			if (out == null) {
				return Collections.emptyList();
			} else {
				return (List) out.get("retval");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return Collections.emptyList();
	}

	@Override
	public List<CodeName> GetParam1(String procedureName, List<SqlParameter> params) throws Exception {
		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName)
					.returningResultSet("retval", newInstance(CodeName.class));

			for (int i = 0; i < params.size(); i++) {
				simpleJdbcCall.declareParameters(params.get(i));
			}

			Map<String, Object> out = this.simpleJdbcCall.execute(param);

			if (out == null) {
				return Collections.emptyList();
			} else {
				return (List) out.get("retval");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return Collections.emptyList();
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Map<String, Object>> GetList(String procedureName, SqlParameterSource param) throws Exception {
//		try {
//			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
//
//			Map<String, Object> out = this.simpleJdbcCall.execute(param);
//
//			if (out == null) {
//				return Collections.emptyList();
//			} else {
//				return ((List<Map<String, Object>>) out.get("#result-set-1"));
//			}
//		} catch (Exception ex) {
//			String msg = messageTrimmer(ex.getMessage());
//			logger.error(ex.getMessage());
//			throw new Exception(msg);
//		}
//	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<Object, Object>> GetListObj(String procedureName, SqlParameterSource param) throws Exception {
		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);

			Map<String, Object> out = this.simpleJdbcCall.execute(param);
//			simpleJdbcCall.setAccessCallParameterMetaData(false);

			if (out == null) {
				return Collections.emptyList();
			} else {
				return ((List<Map<Object, Object>>) out.get("#result-set-1"));
			}
		} catch (Exception ex) {
			String msg = messageTrimmer(ex.getMessage());
			logger.error(ex.getMessage());
			throw new Exception(msg);
		}
	}

	@Override
	public String GetString(String procedureName, SqlParameterSource param) throws Exception {
		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
//			simpleJdbcCall.setAccessCallParameterMetaData(false);

			Map<String, Object> out = this.simpleJdbcCall.execute(param);

			if (out == null) {
				return "";
			} else {
				return (String) out.get("retval");
			}
		} catch (Exception ex) {
			String msg = messageTrimmer(ex.getMessage());
			logger.error(ex.getMessage());
			throw new Exception(msg);
		}
	}

	@Override
	public String messageTrimmer(String message) {
		String msg = message;
		if (message.contains("Exception:")) {
			msg = message.substring(message.lastIndexOf("Exception:") + 11);
		}
		return msg;
	}
}
