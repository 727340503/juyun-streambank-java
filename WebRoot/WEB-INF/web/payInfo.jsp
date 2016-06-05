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
<title>流量银行-充值提示</title>
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
	.box-text{
		width:100%;
		text-align:center;
		margin:20px auto;
	}
	.box-text > div{
		width:80%;
	}
	.text-title{
		margin:0px auto;
		color:rgb(41,189,139);
		font-size:25px;
	}
	.text-msg{
		width:70% !important;
		text-align:left;
		margin:0px auto;
		margin-top:30px;
	}
	.text-msg > div{
		margin:20px 0 20px 0;
		color:rgb(235,107,1);
		font-size:20px;
		font-weight:bold;
	}
	.text-msg .text-center{
		color:#000000;
		font-weight:normal;
		font-size:18px;
	}
	.reach-btn{
		margin:0px auto;
		margin-top:40px;
	}
	.reach-btn .alipay{
		width:100%;
		height:40px;
		margin:10px auto;
		border-radius:5px;
		font-size:18px;
		color:#fff;
		background-color:rgb(41,189,139);
	}
	.reach-btn .weichat{
		width:100%;
		height:40px;
		margin:10px auto;
		border-radius:5px;
		font-size:18px;
		color:#fff;
		background-color:rgb(41,189,139);
	}
</style>
</head>
<body>
	<div class="max-box">
		<div class="title-box">
			<span class="text">流量充值-充值提示</span>
		</div>
		<div class="swipe-box" id="swipe-box">
			<div>
				<a class="swipe-item" href="javascript:;">
					<img class="swipe-img" src="${streambank}/res/images/banner1.gif" />
				</a>
			</div>
		</div><!-- sildes box end -->
		<div class="box-text">
			<div class="text-title">流量充值提示消息</div>
			<div class="text-msg">
				<div class="text-phone">
					<span>手机号</span>
					<span>:</span>
					<span class="text-center">${phone}</span>
				</div>
				<div class="text-flow">
					<span>流量</span>
					<span>:</span>
					<span class="text-center">${packageName}</span></div>
				<div class="text-price"> 
					<span>价格</span>
					<span>:</span>
					<span class="text-center">￥ ${price}</span></div>
				<div class="reach-btn">
					<div><input type="button" value="支付宝支付" class="alipay" id="alipay-btn"/></div>
					<div><input type="button" value="微信支付" class="weichat"id="weichat-btn"/></div>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript" src="${streambank}/js/jquery.min.js"></script>
<script type="text/javascript" src="${streambank}/js/jquery.mobile.custom.min.js"></script>
<script type="text/javascript" src="${streambank}/js/payInfo.js"></script>
<script type="text/javascript" src="${streambank}/js/public.js"></script>
</html>