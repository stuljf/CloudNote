<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".note-line").hover(function(){
		$(this).css("background-color","#f5f6f7");
	},function(){
		$(this).css("background-color","white");
	});
})
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
			<th>权限</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${notes }" var="note">
			<tr class="note-line">
				<td><a href="${pageContext.request.contextPath}/edit/edit?id=${note['id'] }">${note['title'] }</a></td>
				<td>${note['name'] }</td>
				<td>${note['book'] }</td>
				<td>
					<c:choose>
						<c:when test="${note['privacy'] eq 0 }"><c:out value="只读"></c:out></c:when>
						<c:when test="${note['privacy'] eq 1 }"><c:out value="可编辑"></c:out></c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>