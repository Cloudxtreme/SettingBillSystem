<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String user = (String) session.getAttribute("username");
	if (user == null) {
		response.sendRedirect("login.jsp");
	}
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
	<title>模板 - 定值单系统</title>
	<link rel="stylesheet" href="_css/normalize.css">
	<link rel="stylesheet" href="_css/bootstrap.min.css">
	<link rel="stylesheet" href="_css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="_css/tablesorterstyle.css">
	<link rel="stylesheet" href="_css/myFlatStyle.css">
	<link rel="stylesheet" href="_css/template.css">
	<link rel="shortcut icon" href="_img/favicon.ico">
	<script src="_js/jquery-2.1.0.min.js"></script>
	<script src="_js/bootstrap.min.js"></script>
	<script src="_js/jquery.json-2.4.min.js"></script>
	<script src="_js/jquery.tablesorter.min.js"></script>
	
	<!--[if IE]>
		alert("This is IE");
		<script src="_js/html5shiv.js"></script>
		<script src="_js/IE9.js"></script>
	<![endif]-->

	<script src="_js/template.js"></script>
	
</head>
<body>
	<nav id="navbar" class="navbar navbar-default navbar-fixed-top" role="navigation" >

		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a id="companyName" class="navbar-brand" href="#">${companyName}</a>
		</div>
	
		<div class="collapse navbar-collapse" id="navbar-collapse">
			<div id="navbar-collapse">
				<ul id="mainNav" class="nav navbar-nav">
					<li><a href="index.jsp">定值单</a></li>
					<li class="active"><a href="template.jsp">模板</a></li>
				</ul>
				<ul id="account" class="nav navbar-nav navbar-right">
					<li class="divider"></li>
					<li><a id="user" href="#">${displayName}(${username})</a></li>
					<li><a id="exit" onclick="$('#logoutForm').submit()">退出</a></li>
				</ul>
				<form id="logoutForm" action="Logout" method="post"></form>
			</div>
		</div>
	</nav>

	<div id="mainContent" class="row">
		<div id="templateList" class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
			<div id="toolbar">
				<a class="btn btn-primary" id="addTemplate">
					<span class="glyphicon glyphicon-plus"></span>创建
				</a>
			</div>
			<div class="table-responsive">
				<table id="templates" class="table table-hover tablesorter">
					<thead>
						<tr class='header'>
							<th>模板编码</th>
							<th>模板名称</th>
							<th>创建者</th>
							<th>创建时间</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
		
		<div id="preview" class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
			<p id="preview_placeholder">请从左边列表中选择一份模板</p>
			<!-- <fieldset id="detail"></fieldset> -->
		</div>
	</div>

	<script>
	$(document).ready(function() {
		var hw = $(window).height();
		var hn = $('#navbar').css('height');
		var hbt = $('body').css('padding-top');
		var hbb = $('body').css('padding-bottom');
		hn = hn.substring(0, hn.length-2);
		hbt = hbt.substring(0, hbt.length-2);
		hbb = hbb.substring(0, hbb.length-2);
		// var h = hw-hn-hbt-hbb;
		var h = hw-hbt-hbb;
		$('#templateList').css('height', h+"px");
		$('#preview').css('height', h+"px");

		$(window).resize(function(event) {
			var hw = $(window).height();
			var hn = $('#navbar').css('height');
			var hbt = $('body').css('padding-top');
			var hbb = $('body').css('padding-bottom');
			hn = hn.substring(0, hn.length-2);
			hbt = hbt.substring(0, hbt.length-2);
			hbb = hbb.substring(0, hbb.length-2);
			// var h = hw-hn-hbt-hbb;
			var h = hw-hbt-hbb;
			$('#templateList').css('height', h+"px");
			$('#preview').css('height', h+"px");
		});
	});
	
	function deleteCookie(the_cookie){
		$.cookie(the_cookie, null);
	}
	
	function logout() {
		deleteCookie('JSESSIONID');
		$("#logoutForm").submit();		
	}
	</script>

</body>
</html>
