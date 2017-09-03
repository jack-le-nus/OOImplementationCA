<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="at.nocturne.core.*, java.util.*" %> 
<!DOCTYPE html>
<%! Map<String, Class<?>> actions = NocturneActionMap.getInstance(); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Nocturne Framework is up <%= new java.util.Date() %></h2>
		<table border="1">
			<tr>
				<th>Mapping</th>
				<th>Class</th>
			</tr>
			<% for (String s: actions.keySet()) { %>
				<tr>
					<td><%= s %></td>
					<td><%= actions.get(s).getName() %></td>
				</tr>
			<% } %>
		</table>
    </body>
</html>
