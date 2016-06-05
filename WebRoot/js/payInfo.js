$(function(){
	//初始化充值方法
	$('.business-bar > span').on('tap',function(e){
		e.preventDefault();
		var childs =$(this).parent().children(),panels = $('.business-panels').children();
		childs.removeAttr('active');
		panels.hide();
		childs.eq($(this).index()).attr('active','');
		panels.eq($(this).index()).show();
	});
	
	$('#alipay-btn').bind('click',function(){
		window.location.href = project+'/order/createOrder.do?pay=1';
	});
})
