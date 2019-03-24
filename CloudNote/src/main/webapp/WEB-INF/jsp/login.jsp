<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>

<tmp:login title="登录">
<jsp:body>
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
					window.location="${pageContext.request.contextPath}/note/main";
				} else {
					$("#tip").html(data.msg);
					$("#tip").show();
				} 
			}});
}
</script>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<!-- Start Sign In Form -->
				<form id="login_form" class="fh5co-form" role="form">
				
						<h2>登录</h2>
						<div id="tip" class="alert alert-warning" style="display:none;"></div>
						<div class="form-group">
							<input type="text" class="form-control" name="email" placeholder="请输入邮箱账号"/>
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="password" placeholder="请输入密码"/>
						</div>
						<div class="form-group">
							<label><input type="checkbox" name="remember"> 保持登录</label>
						</div>
						<div class="form-group">
							<p>首次登录将自动注册账号&ensp;|&ensp;<a href="${pageContext.request.contextPath}/web/forget">忘记密码？</a></p>
						</div>
						<div class="form-group">
							<input type="button" value="登录" class="btn btn-primary" onclick="login()">
						</div>
					
				</form>
				<!-- END Sign In Form -->

			</div>
		</div>
	</div>

</jsp:body>
</tmp:login>