package com.woyun.streambank.util.weichatpay;

public class WeiChatPayConfig {
	//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
		// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
		// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

		public static final String KEY = "9D584208FD0F27250F4434BDF94E95E6";

		//微信分配的公众号ID（开通公众号之后可以获取到）
		public static final String APPID = "wxd466177e74705232";

		//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		public static final String MCHID = "1301665601";

		//微信支付异步通知的地址
		public static final String NOTIFY_URL = "http://119.29.187.161:8080/streambank/weiChatPay/notify_url.do";
		
		//微信支付
		public static final String WEICHAT_PAY_TYPE = "NATIVE";
		//受理模式下给子商户分配的子商户号
//		public static final String SUBMCHID = "";

		//HTTPS证书的本地路径
//		public static final String CERTLOCALPATH = "";

		//HTTPS证书密码，默认密码等于商户号MCHID
//		public static final String CERTPASSWORD = "";

		//是否使用异步线程的方式来上报API测速，默认为异步模式
		public static final boolean USETHREADTODOREPORT = true;

		//机器IP
//		public static final String IP = "";

		
		
		//以下是几个API的路径：
		//统一下单地址
		public static final String UNIFIEDORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		//1）被扫支付API
		public static final String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

		//2）被扫支付查询API
		public static final String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

		//3）退款API
		public static final String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

		//4）退款查询API
		public static final String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

		//5）撤销API
		public static final String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

		//6）下载对账单API
		public static final String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

		//7) 统计上报API
		public static final String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

		public static final String HTTPSREQUESTCLASSNAME = "com.tencent.common.HttpsRequest";

}
