package com.amdocs.pi;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Test {

	public static void main(String[] args) {

		Test pt = new Test();
		// pt.getTable("DSL_INFO");
		// pt.getHeadings();
		pt.getBodyElements();
		System.out.println("---------------<<<<END>>>>---------------");
	}

	public LinkedHashMap<IBodyElement, List<IBodyElement>> getBodyElements() {
		IBodyElement m_table = null;
		IBodyElement m_head = null, m_para = null;
		List<IBodyElement> l_Tables = new ArrayList<IBodyElement>();
		List<IBodyElement> bodyText = new ArrayList<IBodyElement>();
		LinkedHashMap<IBodyElement, List<IBodyElement>> allItems = new LinkedHashMap<IBodyElement, List<IBodyElement>>();
		String currentStyle, lastStyle = "", lastToLastStyle = "";
		IBodyElement lastElement = null, currentElement = null;

		try {
			FileInputStream fis = new FileInputStream("C:/Users/mothilp/workspace/ApachePOI/test/read-test1.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			List<IBodyElement> xwpfElement = xdoc.getBodyElements();
			System.out.println(xwpfElement.size());
			int i = 0;
			for (IBodyElement para : xwpfElement) {

				if (para instanceof XWPFParagraph) {
					currentStyle = (((XWPFParagraph) para).getStyle());
					currentElement = para;
					// System.out.println(currentStyle+" : "+((XWPFParagraph)
					// para).getText());
					if (currentStyle != null && (currentStyle.equals("Heading1"))) {
						if (!allItems.containsKey(para))
							allItems.put((XWPFParagraph) para, new ArrayList<IBodyElement>());
						lastStyle = currentStyle;
						lastElement = para;
					}
				}
				if (allItems.containsKey(lastElement)) {
					bodyText = allItems.get(lastElement);
					bodyText.add(para);
					allItems.put(lastElement, bodyText);
				}
			}
			for (IBodyElement para : xwpfElement) {
				if (para instanceof XWPFParagraph) {
					currentStyle = (((XWPFParagraph) para).getStyle());
					currentElement = para;
					if (currentStyle != null && currentStyle.equals("Heading1"))
					{
						lastElement = null;
						continue;
					}
					if (currentStyle != null && (currentStyle.startsWith("Heading"))) {
						if (!allItems.containsKey(para))
							allItems.put((XWPFParagraph) para, new ArrayList<IBodyElement>());
						lastStyle = currentStyle;
						lastElement = para;
					}
				}
				if (allItems.containsKey(lastElement)) {
					bodyText = allItems.get(lastElement);
					bodyText.add(para);
					allItems.put(lastElement, bodyText);
				}
			}
			System.out.println("size=" + allItems.size());

			int rowIdx = 1, colIdx = 1;
			// read required paragraphs
			System.out.println("Headings and content: - ");
			System.out.println("size=" + allItems.size());
			System.out.println("---------------<<<<START>>>>---------------");
			for (Entry<IBodyElement, List<IBodyElement>> entry : allItems.entrySet()) {
				System.out.println("HEADING----> : " + ((XWPFParagraph) entry.getKey()).getText());
				ListIterator<IBodyElement> itr1 = ((List) entry.getValue()).listIterator();
				while (itr1.hasNext()) {
					try {
						IBodyElement temp_para = (IBodyElement) itr1.next();
						if (temp_para instanceof XWPFParagraph) {
							if (((XWPFParagraph) temp_para).getStyle() != null
									&& ((XWPFParagraph) temp_para).getStyle().contains("Heading")) {
								System.out.println("SubHeading : " + ((XWPFParagraph) temp_para).getText());
							}
							System.out.println(((XWPFParagraph) temp_para).getText());
						} else if (temp_para instanceof XWPFTable) {
							System.out.println("<<<<TABLE>>>>");
							// System.out.println(((XWPFTable)
							// temp_para).getText());

							XWPFTable tableinDoc = (XWPFTable) temp_para;

							rowIdx = 1;
							List<XWPFTableRow> tableRow = tableinDoc.getRows();
							for (XWPFTableRow xwpfTableRow : tableRow) {
								// System.out.println("Row -" + rowIdx);
								colIdx = 1;
								List<XWPFTableCell> tableCell = xwpfTableRow.getTableCells();
								for (XWPFTableCell xwpfTableCell : tableCell) {
									if (xwpfTableCell != null) {
										System.out.print("\t|" + xwpfTableCell.getText());
									}
									colIdx++;
								}
								System.out.println("");
								rowIdx++;
							}

							System.out.println("");
							System.out.println("<<<<TABLE>>>>");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("****************************************************************************");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return allItems;
	}

	void getHeadings() {
		IBodyElement m_head = null, m_para = null;
		List<IBodyElement> bodyText = new ArrayList<IBodyElement>();
		LinkedHashMap<XWPFParagraph, List> allItems = new LinkedHashMap<XWPFParagraph, List>();

		try {
			FileInputStream fis = new FileInputStream("C:/Users/mothilp/workspace/ApachePOI/test/read-test1.docx");
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
		List<IBodyElement> m_table = new ArrayList<IBodyElement>();
		LinkedHashMap<IBodyElement, List<IBodyElement>> allItems = new LinkedHashMap<IBodyElement, List<IBodyElement>>();
		// IBodyElement m_table = null;
		try {
			FileInputStream fis = new FileInputStream("C:/Users/mothilp/workspace/ApachePOI/test/read-test1.docx");
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			List<IBodyElement> xwpfElement = xdoc.getBodyElements();

			for (IBodyElement p : xwpfElement) {
				try {
					if (p instanceof XWPFParagraph)
						;
					// System.out.println(((XWPFParagraph)
					// p).getParagraphText().toString());
					if (p instanceof XWPFTable) {
						m_table.add(p);
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
