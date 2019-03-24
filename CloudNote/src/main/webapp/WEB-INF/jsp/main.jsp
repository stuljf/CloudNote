<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tmp:edit title="云笔记">
<jsp:body>
<script type="text/javascript">
	//用最近访问初始化页面
	$(document).ready(function(){
		getNotes(-1,"最近访问");
	})
	//获取笔记本中的笔记
	function getNotes(id, title){
		$.get("${pageContext.request.contextPath}/note/notes?id="+id+"&book="+title,
				function(data){
					$("#notes").html(data);
				}
			);
	}
	//获取回收站的笔记
	function getDelete(){
		$.get("${pageContext.request.contextPath}/recycle/notes",
				function(data){
					$("#notes").html(data);
				}
			);
	}
</script>
<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-1">
			<ul class="list-unstyled side-content">
				<li><a href="#" onclick="getNotes(-1,'最近访问')">
					<span class="glyphicon glyphicon-time"></span> 最近访问
				</a></li>
				<li><span class="glyphicon glyphicon-link"></span> 与我协作</li>
			</ul>
				
			<p class="side-title">笔记本 </p>
			<ul id="books" class="list-unstyled side-content">
				<c:forEach items="${books }" var="book">
				<li><a href="#" onclick="getNotes(-1,'${book}')">
					<span class="glyphicon glyphicon-book"></span> ${book }
				</a></li>
				</c:forEach>
			</ul>
			<p class="side-title">标签 </p>
			<ul id="tags" class="list-unstyled side-content">
				<c:forEach items="${tags }" var="tag">
				<li><a href="#" onclick="getNotes(${tag.id },'${tag.text }')">
					<span class="glyphicon glyphicon-tag"></span> ${tag.text }
				</a></li>
				</c:forEach>
			</ul>
			<p class="side-title"></p>
			<ul class="list-unstyled side-content">
				<li><span class="glyphicon glyphicon-user"></span> 我的好友</li>
				<li><a href="#" onclick="getDelete()">
					<span class="glyphicon glyphicon-trash"></span> 回收站
				</a></li>
			</ul>
		</div>
		<div id="notes" class="col-md-8">
		</div>
	</div>
</div>
</jsp:body>
</tmp:edit>