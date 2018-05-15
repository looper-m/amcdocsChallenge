<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.amdocs.pi.ReadSSTCases"%>
<%@ page import="com.amdocs.pi.SstImpact"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.ArrayList"%>
<% ReadSSTCases cases= new ReadSSTCases();
   String key = request.getParameter("val");
   ArrayList<SstImpact> al= cases.getRecord(key);
   Iterator<SstImpact> itr = al.iterator();
   while(itr.hasNext())
   {
	   SstImpact si= itr.next();
	   out.println("<br>");	   out.println("<br>");
	   out.println(si.getImpactedFun());
	   out.println("<br>");
	   out.println(si.getSstOutline());
	   out.println("<br>");
   }
   %>