<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".note-line").hover(function(){
		$(this).css("background-color","#f5f6f7");
		$(this).find(".note-option").css("visibility","visible");
	},function(){
		$(this).css("background-color","white");
		$(this).find(".note-option").css("visibility","hidden");
	});
})

function delAll(){
	$.get("${pageContext.request.contextPath}/recycle/clear",
			function(data){
				if(data.status==200){
					$('#delAllModal').modal('hide');
					$('#delAllModal').on('hidden.bs.modal', function () {
						  getNotes(-1,"最近访问");
					});
				}
			}
		);
}

function onsub(id){
	$("#subid").val(id);
}

function del(){
	var id=$("#subid").val();
	$.get("${pageContext.request.contextPath}/recycle/delete?id="+id,
			function(data){
				if(data.status==200){
					$('#delModal').modal('hide');
					$('#delModal').on('hidden.bs.modal', function () {
						  getDelete();
					});
				}
			}
		);
}

function rec(){
	var id=$("#subid").val();
	$.get("${pageContext.request.contextPath}/recycle/recover?id="+id,
			function(data){
				if(data.status==200){
					$('#recModal').modal('hide');
					$('#recModal').on('hidden.bs.modal', function () {
						  getDelete();
					});
				}
			}
		);
}

</script>
<div style="padding:20px;">
	<span id="title">${title}</span>
	<button id="btn-new" type="button" class="btn btn-info pull-right" data-toggle="modal" data-target="#delAllModal">清空</button>
</div>
<table class="table table-style text-center">
	<thead>
		<tr id="table-title">
			<th>标题</th>
			<th>创建人</th>
			<th>笔记本</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${notes }" var="note">
			<tr class="note-line">
				<td><a href="${pageContext.request.contextPath}/note/edit?id=${note['id'] }">${note['title'] }</a></td>
				<td>${note['name'] }</td>
				<td>${note['book'] }</td>
				<td><div class="dropdown note-option">
					<button type="button" class="dropdown-toggle" data-toggle="dropdown">
		            	<span class="glyphicon glyphicon-chevron-down"></span>
		            </button>
		            <ul class="dropdown-menu">
			            <li><a href="#" onclick="onsub(${note['id'] })" data-toggle="modal" data-target="#delModal">彻底删除</a></li>
			            <li><a href="#" onclick="onsub(${note['id'] })" data-toggle="modal" data-target="#recModal">还原</a></li>
		            </ul>
		        </div></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<!-- 用于记录当前操作的笔记id -->
<input id="subid" name="id" style="display:none;" />
<!-- 清空笔记的模态框 -->
<div class="modal fade" id="delAllModal" tabindex="-1" role="dialog" aria-labelledby="delAllModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="delAllModalLabel">清空笔记</h4>
            </div>
            <div class="modal-body">确认要将回收站中的笔记清空吗？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="delAll()">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 删除笔记的模态框 -->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="delModalLabel">删除笔记</h4>
            </div>
            <div class="modal-body">确认要将该笔记彻底删除吗？</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="del()">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 还原笔记的模态框 -->
<div class="modal fade" id="recModal" tabindex="-1" role="dialog" aria-labelledby="recModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="recModalLabel">删除笔记</h4>
            </div>
            <div class="modal-body">确认要还原该笔记吗？</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="rec()">还原</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>