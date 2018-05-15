<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.amdocs.pi.XLSXReaderWriter" %>
<html>
     <% XLSXReaderWriter t = new XLSXReaderWriter();
        String str = t.returnTree();%>
  <head>  
      <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script> 
   <script type='text/javascript'>  
    google.load('visualization', '1', {packages:['orgchart']});  
    google.setOnLoadCallback(drawChart);  
    function drawChart() {  
     var data = new google.visualization.DataTable();  
     data.addColumn('string', 'Node');  
     data.addColumn('string', 'Parent');
     data.addRows(<%=str%>);
     var chart = new google.visualization.OrgChart(document.getElementById('chart_div'));
     chart.draw(data);  
    }
   </script>  
  </head>
  <body>
 <div id='chart_div'></div>
  </body>
</html>