<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="streambank" />
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=3, minimum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-title" content="">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="msapplication-tap-highlight" content="no">
<meta name="format-detection" content="telephone=no">
<meta charset="utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>流量银行-手机流量充值平台</title>
<link rel="stylesheet" href="${streambank}/css/public.css" />
<link rel="stylesheet" href="${streambank}/css/spinner.css" type="text/css">
<style>
	body{background-color: #F3F3F3;}
	.max-box{padding-top: 40px;}
	.title-box > .right{
		width: 50px;
		height: 30px;
		color: #FFF;
		line-height: 30px;
		text-align: center;
		border: 1px #039362 solid;
		background-color: #0B9B6A;
		padding: 0;
		top: 5px;
	}
	.swipe-box{
		width: 100%;
		overflow: hidden;
		position: relative;
	}
	.swipe-item{
		position: relative;
		float: left;
	}
	.swipe-img{
		width: 100%;
		display: block;
	}
	.business{
		padding: 10px;
	}
	.business-panels{
		width: 100%;
		padding-top: 10px;
	}
	.box-center{
		width:100%;
		margin-top:30px;
	}
	.box-center .box-msg{
		width:90px;
		height:90px;
		line-height:120px;
		background-color:rgb(41,189,139);
		margin:0px auto;
		text-align:center;
		border-radius:50px;
	}
	.err-img{
		width:70px;
		height:70px;
		margin-top:8px;
	}
	.box-text{
		margin:10px auto;
		text-align:center;
		font-size:26px;
	}
	.msg-text{
		font-weight:bold;
		color:rgb(41,189,139);
	}
	.box-btn{
		width:100%;
		text-align:center;
	}
	.btn-return{
		width:70%;
		height:40px;
		margin:0px auto;
		background-color:rgb(41,189,139);
		margin-top:20px;
		border-radius:8px;
		font-size:20px;
		color:#fff;
	}
	.text-msg{
		margin:0px auto;
		width:80%;
		text-align:center;
		font-size:18px;
	}
</style>
</head>
<body>
	<div class="max-box">
		<div class="title-box">
			<span class="text">流量充值-友情提示</span>
		</div>
		<div class="swipe-box" id="swipe-box">
			<div>
				<a class="swipe-item" href="javascript:;">
					<img class="swipe-img" src="${streambank}/res/images/banner1.gif" />
				</a>
			</div>
		</div><!-- sildes box end -->
		<div class="box-center">
			<div class="box-msg">
				<img src="${streambank}/image/sus.png" class="err-img"/>
			</div>
			<div class="box-text">
				<div class="text-msg">
					<span class="msg-text">充值成功</span>
				</div>
				<div class="text-msg">
					<span class="msg-text">您冲值的流量会在24小时之内到账,请注意查收充值短信,谢谢您的支持！</span>
				</div>
			</div>
			<div class="box-btn">
				<input type="button" value="继续充值" class="btn-return" id="btn-return"/>
			</div>
		</div>
</body>
<script type="text/javascript" src="${streambank}/js/jquery.min.js"></script>
<script type="text/javascript" src="${streambank}/js/jquery.mobile.custom.min.js"></script>
<script type="text/javascript" src="${streambank}/js/public.js"></script>
<script type="text/javascript" src="${streambank}/js/error.js"></script>
</html>