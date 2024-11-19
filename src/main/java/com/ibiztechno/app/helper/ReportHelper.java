package com.ibiztechno.app.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ibiztechno.app.model.CodeName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Repository
public class ReportHelper {
	@Autowired
//	@Qualifier("ERP")
    JdbcTemplate jdbcTemplate;

	public void dowload(String rptName, String xmlName, String extenstion, Map<String, Object> param,
	    HttpServletResponse response) throws JRException, SQLException, IOException {
    	
    	 //xmlName = xmlName.replace(".rpt", "");
    	 
		InputStream reportStream = getClass().getResourceAsStream("/reports/" + xmlName + ".jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
	
		Connection con = jdbcTemplate.getDataSource().getConnection();
	
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, con);
		
//			rptName = rptName.replace("", "pdf");
		extenstion="pdf";
		
		//will update to support another format
		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=" + rptName +"."+ extenstion);
	
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    public static List<CodeName>  getReporttype() {
	List<CodeName> ls = new ArrayList<CodeName>();
	ls.add(new CodeName("PDF", ".pdf"));
	ls.add(new CodeName("WORD", ".docx"));
	ls.add(new CodeName("RICH TEXT", ".rtf"));
	ls.add(new CodeName("POWER POINT", ".ppt"));
	ls.add(new CodeName("EXCEL 2003", ".xls"));
	ls.add(new CodeName("EXCEL 2007 AND HIGHER", ".xlsx"));
	ls.add(new CodeName("CSV", ".csv"));
	ls.add(new CodeName("XML", ".xml"));
	ls.add(new CodeName("TEXT", ".txt"));
	return ls;
    }
}
