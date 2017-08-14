<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${diaryType.diaryTypeId!=null }">
				<img src="${pageContext.request.contextPath}/images/diary_type_edit_icon.png"/>
				修改日记类别
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/images/diaryType_add_icon.png"/>
				添加日记类别
			</c:otherwise>
		</c:choose>
		</div>
		<form action="diaryType?action=save&diaryTypeId=${diaryType.diaryTypeId==null?null:diaryType.diaryTypeId }" method="post" onsubmit="return checkForm()">
			<div class="diaryType_form" >
				<input type="hidden" id="diaryTypeId" name="diaryTypeId" value="${diaryType.diaryTypeId }"/>
					<table align="center">
						<tr>
							<td>类别名称：</td>
							<td><input type="text" id="typeName"  name="diaryTypeName" value="${diaryType.typeName }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><input type="submit" class="btn btn-primary" value="保存"/></td>
							<td><button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>&nbsp;&nbsp;<font id="error" color="red">${error }</font>  </td>
						</tr>
					</table>
			</div>
		</form>
</div>