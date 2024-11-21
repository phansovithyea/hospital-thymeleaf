package com.ibiztechno.app.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ibiztechno.app.repository.SqlRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

@Repository
public class ExcelReader {
    
    @Autowired
    SqlRepository accountRepository;

    @Autowired
	private JdbcTemplate jdbcTemplate;
    
    public void read(MultipartFile file, String tblName) throws Exception {
	try {

	    String extension = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

	    if (!extension.equalsIgnoreCase(".xls")) {
	    	
	    	throw new Exception("File not support. excel 2007 allow only.");
	    }

	    Path tempDir = Files.createTempDirectory("");
	    File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
	    file.transferTo(tempFile);

	    Workbook workbook = WorkbookFactory.create(tempFile);
	    
	    Sheet sheet = workbook.getSheetAt(0);
	    
	    DataFormatter dataFormatter = new DataFormatter();

	    Iterator<Row> rowIterator = sheet.rowIterator();

	    int r = 0;

	    int lrn = sheet.getLastRowNum();
	    String insert = "insert into "+tblName;

	    int celCount = 0;
	    for (int i = 0; i <= sheet.getLastRowNum(); i++) {

		Row row = sheet.getRow(i);
		if (i == 0) {
		    celCount = row.getPhysicalNumberOfCells();
		    insert +="(";
		    for(int j = 0 ; j< celCount; j++) {
				insert += "["+String.valueOf(row.getCell(j)).trim().replace("'", "''");
				if(j < celCount-1) {
				    insert += "],";
				}
		    }
		    insert+="]) values ";

		} else {
		    insert += "(";
		    for (int j = 0; j < celCount; j++) {
				insert += "N'";
				if (String.valueOf(row.getCell(j)).trim().equalsIgnoreCase("null")) {
				    insert += "";
				} else {
				    insert += String.valueOf(row.getCell(j)).trim().replace("'", "''");
				}
				insert += "'";
				if (j < celCount - 1) {
				    insert += ",";
				}
		    }
		    insert += ")";
		    if (i < sheet.getPhysicalNumberOfRows() - 1) {
		    	insert += ",";
		    }
		}

	    }
	    
	    //insert ="SET IDENTITY_INSERT "+tblName+" ON " + insert+" SET IDENTITY_INSERT "+tblName+" OFF";
	    jdbcTemplate.execute(insert);
	    

	} catch (IOException exception) {
	    accountRepository.GetString("ProcedureLog_Add",
		    new MapSqlParameterSource().addValue("ObjectID", "0").addValue("ParamDefinition", "")
			    .addValue("DatabaseID", "").addValue("AdditionalInfo", exception.getMessage()));
	    throw new Exception(accountRepository.messageTrimmer(exception.getMessage()));
	}

    }
}
