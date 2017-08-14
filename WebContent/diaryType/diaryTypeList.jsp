<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	function deleteTypes(diaryTypeId) {
		if (diaryTypeId!=null) {
			if(confirm("您确定删除改分类？")){
				window.location="diaryType?action=delete&diaryTypeId="+diaryTypeId;
			}
		}
	}
</script>
<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/list_icon.png"/>
		日记类别列表
		<span style="float: right;margin-right: 20px;">
			<button class="btn btn-mini btn-success" type="button" onclick="javascript:window.location='diaryType?action=write'">添加日志类别</button>
		</span>
		</div>
		<div>
			<table class="table table-hover table-striped">
			  <tr>
			  	<th>编号</th>
			  	<th>类别名称</th>
			  	<th>操作</th>
			  </tr>
			  <c:forEach var="diaryType" items="${diaryTypeList }">
			  	<tr>
			  		<td>${diaryType.diaryTypeId }</td>
			  		<td>${diaryType.typeName }</td>
			  		<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='diaryType?action=update&diaryTypeId=${diaryType.diaryTypeId}'">修改</button>&nbsp;<button class="btn btn-mini btn-danger" type="button" onclick="deleteTypes(${diaryType.diaryTypeId})">删除</button></td>
			  	</tr>
			  </c:forEach>
			</table>
			<div style="text-align: center;"><font style="color: red">${error }</font></div>
		</div>
</div>
