package com.amdocs.pi;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ParseDocx {

	public static void main(String[] args) {

		ParseDocx pt = new ParseDocx();
		// pt.getTable("DSL_INFO");
		// pt.getHeadings();
		pt.getBodyElements();
		System.out.println("hey... Its doneaaaa");
	}

	void getBodyElements() {
		IBodyElement m_table = null;
		IBodyElement m_head = null, m_para = null;
		List<IBodyElement> l_Tables = new ArrayList<IBodyElement>();
		List<IBodyElement> bodyText = new ArrayList<IBodyElement>();
		LinkedHashMap<IBodyElement, List<IBodyElement>> allItems = new LinkedHashMap<IBodyElement, List<IBodyElement>>();
		String currentStyle, lastStyle = "", lastToLastStyle = "";
		IBodyElement lastElement = null, currentElement = null;

		try {
			FileInputStream fis = new FileInputStream("C:/KIAS/read-test1.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			List<IBodyElement> xwpfElement = xdoc.getBodyElements();
			System.out.println(xwpfElement.size());
			int i = 0;
			for (IBodyElement para : xwpfElement) {

				// i++;
				// System.out.println("styles:-"+((XWPFParagraph) para).getStyle()+((XWPFParagraph) para).getText());
				if (para instanceof XWPFParagraph) {
					currentStyle = (((XWPFParagraph) para).getStyle());
				//	if (currentStyle != null) {
						//System.out.println(currentStyle+":"+((XWPFParagraph) para).getText());
					//	currentStyle = currentStyle.toString().trim();
						currentElement = para;
//						 switch (currentStyle) {
//						 case "Heading1":
//						 System.out.println(
//						 "Heading1<" + (++i) + ">" + ((XWPFParagraph)
//						 para).getParagraphText().toString());
//						 break;
//						 case "Heading2":
//						 System.out.println(
//						 "Heading2<" + (++i) + ">" + ((XWPFParagraph)
//						 para).getParagraphText().toString());
//						 break;
//						 case "Heading":
//						 System.out.println(
//						 "Para<" + (++i) + ">" + ((XWPFParagraph)
//						 para).getParagraphText().toString());
//						 break;
//						 case "BodyText" :
//						 System.out.println(
//						 "BodyText<" + (++i) + ">" + ((XWPFParagraph)
//						 para).getParagraphText().toString());
//						 break;
//						 default:
//						 break;
//						 }

						if (currentStyle != null && (currentStyle.equals("Heading1"))) {
							// System.out.println("adding key"+((XWPFParagraph)
							// para).getText());
							if (!allItems.containsKey(para))
							allItems.put((XWPFParagraph) para, new ArrayList<IBodyElement>());
							lastStyle = currentStyle;
							lastElement = para;
						} else if (currentStyle == null || (currentStyle.equals("Heading2") || currentStyle.equals("BodyText")) ) {

							if (allItems.containsKey(lastElement)) {
							//	System.out.println("adding value" + ((XWPFParagraph) para).getText());
								bodyText= allItems.get(lastElement);
								bodyText.add(para);
								allItems.put(lastElement, bodyText);
							//	System.out.println("added:-" + "key" + ((XWPFParagraph) lastElement).getText()+bodyText.size());
							}
						}
					//}
				} else if (para instanceof XWPFTable) {
					l_Tables.add(para);
					if (((XWPFTable) para).getText().contains("DSL_INFO")) {
						m_table = para;
						// break;
					}
				}
			}
			System.out.println("size=" + allItems.size());

			// read required table
			System.out.println("Table: - ");
			Iterator<IBodyElement> itr = l_Tables.iterator();
			while (itr.hasNext()) {
				XWPFTable temp_table = (XWPFTable) itr.next();
				// System.out.println(temp_table.getText());
				// System.out.println("**********************************************************************************");
			}

			// read required paragraphs
			System.out.println("Headings and content: - ");
			System.out.println("size=" + allItems.size());
			for (Entry<IBodyElement, List<IBodyElement>> entry : allItems.entrySet()) {
				System.out.println("Heading = " + ((XWPFParagraph) entry.getKey()).getText());
				Iterator<IBodyElement> itr1 = ((List) entry.getValue()).iterator();
				while (itr1.hasNext()) {
					XWPFParagraph temp_para = (XWPFParagraph) itr1.next();
					System.out.println(temp_para.getText());  //TODO:  get heading or whatever
				}System.out.println("**********************************************************************************");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void getHeadings() {
		IBodyElement m_head = null, m_para = null;
		List<IBodyElement> bodyText = new ArrayList<IBodyElement>();
		HashMap<XWPFParagraph, List> allItems = new HashMap<XWPFParagraph, List>();

		try {
			FileInputStream fis = new FileInputStream(
					"C:/Users/mothilp/workspace/ApachePOI/test/read-test.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			List<IBodyElement> xwpfElement = xdoc.getBodyElements();
			System.out.println(xwpfElement.size());
			int i = 0;
			for (IBodyElement para : xwpfElement) {
				if (para instanceof XWPFParagraph) {
					// i++;
					// System.out.println(((XWPFParagraph) para).getStyle()+"
					// ");
					if (((XWPFParagraph) para).getStyle() != null
							&& ((XWPFParagraph) para).getStyle().contains("Heading1")) {
						System.out
								.println("Para<" + (++i) + ">" + ((XWPFParagraph) para).getParagraphText().toString());
					}

					if (((XWPFParagraph) para).getText().contains("PSTN_RANGE_HIS")) {
						m_head = para;

						// break;
					}
				}
			}
			if (m_head != null)
				System.out.println("Heading: - " + ((XWPFParagraph) m_head).getText());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void getTable(String tname) {
		IBodyElement m_table = null;
		try {
			FileInputStream fis = new FileInputStream(
					"C:/Users/mothilp/workspace/ApachePOI/test/read-test.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			List<IBodyElement> xwpfElement = xdoc.getBodyElements();

			for (IBodyElement p : xwpfElement) {
				try {
					if (p instanceof XWPFParagraph)
						;
					// System.out.println(((XWPFParagraph)
					// p).getParagraphText().toString());
					if (p instanceof XWPFTable) {
						if (((XWPFTable) p).getText().contains("DSL_INFO")) {
							m_table = p;
							break;
						}
					}
				} catch (Exception e) {
				}
			}
			System.out.println(((XWPFTable) m_table).getText());

			List<XWPFTableRow> tabRow = ((XWPFTable) m_table).getRows();
			for (XWPFTableRow tableRow : tabRow) {
				List<XWPFTableCell> tabRow1 = tableRow.getTableCells();
				// if(tableRow.getCell(0).getText().contains("DSL_INFO"))
				// {
				for (XWPFTableCell tableCell : tabRow1) {
					// System.out.println(tableCell.getText());
				}
				// }
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
