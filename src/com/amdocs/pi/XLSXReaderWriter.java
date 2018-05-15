package com.amdocs.pi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXReaderWriter {
	 StringBuilder treeData=new StringBuilder("[[{v:'VFD2'},'', ''],");

	public static void main(String args[]){
		new XLSXReaderWriter().returnTree();
	}
	
	public String returnDummy(){
		return "DUMMY";
	}
	public String returnTree() {

		try {
			File excel = new File(
					"C:\\Users\\anuragr\\Desktop\\pi\\jas\\ADM KTV Raw_Q1'16.xlsx");
			if (!excel.exists())
				excel.createNewFile();
			else
				System.out.println(excel.toPath().toString() + " "
						+ excel.lastModified());
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);

			List<TeamDetails> searchRowList = findRow(sheet, "Agarwal Saurabh");
			XSSFSheet newSheet = null;
			if (book.getSheet("Team_Detail") == null) {
				newSheet = book.createSheet("Team_Detail");
			} else {
				newSheet = book.getSheet("Team_Detail");
			}
			int rownum = 0;
			FileOutputStream os = new FileOutputStream(excel);
			Row row = newSheet.createRow(rownum++);

			CellStyle style = book.createCellStyle();
			Font font = book.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			// style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
			style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
					.getIndex());
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFillPattern(CellStyle.ALIGN_FILL);
			style.setFont(font);

			Cell cell11 = row.createCell(0);
			cell11.setCellValue("Employee Number");
			row.getCell(0).setCellStyle(style);

			Cell cell21 = row.createCell(1);
			cell21.setCellValue("Employee Name");
			row.getCell(1).setCellStyle(style);

			Cell cell31 = row.createCell(2);
			cell31.setCellValue("Aspect Desc");
			row.getCell(2).setCellStyle(style);

			Cell cell41 = row.createCell(3);
			cell41.setCellValue("Competency Desc");
			row.getCell(3).setCellStyle(style);

			Cell cell51 = row.createCell(4);
			cell51.setCellValue("Grade");
			row.getCell(4).setCellStyle(style);

			for (TeamDetails eachRow : searchRowList) {
				row = newSheet.createRow(rownum++);
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(eachRow.getEmployeeId());

				Cell cell2 = row.createCell(1);
				cell2.setCellValue(eachRow.getEmployeeName());

				Cell cell3 = row.createCell(2);
				cell3.setCellValue(eachRow.getAspectDesc());

				Cell cell4 = row.createCell(3);
				cell4.setCellValue(eachRow.getCompetencyDesc());

				Cell cell5 = row.createCell(4);
				cell5.setCellValue(eachRow.getGrade());
			}
			book.write(os);

			System.out.println("Writing on Excel file Finished ...");
			os.close();
			book.close();
			fis.close();

			filterCompetency(searchRowList);
			System.out.println("treeData:\n"+treeData);
			String finalString = stringFormation(searchRowList);

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return treeData.toString();
	}

	private static List<TeamDetails> findRow(XSSFSheet sheet,
			String searchString) {

		List<TeamDetails> teamDetails = new ArrayList<TeamDetails>();
		TeamDetails team = null;
		int columnIndex[] = new int[6];

		Iterator<Row> rowIterator = sheet.iterator();

		Row headerRow = sheet.getRow(0);
		int columnMax = headerRow.getLastCellNum();
		for (int col = 0; col < columnMax; col++) {
			Cell cell = headerRow.getCell(col);
			String columnName = cell.getStringCellValue();

			switch (columnName) {
			case "Employee Number":
				columnIndex[0] = cell.getColumnIndex();
			case "Employee Name":
				columnIndex[1] = cell.getColumnIndex();
			case "Aspect Desc":
				columnIndex[2] = cell.getColumnIndex();
			case "Competency Desc":
				columnIndex[3] = cell.getColumnIndex();
			case "Competency Grade":
				columnIndex[4] = cell.getColumnIndex();
				break;
			case "Direct Manager Name":
				columnIndex[5] = cell.getColumnIndex();
			}
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			team = new TeamDetails();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getColumnIndex() == columnIndex[0]) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						continue;
					case Cell.CELL_TYPE_NUMERIC:
						team.setEmployeeId((int) cell.getNumericCellValue());
					}
				}
				if (cell.getColumnIndex() == columnIndex[1]) {
					team.setEmployeeName(cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[2]) {
					team.setAspectDesc(cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[3]) {
					team.setCompetencyDesc(cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[4]) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						continue;
					case Cell.CELL_TYPE_NUMERIC:
						team.setGrade((int) cell.getNumericCellValue());
					}
				}
				if (cell.getColumnIndex() == columnIndex[5]) {
					team.setManagerName(cell.getStringCellValue());
				}
			}
			teamDetails.add(team);
		}

		List<TeamDetails> teamList = new ArrayList<TeamDetails>();
		for (TeamDetails record : teamDetails) {
			if (record.getManagerName().equals(searchString)) {
				teamList.add(record);
			}

		}
		return teamList;
	}

	public String filterCompetency(List<TeamDetails> teamList) {
		HashSet<String> teamSet = new HashSet<String>();
		int count = 0,sum = 0;
		Map<String, String> countMap = new HashMap<String, String>();

		for (TeamDetails team : teamList) {
			if (teamSet.contains("['" + team.getCompetencyDesc() + "','"
					+ team.getAspectDesc() + "']")) {
				if (countMap.containsKey(team.getCompetencyDesc())) {
					
					String[] str = countMap.get(team.getCompetencyDesc()).split("-");
					sum = Integer.parseInt(str[0])+ team.getGrade();
					count = Integer.parseInt(str[1]) + 1;
					String finalStr = sum + "-" + count;
					countMap.put(team.getCompetencyDesc(), finalStr);
				} else {
					countMap.put(team.getCompetencyDesc(), team.getGrade() + "-" + "1");
				}

			} else {
				teamSet.add("['" + team.getCompetencyDesc() + "','"
						+ team.getAspectDesc() + "']");
				countMap.put(team.getCompetencyDesc(), team.getGrade() + "-" + "1");
			}
		}

		  Set set = countMap.entrySet(); 
		  Iterator iter = set.iterator();
		  
		  while (iter.hasNext()) {
			  Map.Entry entry = (Map.Entry) iter.next();
			//  System.out.println(entry.getKey() + " ____ " + entry.getValue()); 
			  
//		  prepare below string
//		  [{v:'Manager', f:'<div style="background-color:red; font-style:italic">Manager</div>'}, '','']
			  //treeData.append("   [{v:'"+entry.getKey()+"', f:'<div style=\"background-color:red; font-style:italic\">"+entry.getKey()+"</div>'}, 'Mike',''], ");
			  treeData.append("   [{v:'"+entry.getKey()+"'}, 'VFD2',''], ");
		  }
		  treeData.append("['', '', '']]");
		 
		return null;
	}

	public static String stringFormation(List<TeamDetails> teamList) {

		return null;
	}
}
