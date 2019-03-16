<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.serializeJSON/2.9.0/jquery.serializejson.min.js"></script>
<script type="text/javascript">
function login(){
	$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/user/login",
			contentType:"application/json",
			dataType:"json",
			data:JSON.stringify($("#login_form").serializeJSON()),
			success:function(data){
				if (data.status == 200) {
					window.location="${pageContext.request.contextPath}/web/main";
				} else {
					$("#tip").html(data.msg);
				} 
			}});
}
</script>
</head>
<body>
<span id="tip" style="color:red"></span>
<form id="login_form">
<div><input name="email" type="text" placeholder="请输入登录邮箱"/></div>
<div><input name="password" type="password" placeholder="请输入密码"/></div>
<div><input type="button" onclick="login()" value="登录"/></div>
</form>
<a href="${pageContext.request.contextPath}/web/regist">注册</a>
<a href="${pageContext.request.contextPath}/web/forget">忘记密码</a>
</body>
</html>