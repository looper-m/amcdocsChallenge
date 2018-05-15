package com.amdocs.pi;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
public class BasicTextExtractor {
   public static void main(String[] args) {
	   try {
		   FileInputStream fis = new FileInputStream("//ANURAGR02/Users/anuragr/Desktop/pi/test/RCA_Serf_solution_DEKIASUPSPROBLEM-1047.docx");
		   XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
		   XWPFStyles styles=xdoc.getStyles();         
	        List<XWPFParagraph> xwpfparagraphs =xdoc.getParagraphs();
	        System.out.println();
	        for(int i=0;i<xwpfparagraphs.size();i++)
	        {
	           // System.out.println("paragraph style id "+(i+1)+":"+xwpfparagraphs.get(i).getStyleID());                         
	            if(xwpfparagraphs.get(i).getStyleID()!=null)
	            {
	                String styleid=xwpfparagraphs.get(i).getStyleID();
	                XWPFStyle style=styles.getStyle(styleid);
	                if(style!=null)
	                {
	                //    System.out.println("Style name:"+style.getName());
	                    if(style.getName().startsWith("heading"))
	                    {
	                    	System.out.println(xwpfparagraphs.get(i).getText());
	                    }
	                }
	            }
	        }
   
		  // XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
		   //System.out.println(extractor.getText());
		} catch(Exception ex) {
		    ex.printStackTrace();
		} 
   }
}
