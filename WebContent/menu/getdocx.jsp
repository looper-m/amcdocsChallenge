<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.amdocs.pi.Test"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="org.apache.poi.xwpf.usermodel.IBodyElement"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="org.apache.poi.xwpf.usermodel.IBodyElement"%>
<%@ page import="org.apache.poi.xwpf.usermodel.XWPFDocument"%>
<%@ page import="org.apache.poi.xwpf.usermodel.XWPFParagraph"%>
<%@ page import="org.apache.poi.xwpf.usermodel.XWPFTable"%>
<%@ page import="org.apache.poi.xwpf.usermodel.XWPFTableCell"%>
<%@ page import="org.apache.poi.xwpf.usermodel.XWPFTableRow"%>

<%
	Test document = new Test();

	LinkedHashMap<IBodyElement, List<IBodyElement>> allItems = document.getBodyElements();
	String key = request.getParameter("val");
	for (Entry<IBodyElement, List<IBodyElement>> entry : allItems.entrySet()) {

		String heading = ((XWPFParagraph) entry.getKey()).getText();
		if (heading.equals(key)) {
			ListIterator<IBodyElement> itr1 = ((List) entry.getValue()).listIterator();
			while (itr1.hasNext()) {
				try {
					IBodyElement temp_para = (IBodyElement) itr1.next();
					if (temp_para instanceof XWPFParagraph) {
						if (((XWPFParagraph) temp_para).getStyle() != null
								&& ((XWPFParagraph) temp_para).getStyle().contains("Heading")) {
						//	out.println("SubHeading :<b> " + ((XWPFParagraph) temp_para).getText());
							out.println("<br><br>");
						}
						out.println(((XWPFParagraph) temp_para).getText());
						out.println("<br><br>");

					} else if (temp_para instanceof XWPFTable) {
						out.println("<<<<TABLE>>>>");
						out.println(((XWPFTable) temp_para).getText());
						out.println("<<<<TABLE>>>>");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
%>