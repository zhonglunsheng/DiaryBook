<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	function deleteDiary(diaryId){
		if(confirm("您确认删除这个日志吗？")){
			window.location="diary?action=delete&diaryId="+diaryId;
		}
	}
</script>
<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/diary_show_icon.png"/>
		日记信息</div>
		<div>
			<div style="margin-top:20px;text-align: center;"><h3>${diaryShow.title }</h3></div>
			<div style="text-align: center;">
				发布时间：『 <fmt:formatDate value="${diaryShow.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>』&nbsp;&nbsp;日志类别：${diaryShow.typeName}
			</div>
			<div style="margin-top:20px;">
				${diaryShow.content }
			</div>
			<div style="margin-top:20px;">
				<button class="btn btn-primary" type="button" onclick="javascript:window.location='diary?action=update&diaryId=${diaryShow.diaryId}'">修改日志</button>
				<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
				<button class="btn btn-danger" type="button" onclick="deleteDiary(${diaryShow.diaryId})">删除日志</button>
			</div>
		</div>
</div>
