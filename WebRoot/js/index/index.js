$(function(){
	//初始化充值方法
	$('.business-bar > span').on('tap',function(e){
		e.preventDefault();
		var childs =$(this).parent().children(),panels = $('.business-panels').children();
		childs.removeAttr('active');
		panels.hide();
		childs.eq($(this).index()).attr('active','');
		panels.eq($(this).index()).show();
		$('input[type="tel"]').val('');
		$('#phone').val('');
	});
	$('.business-panels').children().eq(0).show();

	//选择套餐绑定事件
	$('.packages-item input').change(function(){
		var price = $(this).attr('data-price');
		if(!price)return;
		price = price.split('.');
		$('.price-box .text').html('￥<span>' + price[0] + '</span>' + (price.length > 1 ? '.' + price[1]: ''));
	});
//	$('#phone').blur(function(){
//		get_package();
//	});
	$('#phone').bind('input propertychange',function(){
		var phone = $('#phone').val();
		if(phone.length >= 11){
			get_package();
		}
	});
	$('#buy-btn').bind('click',function(){
		recharge_flow();
	});
})

function get_package(){
	var phone = $('#phone').val().trim();
	if(phone.length < 11){
		return;
	}
	if(phoneTest($('#phone'))){
		var phone = $('#phone').val();
		var url = project+'/package/getProducts.json'
		var data = {};
		$('.spinner-f').show();
		data.phone = phone;
		$.post(
			url,
			data,
			function(ret){
				var result = ret.result;
				if(result.status == 0){
					$('#national_traff').html('');
					var discount = parseFloat(result.data.discount);
					$(result.data.packageList).each(function(index){
						var price = (this.Price*discount).toFixed(2);
						var text = '<li class="packages-item"><div class="control-radio"><input class="input" id="packages-'+index+'" name="packages" type="radio"  data-name="'+this.Package+'" data-price="'+price+'" /><label class="label" for="packages-'+index+'">'+this.Name+'<span></span></label></div></li>';
						$('#national_traff').append(text);
					});
					var text = '<li class="clear"></li>';
					$('#national_traff').append(text);
					$('.control-radio > .input:checked + .label').css('color','#fff');
					$('.business-panel .control-radio').css('color','#29BD8B');
					//选择套餐绑定事件
					$('.packages-item input').change(function(){
						var price = $(this).attr('data-price');
						if(!price)return;
						price = price.split('.');
						$('.price-box .text').html('￥<span>' + price[0] + '</span>' + (price.length > 1 ? '.' + price[1]: ''));
					});
				}else{
					$('#national_traff').html('');
					alert(result.message);
				}
				$('.price-box .text').html('￥<span>0.00</span>');
				$('.spinner-f').hide();
			}
		);
	};
}

function recharge_flow(){
	if(phoneTest($('#phone'))){
		var phone = $('#phone').val().trim();
		var flow = $('.control-radio > .input:checked + .label').prev().attr('data-name');
		if(flow == null || flow.length == 0){
//			if(phone.length = 11){
//				alert('请刷新页面后重试');
//				return;
//			}
			alert('请选择要充值的套餐');
			return;
		}
		var data = {};
		data.flow = flow;
		data.phone = phone;
		var data_str = BASE64.encoder(JSON.stringify(data));
		$('#reach_data').val(data_str);
		$('#phone').val('');
		var url = project+"/order/getPayToken.do";
		$('#recharge').attr("action",url);
		$('#recharge').submit();
	}
} 


/****校验手机号的方法****/
function phoneTest(obj){
	var phone = obj.val().trim();
	if(phone.length == 0){
		alert('请输入手机号');
		return;
	}
	var phoneReg = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
	if(!phoneReg.test(phone)){
		alert('请输入正确的手机号')
		return false; 
	}
	return true;
}
