<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.amdocs.pi.Test"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="org.apache.poi.xwpf.usermodel.IBodyElement"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.util.Iterator"%>
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
		ArrayList subHeadings = new ArrayList();
		String heading = ((XWPFParagraph) entry.getKey()).getText();
		if (heading.contains(key)) {
			out.println("<br>");
			out.println("<div id=\"Headings\">");
			out.println("<a href=\"#\" onclick=\"return getData('" + heading + "');\">"
					+ ((XWPFParagraph) entry.getKey()).getText() + "</a>");
			out.println("</div>");
			out.println("<br>");
			ListIterator<IBodyElement> itr1 = ((List) entry.getValue()).listIterator();
			while (itr1.hasNext()) {
				try {
					IBodyElement temp_para = (IBodyElement) itr1.next();
					if (temp_para instanceof XWPFParagraph) {
						if (((XWPFParagraph) temp_para).getStyle() != null
								&& ((XWPFParagraph) temp_para).getStyle().contains("Heading")) {
							subHeadings.add(((XWPFParagraph) temp_para).getText());
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			Iterator<String> iterator = subHeadings.iterator();
			while (iterator.hasNext()) {
				String shead = iterator.next();
				out.println("<div id=\"sHeadings\">");
				out.println("<a id=\"sHeadings\" href=\"#\" onclick=\"return getData('" + shead
						+ "');\">&diams;&nbsp;" + shead + "</a>");
				out.println("</div>");
				out.println("<br>");
			}
		}
	}
%>