package com.amdocs.pi;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
public class ExtractParagraphDOCX {
   public static void main(String[] args) {
	   try {
		   FileInputStream fis = new FileInputStream("D:/docx/read-test.docx");
		   XWPFDocument xdoc=new XWPFDocument(OPCPackage.open(fis));
		   List<XWPFParagraph> paragraphList =  xdoc.getParagraphs();
		   for (XWPFParagraph paragraph: paragraphList){
			   System.out.println(paragraph.getText());
		   }
		} catch(Exception ex) {
		    ex.printStackTrace();
		} 
   }
} 
