<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	/* if(!session.isNew()) { 
		session.invalidate();
		request.setAttribute("error", null);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	} */
%>

<!doctype html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<title>登录</title>
	<link rel="stylesheet" href="_css/normalize.css">
	<link rel="stylesheet" href="_css/bootstrap.min.css">
	<link rel="stylesheet" href="_css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="fui/css/flat-ui.css">
	<link rel="stylesheet" href="_css/myFlatStyle.css">
	<link rel="stylesheet" href="_css/login.css">
	<link rel="shortcut icon" href="_img/favicon.ico">
	<script src="_js/jquery-2.1.0.min.js"></script>
	<script src="_js/bootstrap.min.js"></script>
	
	<!--[if IE]>
		<script>alert("This is IE");</script>
		<script src="_js/html5shiv.js"></script>
		<script src="_js/IE9.js"></script>
	<![endif]-->

</head>
<body>
	
	<form action="Login" method="post" id="loginForm">
		<h1>定值单管理系统</h1>
		<div class="form-group">
			<input type="text" id="username" class="form-control" name="username" placeholder="用户名" autofocus="autofocus" />
			<span class="input-icon fui-user"></span>
		</div>
		<div class="form-group">
			<input type="password" id="password" class="form-control" name="password" placeholder="密码" />
			<span class="input-icon fui-lock"></span>
		</div>
		<a id="login_btn" class="btn btn-primary btn-lg btn-block" onclick="$('#loginForm').submit()">登 录</a>
		<%
			String msg = (String)request.getAttribute("error");
			if(msg != null) out.print("<div id='error' class='alert alert-danger'>" + msg + "</div>");
		 %>
	</form>

	<script>
	$(document).ready(function() {
		var hw = $(window).height();
		var hf = $('#loginForm').css('height');
		hf = hf.substring(0, hf.length-2);
		var h = (hw-hf)*0.4;
		$('#loginForm').css('margin-top', h+"px");

		$(window).resize(function(event) {
			var hw = $(window).height();
			var hf = $('#loginForm').css('height');
			hf = hf.substring(0, hf.length-2);
			var h = (hw-hf)*0.4;
			$('#loginForm').css('margin-top', h+"px");
		});
		
		$("#username").keydown(function(e){
			if(compareKey(13, e)) {
				$("#password").focus();
			}
		});

		$("#password").keydown(function(e){
			if(compareKey(13, e)) {
				$("#login_btn").click();
			}
		});

		function compareKey(arg, evt){
			var key;
			if(window.event){
				key = evt.keyCode;
			} else {
				key = evt.which;
			}
			return (key == arg) ? true : false;
		}
		
		/* var error = $("#error");
		console.info("error.text(): " + error.text());
		console.info("error.text().length: " + error.text().length);
		
		if(error.text() != "       ") {
			if(!error.hasClass('alert alert-danger')) {
				error.addClass("alert alert-danger");
			}
		} else {
			if(error.hasClass('alert alert-danger')) {
				error.removeClass("alert alert-danger");
			}
		}  */
		
	});
	</script>

</body>
</html>