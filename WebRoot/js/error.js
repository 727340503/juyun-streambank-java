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
	$('#btn-return').bind('click',function(){
		to_index();
	})
})

function to_index(){
	window.location.href = project+'/';
}
