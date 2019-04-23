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

function ondel(id){
	$("#delid").val(id);
}

function del(){
	var id=$("#delid").val();
	$.get("${pageContext.request.contextPath}/note/delete?id="+id,
			function(data){
				if(data.status==200){
					$('#delModal').on('hidden.bs.modal', function () {
						  getNotes('${tagid}','${title}');
					});
					$('#delModal').modal('hide');
				}
			}
		);
}
</script>
<div style="padding:20px;">
	<span id="title">${title}</span>
	<button id="btn-new" type="button" class="btn btn-info pull-right" onclick="newnote()">新建</button>
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
				<td><a href="${pageContext.request.contextPath}/edit/edit?id=${note['id'] }">${note['title'] }</a></td>
				<td>${note['name'] }</td>
				<td>${note['book'] }</td>
				<td><div class="dropdown note-option">
					<button type="button" class="dropdown-toggle" data-toggle="dropdown">
		            	<span class="glyphicon glyphicon-chevron-down"></span>
		            </button>
		            <ul class="dropdown-menu">
			            <li><a href="#" onclick="ondel(${note['id'] })" data-toggle="modal" data-target="#delModal">删除</a></li>
			            <li><a href="#">移动到</a></li>
			            <li><a href="#">重命名</a></li>
		            </ul>
		        </div></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<!-- 删除笔记的模态框 -->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">删除笔记</h4>
            </div>
            <div class="modal-body">确认要将该笔记放入回收站吗？
            	<input id="delid" name="id" style="display:none;" />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="del()">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>