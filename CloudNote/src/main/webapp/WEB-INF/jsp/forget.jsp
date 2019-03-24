<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:login title="找回密码">
<jsp:body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<!-- Start find password Form -->
				<form id="forget_form" class="fh5co-form" role="form">
					<h2>找回密码</h2>
					<div class="form-group">
						<div class="alert alert-success" role="alert" style="display:none;">Your email has been sent.</div>
					</div>
					<div class="form-group">
						<input type="email" class="form-control" id="email" placeholder="请输入邮箱账号" autocomplete="off">
					</div>
					<div class="form-group">
						<p><a href="${pageContext.request.contextPath}/web/login">登录/注册</a></p>
					</div>
					<div class="form-group">
						<input type="button" value="发送邮件" class="btn btn-primary">
					</div>
				</form>
				<!-- END find password Form -->

			</div>
		</div>
	</div>
</jsp:body>
</tmp:login>