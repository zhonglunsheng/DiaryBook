<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<script type="text/javascript">
	function checkForm(){
		var title=document.getElementById("title").value;
		var content=CKEDITOR.instances.content.getData();
		var typeId=document.getElementById("typeId").value;
		if(title==null||title==""){
			document.getElementById("error").innerHTML="标题不能为空！";
			return false;
		}
		if(content==null||content==""){
			document.getElementById("error").innerHTML="内容不能为空！";
			return false;
		}
		if(typeId==null||typeId==""){
			document.getElementById("error").innerHTML="请选择日志类别！";
			return false;
		}
		return true;
		}
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${diaryUpdate.diaryId!=null }">
				<img src="${pageContext.request.contextPath}/images/diary_type_edit_icon.png"/>
					修改日记</div>
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/images/diary_show_icon.png"/>
			日记信息</div>
			</c:otherwise>
		</c:choose>
		<form action="diary?action=save&diaryId=${diaryUpdate.diaryId }" method="post" onsubmit="return checkForm()">
			<div>
				<div style="margin-top:20px;text-align: center;"><input type="text" id="title"  name="title" value="${diaryUpdate.title }" class="input-xlarge"  style="margin-top:5px;height:30px;"  placeholder="日志标题..."/></div>
				<div>
					<textarea id="content" name="content">${diaryUpdate.content }</textarea>
				</div>
				<div style="margin-top:10px;">
					<select id="typeId" name="typeId">
						<option value="">请选择日志类别...</option>
						<c:forEach var="diaryType" items="${diaryTypes }">
							<option value="${diaryType.diaryTypeId }"
							${diaryType.diaryTypeId==diaryUpdate.typeId?'selected':''}>${diaryType.typeName }</option>
						</c:forEach>
					</select>
				</div>
				<div>
					<input type="submit" class="btn btn-primary" value="保存"/>
					<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
					<font id="error" color="red">${error }</font>  
				</div>
			</div>
		</form>
		<ckeditor:replace replace="content" basePath="ckeditor/ckeditor/" />
</div>
