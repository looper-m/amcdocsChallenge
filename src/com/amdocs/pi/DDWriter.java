package com.amdocs.pi;

import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DDWriter {

	public static void main(String[] args) {

		DDWriter pt = new DDWriter();
		// pt.getTable("DSL_INFO");
		// pt.getHeadings();
		pt.writeDD();
		System.out.println("---------------<<<<END>>>>---------------");
	}

	public void writeDD() {
		IBodyElement m_table = null;
		IBodyElement m_head = null, m_para = null;
		String currentStyle, lastStyle = "", lastToLastStyle = "";
		IBodyElement lastElement = null, currentElement = null;
		FileOutputStream fis;
			try {
				fis = new FileOutputStream("C:/KIAS/DOX_004621_DD_KIAS_CS1350_Safe_and_Secure_Network_Phase_2.docx");

				XWPFDocument document = new XWPFDocument();
				XWPFParagraph tmpParagraph = document.createParagraph();
				XWPFRun tmpRun = tmpParagraph.createRun();
				tmpRun.setText("LALALALAALALAAAA");
				tmpRun.setFontSize(18);
				document.write(fis);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
