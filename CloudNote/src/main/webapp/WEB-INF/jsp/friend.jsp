<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".note-line").hover(function(){
		$(this).find(".note-option").css("visibility","visible");
	},function(){
		$(this).find(".note-option").css("visibility","hidden");
	});
})

function ondel(id){
	$("#delid").val(id);
}

function del(){
	var id=$("#delid").val();
	$.get("${pageContext.request.contextPath}/user/delete?id="+id,
			function(data){
				if(data.status==200){
					$('#delModal').on('hidden.bs.modal', function () {
						  getFriend();
					});
					$('#delModal').modal('hide');
				}else {
					$("#tip-del").html(data.msg);
					$("#tip-del").show();
				} 
			}
		);
}

function add(){
	var email=$("#add-email").val();
	$.get("${pageContext.request.contextPath}/user/add?email="+email,
			function(data){
				if(data.status==200){
					$('#addModal').modal('hide');
					$('#addModal').on('hidden.bs.modal', function () {
						  getFriend();
					});
				}else {
					$("#tip-add").html(data.msg);
					$("#tip-add").show();
				} 
			}
		);
}
</script>
<div style="padding:20px;">
	<span id="title">${title}</span>
	<button id="btn-new" type="button" class="btn btn-info pull-right" data-toggle="modal"
					data-target="#addModal">添加</button>
</div>
<div>
	<c:forEach items="${users }" var="user">
		<div class="note-line"
			style="width: 45%; margin: 10px; float: left; background-color: white">
			<img class="head-img" style="margin: 20px"
				src="${pageContext.request.contextPath}/sources/img/head.jpg" />
			<span style="font-size: 18px; font-weight: 500;">${user.name }</span>
			<div class="note-option pull-right">
				<button style="background-color: white;" type="button"
					onclick="ondel(${user.id })" data-toggle="modal"
					data-target="#delModal">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
			</div>
		</div>
	</c:forEach>
</div>

<!-- 删除好友的模态框 -->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="delModalLabel">删除好友</h4>
            </div>
            <div class="modal-body">确认要将该好友删除吗？
            	<input id="delid" name="id" style="display:none;" />
            	<div id="tip-del" class="alert alert-warning" style="display:none;"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="del()">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 添加好友的模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="addModalLabel">添加好友</h4>
            </div>
			<div class="modal-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<label for="email">请输入好友的邮箱：</label>
						<input id="add-email" class="form-control" type="text" name="email" />
					</div>
				</form>
				<div id="tip-add" class="alert alert-warning" style="display:none;"></div>
			</div>
			<div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="add()">添加</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>