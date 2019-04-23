<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${users }" var="user">
	<div style=" margin: 10px; background-color: white; border-radius: 15px;">
		<img class="head-img" style="margin: 20px"
			src="${pageContext.request.contextPath}/sources/img/head.jpg" />
		<span style="font-size: 18px; font-weight: 500;">${user['name'] }</span>
		
		<select onchange="privacy(${user['id'] },this)">
			<c:choose>
				<c:when test="${user['privacy'] eq 0 }">
					<option value="0" selected>只读</option>
					<option value="1">可编辑</option>
				</c:when>
				<c:when test="${user['privacy'] eq 1 }">
					<option value="0">只读</option>
					<option value="1" selected>可编辑</option>
				</c:when>
			</c:choose>
			<option value="2">删除</option>
		</select>
	</div>
</c:forEach>