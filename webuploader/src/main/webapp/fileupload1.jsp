<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>普通文件上传方式</h2>
	<form action="${pageContext.request.contextPath}/FileUploadServlet" method="post" enctype="multipart/form-data">
		文件：<input type="file" value="请选择文件" name="file" /> <br/>
		信息：<input type="text" name="info" /> <br/>
		<input type="hidden" name="fileMd5" value="1520953795625"/>
		<input type="submit" value="提交" />
	</form>
</body>
</html>