<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true" rtexprvalue="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath }/sources/img/logo.ico" />
<title>${title }</title>

<!-- 引入jquery -->
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.serializeJSON/2.9.0/jquery.serializejson.min.js"></script>
<!-- 引入bootstrap -->
<link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.js"></script>

<!-- 引入自定义样式 -->
<link href="${pageContext.request.contextPath }/sources/css/edit.css" rel="stylesheet">
</head>
<body>
	<nav class="navbar" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<img id="logo" src="${pageContext.request.contextPath }/sources/img/logo.ico" />
			CloudNote
		</div>
		<div>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"	data-toggle="dropdown">
						<img class="head-img" src="${pageContext.request.contextPath }/sources/img/head.jpg"/>
					</a>
					<ul class="dropdown-menu">
						<li><a>昵称：${user.name }</a></li>
						<li><a>邮箱：${user.email }</a></li>
						<li class="divider"></li>
						<li><a href="#">修改用户昵称</a></li>
						<li><a href="#">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="#">退出登录</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	</nav>
	<jsp:doBody />
</body>
</html>