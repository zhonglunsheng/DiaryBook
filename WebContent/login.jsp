<%@page import="com.lipop.model.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String userName=null;
	String password=null;
	Cookie[] cookie= request.getCookies();
	for(int i=0;cookie!=null && i<cookie.length;i++)
	{
		if(cookie[i].getName().equals("user"))
		{
			userName=cookie[i].getValue().split("-")[0];
			password=cookie[i].getValue().split("-")[1];
		}
		
		if(userName==null)
		{
			userName="";
		}
		
		if(password==null)
		{
			password="";
		}
		
		pageContext.setAttribute("user", new User(userName,password));
	}
%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap-theme.min.css">
<script src="${pageContext.request.contextPath}/bootstrap3/js/bootstrap.min.js"></script>
<title>学霸日志本</title>
<script type="text/javascript">
	/*检查用户名或密码是否为空*/
	function checkForm(){
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("password").value;
		if(userName=="" || userName==null)
		{
			document.getElementById("error").innerHTML="用户名不能为空";
			return false;
		}
		if(password=="" || password==null)
		{
		document.getElementById("error").innerHTML="密码不能为空";
			return false;
		}
		return true;
	}
	/*重置内容*/
	function rest()
	{
		document.getElementById("userName").value="";
		document.getElementById("password").value="";
		document.getElementById("remember").checked=false;
	}
</script>
<style type="text/css">
		body{
			padding-top: 150px;
			padding-bottom: 40px;
		}
      	.form-signin-heading{
      	text-align: center;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 0px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
	      .form-signin .checkbox {
	        margin-bottom: 10px;
	   }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
	        font-size: 16px;
	        height: auto;
	        margin-bottom: 15px;
	        padding: 7px 9px;
	  }
</style>
</head>
<body>
<div class="container">
<form name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkForm()">
        <h2 class="form-signin-heading">学霸日志本</h2>
        <input id="userName" name="userName" value="${user.userName }"  type="text" class="input-block-level" placeholder="学霸ID">
        <input id="password" name="password" value="${user.password }"   type="password" class="input-block-level" placeholder="学霸秘钥" >
        <label class="checkbox">
        <input id="remember" name="remember" type="checkbox" value="remember-me">记住我 &nbsp;&nbsp;&nbsp;&nbsp; <font id="error" color="red">${error }</font>  
        </label>
        <button class="btn btn-large btn-primary" type="submit">登录</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-large btn-primary" type="button" >重置</button>
</form>
</div>
</body>
</html>