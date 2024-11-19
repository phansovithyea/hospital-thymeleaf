package com.ibiztechno.app.helper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hpsf.Decimal;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import net.sf.jasperreports.engine.util.Java14BigDecimalHandler;

public class ExcelSheetHelper {
	public static void SheetData(List<Map<Object, Object>> datas, Sheet sheet) {
		Row header = sheet.createRow(0);

		if (datas.size() > 0) {
			int row = 0;
			for (Map.Entry<Object, Object> entry : datas.get(0).entrySet()) {
				header.createCell(row).setCellValue(String.valueOf(entry.getKey()));
				row++;
			}

			row = 1;
			for (Map<Object, Object> str : datas) {
				Row _row = sheet.createRow(row);

				int column = 0;
				for (Map.Entry<Object, Object> entry : str.entrySet()) {

					try {
						
						

						if (entry.getValue().getClass() == Integer.class) {
							Integer val = (Integer) entry.getValue();
							if (val != 0) {
								_row.createCell(column).setCellValue(val);
							} else {
								_row.createCell(column, CellType.BLANK);
							}

						} else if (entry.getValue().getClass() == Short.class) {
							Short val = (Short) entry.getValue();
							if (val != 0) {
								_row.createCell(column).setCellValue(val);
							} else {
								_row.createCell(column, CellType.BLANK);
							}

						} else if (entry.getValue().getClass() == BigDecimal.class) {
							Double val = ((BigDecimal) entry.getValue()).doubleValue();
							if (val != 0) {
								_row.createCell(column).setCellValue(val);
							} else {
								_row.createCell(column, CellType.BLANK);
							}
						} else if (entry.getValue().getClass() == Double.class) {
							Double val = ((Double) entry.getValue());
							if (val != 0) {
								_row.createCell(column).setCellValue(val);
							} else {
								_row.createCell(column, CellType.BLANK);
							}
						} else if (entry.getValue().getClass() == Boolean.class) {
							_row.createCell(column).setCellValue((Boolean) entry.getValue());
						} else if (entry.getValue().getClass() == Long.class) {
							Long val = ((Long) entry.getValue());
							if (val != 0) {
								_row.createCell(column).setCellValue(val);
							} else {
								_row.createCell(column, CellType.BLANK);
							}
						} else if (entry.getValue().getClass() == Float.class) {
							Float val = ((Float) entry.getValue());
							if (val != 0) {
								_row.createCell(column).setCellValue(val);
							} else {
								_row.createCell(column, CellType.BLANK);
							}
						} else if (entry.getValue().getClass() == Date.class) {
							_row.createCell(column).setCellValue((Date) entry.getValue());
						} else {
							_row.createCell(column).setCellValue(String.valueOf(entry.getValue()));
						}
					} catch (Exception ex) {
						_row.createCell(column, CellType.BLANK);
						System.out.println(ex.getMessage() + " : " + entry.getKey() + " : " + entry.getValue());
					}
					column++;
				}
				row++;
			}
		}
	}
}
