<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>产品查询</title>
</head>
<body>
	
	<div class="page_title">
		产品管理
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="window.location.href='${ctp }/product/create'">
			产品添加
		</button>
		<button class="common_button" onclick="document.forms[0].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp }/product/list">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					名称
				</th>
				<td>
					<input type="text" name="search_LIKES_name" />
				</td>
				<th>
					型号
				</th>
				<td>
					<input type="text" name="search_LIKES_type" />
				</td>
				<th>
					批次
				</th>
				<td>
					<input type="text" name="search_LIKES_batch" />
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
	</form>	
	
	<c:if test="${page.numberOfElements == 0 }">没有任何记录</c:if>	
	<c:if test="${page.numberOfElements > 0 }">		
		
	<table class="data_list_table" border="0" cellPadding="3"
		cellSpacing="0">
		<tr>
			<th>
				编号
			</th>
			<th>
				名称
			</th>
			<th>
				型号
			</th>
			<th>
				等级/批次
			</th>
			<th>
				单位
			</th>
			<th>
				单价（元）
			</th>
			<th>
				备注
			</th>
			<th>
				操作
			</th>
		</tr>
		<c:forEach items="${page.content }" var="item">
			<tr>
				<td class="list_data_number">
					${item.id }
				</td>
				<td class="list_data_ltext">
					${item.name }
				</td>
				<td class="list_data_text">
					${item.type }
				</td>
				<td class="list_data_text">
					${item.batch }
				</td>
				<td class="list_data_text">
					${item.unit }
				</td>
				<td class="list_data_number">
					${item.price }
				</td>
				<td class="list_data_ltext">
					${item.memo }
				</td>
				<td class="list_data_op">
					<img onclick="window.location.href='${ctp }/product/toupdate?id=${item.id }'" 
						title="编辑" src="${ctp }/static/images/bt_edit.gif" class="op_button" />
					<img onclick="window.location.href='${ctp }/product/delete?id=${item.id }'" 
						title="删除" src="${ctp }/static/images/bt_del.gif" class="op_button" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<tags:pagination123 paginationSize="5" page="${page }"></tags:pagination123>
	</c:if>	
	
			
</body>
</html>