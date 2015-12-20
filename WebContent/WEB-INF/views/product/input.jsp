<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>编辑产品信息</title>
</head>

<body class="main">
	
	<span class="page_title">编辑产品信息</span>
	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.go(-1);">
			返回
		</button>
		<button class="common_button" onclick="document.forms[0].submit();">
			保存
		</button>
	</div>
	
	<form:form action="${ctp }/product/save" method="POST" modelAttribute="product">
		
		<c:if test="${pro.id != null }">
			<input name="_method" value="PUT" type="hidden"/>
		</c:if>
		
		 <form:hidden path="id"/>
		
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					名称
				</th>
				<td>
					<input id="name" name="name" type="text" value="${product.name }"/>
				</td>
				<th>
					型号
				</th>
				<td>
					<input id="type" name="type" type="text" value="${product.type }"/>
				</td>
			</tr>
			<tr>
				<th>
					等级/批次
				</th>
				<td>
					<input id="batch" name="batch" type="text" value="${product.batch }"/>
				</td>
				<th>
					单位
				</th>
				<td>
					<input id="unit" name="unit" type="text" value="${product.unit }"/>
				</td>
			</tr>
			<tr>
				<th>
					单价
				</th>
				<td>
					<input id="price" name="price" type="text" value="${product.price }"/>
				</td>
				<th>
					备注
				</th>
				<td>
					<input id="memo" name="memo" type="text" value="${product.memo }"/>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
