package com.ibiztechno.app.orclprovider.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.sql.*;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibiztechno.app.repository.orcl.OrclRepository;
import com.ibiztechno.app.repository.sql.SqlRepository;

@Controller
@RequestMapping("/ldeposit")
@Component
public class DepositController {

    @Autowired
    private OrclRepository orclRepository;
    

//    @RequestMapping()
//    @ResponseBody
//    public List<Map<String, Object>> index() throws Exception {
//	return orclRepository.GetLists("LOCALDPT_INF", new MapSqlParameterSource().addValue("pTeller", "")
//		.addValue("pAcNo", "").addValue("pFRDT", LocalDateTime.now()).addValue("pTODT", LocalDateTime.now()));
//    }
}