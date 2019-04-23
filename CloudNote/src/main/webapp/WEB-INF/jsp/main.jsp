<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tmp:edit title="云笔记">
<jsp:body>
<script type="text/javascript">
	//初始化页面
	$(document).ready(function(){
		getNotes(-1,"所有笔记");
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
	
	function getShare(){
		$.get("${pageContext.request.contextPath}/note/share",
				function(data){
					$("#notes").html(data);
				}
			);
	}
	
	function getFriend(){
		$.get("${pageContext.request.contextPath}/user/friend",
				function(data){
					$("#notes").html(data);
				}
			);
	}
	function newnote(){
		$.get("${pageContext.request.contextPath}/note/create",
			function(data){
				if(data.status==200){
					location.href="${pageContext.request.contextPath}/edit/edit?id="+data.data;
				}
			}
		);
		
	}
</script>
<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-1">
			<ul class="list-unstyled side-content">
				<li><a href="#" onclick="getNotes(-1,'所有笔记')">
					<span class="glyphicon glyphicon-time"></span> 所有笔记
				</a></li>
				<li><a href="#" onclick="getShare()">
					<span class="glyphicon glyphicon-link"></span> 与我共享
				</a></li>
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
				<li><a href="#" onclick="getFriend()">
					<span class="glyphicon glyphicon-user"></span> 我的好友
				</a></li>
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