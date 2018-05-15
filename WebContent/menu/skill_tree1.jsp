<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.amdocs.pi.XLSXReaderWriter" %>
<%@ page import="com.amdocs.pi.TaskReaderWriter" %>
<%@ page import="com.amdocs.pi.TaskDetails" %>
<%@ page import="java.util.Iterator" %>
<html>  
 <% XLSXReaderWriter t = new XLSXReaderWriter();
 String str = t.returnTree();%>
  <head>  
  
  <style>
input.MyButton {
width: 100px;
padding: 10px;
font-weight: bold;
font-size: 100%;
background: #3366CC;
color: #FFFFFF;
cursor: pointer;
border: 1px solid #999999;
border-radius: 10px;
-webkit-box-shadow: 6px 6px 5px #999999;
-moz-box-shadow: 6px 6px 5px #999999;
box-shaddow: 6px 6px 5px; #999999;
}
input.MyButton:hover {
color: #FFFF00;
background: #3366CC;
border: 1px solid #A3A3A3;
-webkit-box-shadow: 2px 2px 5px #666666;
-moz-box-shadow: 2px 2px 5px #666666;
box-shaddow: 2px 2px 5px; #666666;
}
</style>
  
  		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Task</title>
  		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
  
   <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
   <script type='text/javascript'>  
         google.charts.load('current', {packages:["orgchart"]});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Name');
        data.addColumn('string', 'Manager');
        data.addColumn('string', 'ToolTip');

        // For each orgchart box, provide the name, manager, and tooltip to show.
        data.addRows(<%=str%>);  

        // Create the chart.
        var chart = new google.visualization.OrgChart(document.getElementById('chart_div'));
        // Draw the chart, setting the allowHtml option to true for the tooltips.
        
            var runonce = google.visualization.events.addListener(chart, 'ready', function() {
        // set up + sign event handlers
        var previous;
        $('#chart_div').on('click', 'div', function (){
            var selection = chart.getSelection();
            var row;
            if (selection.length == 0) {
                row = previous;
            }
            else {
                row = selection[0].row;
                previous = row;
            }
            console.log(this);
            var collapsed = chart.getCollapsedNodes();
            var collapse = (collapsed.indexOf(row) == -1);
            chart.collapse(row, collapse);
            chart.setSelection();
        });
        
        // remove this event listener *before* collapsing nodes
        // otherwise this runs multiple times
        google.visualization.events.removeListener(runonce);
        
        // collapse all nodes
        for (var i = 0; i < data.getNumberOfRows(); i++) {
            chart.collapse(i, true);
        }
    });
        
       chart.draw(data, {
        allowHtml: true
    });
      }
   </script>  
  </head>  
  <body>  
   <div id='chart_div'></div>
   <br><br><br><br><br><br>
   		<div class="container">
			<div class="component">
    <% TaskReaderWriter trw = new TaskReaderWriter();
    Iterator<TaskDetails> itr =(trw.readTask("")).iterator();
    	StringBuilder task= new StringBuilder("");
    	while(itr.hasNext())
        {
        	TaskDetails td = itr.next();
        	task.append("<tr><td  class=\"user-name\">"+td.getEmployeeName());
        	if(td.getTaskName() != null)
        		task.append("</td><td  class=\"user-name\">"+td.getTaskName());
        	else
        		task.append("</td><td  class=\"user-name\">");
        	if(td.getTaskDesc() != null)
        		task.append("</td><td>"+td.getTaskDesc()+"<\td></tr>");
        	else
        		task.append("</td><td><form><input class=\"MyButton\" type=\"button\" value=\"Assign\" onclick=\"window.location.href='#'\" /></form> <\td></tr>");
        }
    %>
   <table  id="t01">
   <thead>
						<tr>
							<th>Employee Name	</th>
							<th>Task</th>
							<th>Task Description</th>
						</tr>
					</thead>
					<tbody>
     <%=task%>
     </tbody>
 </table>
   </div></div>

  </body>  
 </html>