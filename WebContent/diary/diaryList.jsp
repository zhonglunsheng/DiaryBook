<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
	<div class="data_list_title">
	<img src="images/list_icon.png"/>&nbsp;日记列表
	</div>
	
	<div class="diary_datas">
			<ul style="list-style-type: none;">
				<c:forEach var="diary" items="${diaryList }">
					<li style="margin-top: 15px;">『<fmt:formatDate value="${diary.releaseDate }" type="date" pattern="yyyy-MM-dd"/>』<span>&nbsp;<a href="diary?action=show&&diaryId=${diary.diaryId }">${diary.title }</a></span></li>
				</c:forEach>
			</ul>
	</div>
	
	<div class="pagination pagination-centered">
			<ul>
				<h5>${error }</h5> ${pageCode }
			</ul>
		</div>
		
</div>
