package com.amdocs.pi;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
public class ExtractTableDOCX {
   public static void main(String[] args) {
	   try {
		   FileInputStream fis = new FileInputStream("D:/docx/read-test.docx");
		   XWPFDocument xdoc=new XWPFDocument(OPCPackage.open(fis));
		   Iterator<IBodyElement> bodyElementIterator = xdoc.getBodyElementsIterator();
		   while(bodyElementIterator.hasNext()) {
			   IBodyElement element = bodyElementIterator.next();
               if("TABLE".equalsIgnoreCase(element.getElementType().name())) {
				   List<XWPFTable> tableList =  element.getBody().getTables();
				   for (XWPFTable table: tableList){
					   System.out.println("Total Number of Rows of Table:"+table.getNumberOfRows());
					   System.out.println(table.getText());
				   }
			   }
		   }
		} catch(Exception ex) {
		    ex.printStackTrace();
		} 
   }
} 