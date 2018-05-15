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

public class TaskReaderWriter {
	 StringBuilder treeData=new StringBuilder("[[{v:'VFD2'},'', ''],");

	public static void main(String args[]){
		TaskReaderWriter trw =new TaskReaderWriter();
	//	trw.returnTeam();
	//	trw.writeTask("DEKIASUPSPROBLEM-1053","http://ipm.vfeds.local:8080/jira/browse/DEKIASUPSPROBLEM-1053","Tank Piyush");
	//	trw.writeTask("DEKIASUPSPROBLEM-1053","http://ipm.vfeds.local:8080/jira/browse/DEKIASUPSPROBLEM-1053","Sen Poonam");
	//	trw.readTask("Rathi Anurag");
		trw.readTaskList("");
	}
	
	public String returnDummy(){
		return "DUMMY";
	}

	
	public List<TaskDetails> readTask(String empName) {
		List<TaskDetails> searchRowSet = null;
		try {
			File excel = new File(
					"C:\\Users\\anuragr\\Desktop\\pi\\jas\\pi.xlsx");
			if (!excel.exists())
				excel.createNewFile();
			else
				System.out.println(excel.toPath().toString() + " "
						+ excel.lastModified());
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet("Task_Detail");

			 searchRowSet = returnTask(sheet,empName);
	
            Iterator<TaskDetails> itr =searchRowSet.iterator();
            while(itr.hasNext())
            {
            	TaskDetails td = itr.next();
            	//System.out.println("Name:"+td.getEmployeeName()+"task"+td.getTaskName().trim());
            }

			System.out.println("Reading on Excel file Finished ...");

			book.close();
			fis.close();

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		return searchRowSet;
	}
	
	public List<TaskList> readTaskList(String key) {
		List<TaskList> searchRowSet = null;
		try {
			File excel = new File(
					"C:\\Users\\anuragr\\Desktop\\pi\\jas\\ADM KTV Raw_Q1'16.xlsx");
			if (!excel.exists())
				excel.createNewFile();
			else
				System.out.println(excel.toPath().toString() + " "+ excel.lastModified());
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet("Task_List");

			 searchRowSet = returnTaskList(sheet,key);
	
            Iterator<TaskList> itr =searchRowSet.iterator();
            while(itr.hasNext())
            {
            	TaskList td = itr.next();
            	System.out.println("Task:"+td.getKey()+"Assignee:"+td.getAssignee());
            }

			System.out.println("Reading on Excel file Finished ...");

			book.close();
			fis.close();

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		return searchRowSet;
	}
	
	public String writeTask(String taskName, String taskDescription, String empName) {

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
			XSSFSheet sheet = book.getSheet("Task_Detail");

			int rowNum = findRow(sheet,empName);
			XSSFSheet newSheet = null;
			if (book.getSheet("Task_Detail") == null) {
				newSheet = book.createSheet("Task_Detail");
			} else {
				newSheet = book.getSheet("Task_Detail");
			}
			int rownum = 0;
			FileOutputStream os = new FileOutputStream(excel);
			Row row = newSheet.createRow(rownum++);

			CellStyle style = book.createCellStyle();
			Font font = book.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			// style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
			style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFont(font);

			Cell cell11 = row.createCell(0);
			cell11.setCellValue("Employee Name");
			row.getCell(0).setCellStyle(style);

			Cell cell21 = row.createCell(1);
			cell21.setCellValue("Task");
			row.getCell(1).setCellStyle(style);
			
			Cell cell22 = row.createCell(2);
			cell22.setCellValue("Task Description");
			row.getCell(1).setCellStyle(style);

				row = newSheet.getRow(rowNum);
				
				Cell cell2 = row.createCell(0);
				cell2.setCellValue(empName);
				Cell cell3 = row.createCell(1);
				cell3.setCellValue(taskName);
				Cell cell4 = row.createCell(2);
				cell4.setCellValue(taskDescription);

			book.write(os);

			System.out.println("Writing on Excel file Finished ...");
			os.close();
			book.close();
			fis.close();

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		System.out.println(treeData.toString());
		return treeData.toString();
	}
	
	
	
	
	public String returnTeam() {

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
			XSSFSheet sheet = book.getSheet("Team_Detail");

			Set<String> searchRowSet = findRow(sheet);
			XSSFSheet newSheet = null;
			if (book.getSheet("Task_Detail") == null) {
				newSheet = book.createSheet("Task_Detail");
			} else {
				newSheet = book.getSheet("Task_Detail");
			}
			int rownum = 0;
			FileOutputStream os = new FileOutputStream(excel);
			Row row = newSheet.createRow(rownum++);

			CellStyle style = book.createCellStyle();
			Font font = book.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			// style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
			style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFont(font);

			Cell cell11 = row.createCell(0);
			cell11.setCellValue("Employee Number");
			row.getCell(0).setCellStyle(style);

			Cell cell21 = row.createCell(1);
			cell21.setCellValue("Task");
			row.getCell(1).setCellStyle(style);	
			Cell cell22 = row.createCell(2);
			cell22.setCellValue("Task Description");
			row.getCell(1).setCellStyle(style);

			for (String eachRow : searchRowSet) {
				row = newSheet.createRow(rownum++);

				Cell cell2 = row.createCell(0);
				cell2.setCellValue(eachRow);
							}
			book.write(os);

			System.out.println("Writing on Excel file Finished ...");
			os.close();
			book.close();
			fis.close();

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		System.out.println(treeData.toString());
		return treeData.toString();
	}

	private static Set<String> findRow(XSSFSheet sheet) {

		Set<String> teamDetails = new HashSet<String>();
		String team = null;
		int columnIndex[] = new int[6];

		Iterator<Row> rowIterator = sheet.iterator();

		Row headerRow = sheet.getRow(0);
		int columnMax = headerRow.getLastCellNum();
		for (int col = 0; col < columnMax; col++) {
			Cell cell = headerRow.getCell(col);
			String columnName = cell.getStringCellValue();

			switch (columnName) {

			case "Employee Name":
				columnIndex[0] = cell.getColumnIndex();
			}
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			team = new String();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				if (cell.getColumnIndex() == columnIndex[0]) {
					team= cell.getStringCellValue();
				}
			}
			teamDetails.add(team);
		}

		Set<String> teamSet = new HashSet<String>();
		
		for (String record : teamDetails) {
//			if (record.getManagerName().equals(searchString)) {
			teamSet.add(record);
//			}

		}
		return teamSet;
	}
	
	private static int findRow(XSSFSheet sheet,
			String searchString) {

		Set<String> teamDetails = new HashSet<String>();
		String team = null;
		int columnIndex[] = new int[6];
        
		if (sheet == null)
		{
			System.out.println("no team detail...");
			new TaskReaderWriter().returnTeam();			
		}
		Iterator<Row> rowIterator = sheet.iterator();

		Row headerRow = sheet.getRow(0);
		int columnMax = headerRow.getLastCellNum();
		for (int col = 0; col < columnMax; col++) {
			Cell cell = headerRow.getCell(col);
			String columnName = cell.getStringCellValue();

			switch (columnName) {

			case "Employee Name":
				columnIndex[0] = cell.getColumnIndex();
			}
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			team = new String();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getColumnIndex() == columnIndex[0]) {
					team= cell.getStringCellValue();
					if (team.equals(searchString)) {
						return row.getRowNum();
					}
				}
			}

		}
		return 0;
	}
	
	
	private static List<TaskDetails> returnTask(XSSFSheet sheet,
			String searchString) {

		List<TaskDetails> teamDetails = new ArrayList<TaskDetails>();
		TaskDetails team = null;
		int columnIndex[] = new int[6];

		Iterator<Row> rowIterator = sheet.iterator();

		Row headerRow = sheet.getRow(0);
		int columnMax = headerRow.getLastCellNum();
		for (int col = 0; col < columnMax; col++) {
			Cell cell = headerRow.getCell(col);
			String columnName = cell.getStringCellValue();

			switch (columnName) {
			case "Employee Name":
				columnIndex[0] = cell.getColumnIndex();
			case "Task":
				columnIndex[1] = cell.getColumnIndex();
			case "Task Description":
				columnIndex[2] = cell.getColumnIndex();
				break;
			case "Matrix Manager Name":
				columnIndex[5] = cell.getColumnIndex();
			}
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			team = new TaskDetails();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getColumnIndex() == columnIndex[0]) {
					team.setEmployeeName(cell.getStringCellValue());
				//	System.out.println("TEST name  "+cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[1]) {
					team.setTaskName(cell.getStringCellValue());
				//	System.out.println("TEST task  "+cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[2]) {
					team.setTaskDesc(cell.getStringCellValue());
				}
			}
			teamDetails.add(team);
		}

		List<TaskDetails> teamList = new ArrayList<TaskDetails>();
		for (TaskDetails record : teamDetails) {
			if(searchString.isEmpty() || searchString == null)
				teamList.add(record);
			else if (record.getEmployeeName().equals(searchString)) {
				teamList.add(record);
			}
		}
		return teamList;
	}
	
	
	private static List<TaskList> returnTaskList(XSSFSheet sheet,
			String searchString) {

		List<TaskList> teamDetails = new ArrayList<TaskList>();
		TaskList team = null;
		int columnIndex[] = new int[6];

		Iterator<Row> rowIterator = sheet.iterator();

		Row headerRow = sheet.getRow(0);
		int columnMax = headerRow.getLastCellNum();
		for (int col = 0; col < columnMax; col++) {
			Cell cell = headerRow.getCell(col);
			String columnName = cell.getStringCellValue();

			switch (columnName) {
			case "Key":
				columnIndex[0] = cell.getColumnIndex();
			case "Summary":
				columnIndex[1] = cell.getColumnIndex();
			case "Assignee":
				columnIndex[2] = cell.getColumnIndex();
				
			case "Priority":
				columnIndex[3] = cell.getColumnIndex();
			case "Status":
					columnIndex[4] = cell.getColumnIndex();
					break;
			}
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			team = new TaskList();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getColumnIndex() == columnIndex[0]) {
					team.setKey(cell.getStringCellValue());
				//	System.out.println("TEST name  "+cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[1]) {
					team.setSummary(cell.getStringCellValue());
				//	System.out.println("TEST task  "+cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[2]) {
					team.setAssignee(cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[3]) {
					team.setPriority(cell.getStringCellValue());
				}
				if (cell.getColumnIndex() == columnIndex[4]) {
					team.setStatus(cell.getStringCellValue());
				}
			}
			teamDetails.add(team);
		}

		List<TaskList> teamList = new ArrayList<TaskList>();
		for (TaskList record : teamDetails) {
			if(searchString.isEmpty() || searchString == null)
				teamList.add(record);
			else if (record.getKey().equals(searchString)) {
				teamList.add(record);
			}
		}
		return teamList;
	}
	
}
