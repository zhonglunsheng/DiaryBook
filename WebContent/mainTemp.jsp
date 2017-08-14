<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>个人日记本主页</title>
<link href="${pageContext.request.contextPath}/style/diary.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap3/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap3/js/bootstrap.js"></script>
<style type="text/css">
	  body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
</style>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="#">学霸日志本</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="user?head=true"><i class="icon-home"></i>&nbsp;主页</a></li>
              <li class="active"><a href="diary?action=write"><i class="icon-pencil"></i>&nbsp;写日记</a></li>
              <li class="active"><a href="diaryType?action=list"><i class="icon-book"></i>&nbsp;日记分类管理</a></li>
              <li class="active"><a href="userCenter?action=user"><i class="icon-user"></i>&nbsp;个人中心</a></li>
            </ul>
          </div>
          <form name="myForm" class="navbar-form pull-right" method="post" action="user?all=search">
			  <input class="span2" id="s_title" name="s_title"  type="text" style="margin-top:5px;height:30px;" placeholder="往事如烟...">
			  <button type="submit" class="btn" onkeydown="if(event.keyCode==13) myForm.submit()"><i class="icon-search"></i>&nbsp;搜索日志</button>
		  </form>
        </div>
      </div>
</div>
<div class="container">
	<div class="row-fluid">
		<div class="span9">
			<jsp:include page="${mainPage }"></jsp:include>
		</div>
		<div class="span3">
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/images/user_icon.png"/>
					个人中心
				</div>
				<div class="user_image" style="text-align: center;">
					<img src="${myuser.imageName }"/>
				</div>
				<div class="nickName" style="text-align: center;"><br /><strong>${myuser.nickName}</strong></div>
				<div class="userSign" style="text-align: center;">(${myuser.mood })</div>
			</div>
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/images/byType_icon.png" />
					按日志类别
				</div>
				<div style="padding: 5px;">
					<ul style="list-style-type: none;">
						<c:forEach var="diaryType" items="${diaryTypes }">
						<li style="margin-top: 10px;"><a href="user?type_id=${diaryType.diaryTypeId}">${diaryType.typeName }(${diaryType.total })</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/images/byDate_icon.png"/>
					按日志日期
				</div>
				<div style="padding: 5px">
					<ul style="list-style-type: none">
						<c:forEach var="diaryDate" items="${diaryDate }"><li style="margin-top: 10px;"><a href="user?date_time=${diaryDate.releaseDateStr }">${diaryDate.releaseDateStr }(${diaryDate.dateTotal })</a></li></c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>