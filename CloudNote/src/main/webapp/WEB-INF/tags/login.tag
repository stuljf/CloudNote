<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true" rtexprvalue="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title }</title>

<!-- 引入jquery -->
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.serializeJSON/2.9.0/jquery.serializejson.min.js"></script>
<!-- 引入bootstrap -->
<link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.js"></script>

<!-- 引入自定义样式 -->
<link href="${pageContext.request.contextPath }/sources/css/login.css" rel="stylesheet">

</head>
<body>
	<jsp:doBody />
</body>
</html>