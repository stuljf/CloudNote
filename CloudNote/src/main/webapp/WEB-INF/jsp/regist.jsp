<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
function regist(){
	$.post("${pageContext.request.contextPath}/user/regist",$("#regist_form").serialize(),
			function(data){
		if (data.status == 200) {
			window.location="${pageContext.request.contextPath}/web/login";
		} else {
			$("#tip").html(data.msg);
		} 
	},"json")
}

</script>
</head>
<body>
<span id="tip" style="color:red"></span>
<form id="regist_form">
<div><input name="name" type="text" placeholder="请输入用户名"/></div>
<div><input name="email" type="text" placeholder="请输入邮箱"/></div>
<div><input name="password" type="password" placeholder="请输入密码"/></div>
<div><input name="password_again" type="password" placeholder="请确认密码"/></div>
<div><input type="button" onclick="regist()" value="注册"/></div>
</form>
</body>
</html>