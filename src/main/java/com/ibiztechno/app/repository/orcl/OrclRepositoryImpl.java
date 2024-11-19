package com.ibiztechno.app.repository.orcl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ibiztechno.app.model.CodeName;

@Repository
public class OrclRepositoryImpl implements OrclRepository {
    private static final Logger logger = LoggerFactory.getLogger(OrclRepositoryImpl.class);

//    @Autowired
//    @Qualifier("CBS")
//    private JdbcTemplate jdbcTemplate;
//
//    private SimpleJdbcCall simpleJdbcCall;
//    SqlParameterSource param;
//
//    @Override
//    public List<Object> GetList(Principal principal, String procedureName, SqlParameterSource param, Class c)
//	    throws Exception {
//
//	try {
//	    simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName)
//		    .returningResultSet("retval", BeanPropertyRowMapper.newInstance(c));
//
//	    Map<String, Object> out = this.simpleJdbcCall.execute(param);
//
//	    if (out == null) {
//		return Collections.emptyList();
//	    } else {
//		return (List) out.get("retval");
//	    }
//	} catch (Exception ex) {
//	    String msg = messageTrimmer(ex.getMessage());
//	    logger.error(ex.getMessage());
//	    throw new Exception(msg);
//	}
//    }
//
//    @Override
//    public Long GetLong(String procedureName, SqlParameterSource param) throws Exception {
//
//	try {
//	    simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
////		    .returningResultSet("retval", BeanPropertyRowMapper.newInstance(Long.class));
//
//	    Map<String, Object> out = this.simpleJdbcCall.execute(param);
//	    return Long.parseLong(out.get("retval").toString());
//	} catch (Exception ex) {
//	    String msg = messageTrimmer(ex.getMessage());
//	    logger.error(ex.getMessage());
//	    throw new Exception(msg);
//	}
//
//    }
//
//    @Override
//    public List<CodeName> GetParam(String procedureName, SqlParameterSource param) throws Exception {
//	try {
//	    simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName)
//		    .returningResultSet("retval", BeanPropertyRowMapper.newInstance(CodeName.class));
//
//	    Map<String, Object> out = this.simpleJdbcCall.execute(param);
//
//	    if (out == null) {
//		return Collections.emptyList();
//	    } else {
//		return (List) out.get("retval");
//	    }
//	} catch (Exception ex) {
//	    logger.error(ex.getMessage());
//	}
//	return Collections.emptyList();
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<Map<String, Object>> GetList(String procedureName, SqlParameterSource param) throws Exception {
//	try {
//	    simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
//
//	    Map<String, Object> out = this.simpleJdbcCall.execute(param);
//
//	    if (out == null) {
//		return Collections.emptyList();
//	    } else {
//		return ((List<Map<String, Object>>) out.get("#result-set-1"));
//	    }
//	} catch (Exception ex) {
//	    String msg = messageTrimmer(ex.getMessage());
//	    logger.error(ex.getMessage());
//	    throw new Exception(msg);
//	}
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<Map<String, Object>> GetLists(String procedureName, SqlParameterSource param) throws Exception {
//	try {
//	    simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
//
//	    Map<String, Object> out = this.simpleJdbcCall.execute(param);
//
//	    if (out == null) {
//		return Collections.emptyList();
//	    } else {
//		return ((List<Map<String, Object>>) out.get("#result-set-1"));
//	    }
//	} catch (Exception ex) {
//	    String msg = messageTrimmer(ex.getMessage());
//	    logger.error(ex.getMessage());
//	    throw new Exception(msg);
//	}
//    }
//
//    @Override
//    public String GetString(String procedureName, SqlParameterSource param) throws Exception {
//	try {
//	    simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
//
//	    Map<String, Object> out = this.simpleJdbcCall.execute(param);
//
//	    if (out == null) {
//		return "";
//	    } else {
//		return (String) out.get("retval");
//	    }
//	} catch (Exception ex) {
//	    String msg = messageTrimmer(ex.getMessage());
//	    logger.error(ex.getMessage());
//	    throw new Exception(msg);
//	}
//    }
//
//    @Override
//    public String messageTrimmer(String message) {
//	String msg = message;
//	if (message.contains("Exception:")) {
//	    msg = message.substring(message.lastIndexOf("Exception"));
//	}
//	return msg;
//    }
//
//    @Override
//    public List<Map<String, Object>> GetListFromRawSQL(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
//	    String sql, Map<String, Object> paramMap) throws Exception {
//	return namedParameterJdbcTemplate.queryForList(sql, paramMap);
//    }
//
//    @Override
//    public Integer GetTotalRecord(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String sql,
//	    Map<String, Object> paramMap) throws Exception {
//	String _sql = "select count(*) from (" + sql + ")";
//	return namedParameterJdbcTemplate.queryForObject(_sql, paramMap, Integer.class);
//    }
//
//    @SuppressWarnings("resource")
//    @Override
//    public String ReadSQL(String path) throws FileNotFoundException {
//	return new BufferedReader(new FileReader(path)).lines().collect(Collectors.joining(System.lineSeparator()));
//    }

}
