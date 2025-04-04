  <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%--  <%@ page contentType="text/html" pageEncoding="UTF-8"%>
 <%@page import="java.sql.*;" %>--%>
<%@ page import="java.sql.DriverManager" %> 
<%@ page import="java.sql.*" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 
</head>
<body>
<center>
<label style="margin-right:150px;">Company Group:</label>
<select class="form-control" style="width:250px;">
<option>ALL</option>
<%

try{
	
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@//ushdbld00061:16209/d375.tfayd.com","PRMDBO","dbo34dev");
	//Statement stm=conn.createStatement();
	//String Query="select * from companyg";
	 PreparedStatement ps=conn.prepareStatement("select * from companyg");
	ResultSet rs=ps.executeQuery();
	while(rs.next()){
		%>
		<option><%=rs.getString("COMPANY_GROUP")%></option>
		<% 
	}
}
catch(Exception ex){
	ex.printStackTrace();
	out.println("Error "+ex.getMessage());
}

%>
</select>
<form action="FetchData" method="post">
<input type="submit" value="Get All Record">
</form>
</center>
</body>
</html>