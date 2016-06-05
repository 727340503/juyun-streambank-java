package com.woyun.streambank.util.alipay;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	//alipay异步回调的返回结果
	public static final String ALIPAY_NOTIFY_ERROR = "error";
	public static final String ALIPAY_NOTIFY_ERROR_SIGN = "签名校验失败";
	
	public static final String ALIPAY_NOTIFY_SUCCESS = "success";
	
	public static final String ALIPAY_SERVICE = "create_direct_pay_by_user";
	
	public static final String NOTIFY_URL = "http://119.29.187.161:8080/streambank/alipay/alipay_notify.do";//异步回调地址
	
	public static final String RETURN_URL = "http://119.29.187.161:8080/streambank/alipay/alipay_return.do";//页面同步通知
	
//	public static final String COMMIT_TYPE = "post";
	public static final String COMMIT_TYPE = "get";
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static final String PARTNER = "2088911048325074";
	
	// 收款支付宝账号，一般情况下收款账号就是签约账号
	public static final String SELLER_EMAIL = "juyungz@wepower365.com";
	// 商户的私钥
	public static final String KEY = "xwmzjey612877ehjyqrleto096nwkaif";
	public static final String ALIPAY_RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANVNbxnuhufY+uZOF45zNAlHULi7Z8VRWxOjmj3pimeksYlikuF041K8ug/y0jASl/PjzWES5JLXSKU3WylQBtehBTAlHDQTGBHF7DzCaD0quMHwQDKtmcYjR5CReFldvBmXedRKW1b45UQNdE7WDMXXZwmsySQ1RCCGxsyGw313AgMBAAECgYA0kdkH8xmcmoV7rVNw8EOq5cCnRsjUgLCJrpCdIanS0WxQB9NbbYt5ILpao+XZozuACGzdt+JNrnFhBruY+vg0SXLAyKKYoRpOa5BezWYNu5ACe6W6cxTIa1jccFEQwnX/3i9V9Xw+9xAWi0HcD3uvsadddlOJoM5E5eCZcyGtQQJBAOuwa+i+osJucZJuXwdEFudhrmRoEFllVe/LmADWzdrMIu7Ug8HQPtRDgrT8ZtUevMXf4wdmtHOas4DdM0YqQN0CQQDnryLl9tfhq4kIBR6PEPQnOjKuE2CzD1vI9K8BQ9N5s3H25COyuzS/FW491b7mj3WFTp08gkNPKssqmJZG44hjAkA6bNNzML68IBC7UezwqipbVVWbwkg7QOmatgeKPbVuBairN7cptmu8xreCEgeT0TjOfH+U+BrHhZP139+0BwlpAkBfhEHkAzePj1PIXavyma+++Gxqfgyw5dUWbWI1KExocUW73uSkzC971A8GKTgx/9UYp4eFqcKsQd1O62eRRD83AkBEKBS9c5j0WE/tOzdKJ4g3UpK1JhRHAwV3Dx6XocwKBshA0bnaHhNw43itYdsMBWXMSnG4AgG1m07/bVO+Kt1q";
//   public static final String ALIPAY_RSA_PRIVATE = "MIICWwIBAAKBgQDVTW8Z7obn2PrmTheOczQJR1C4u2fFUVsTo5o96YpnpLGJYpLhdONSvLoP8tIwEpfz481hEuSS10ilN1spUAbXoQUwJRw0ExgRxew8wmg9KrjB8EAyrZnGI0eQkXhZXbwZl3nUSltW+OVEDXRO1gzF12cJrMkkNUQghsbMhsN9dwIDAQABAoGANJHZB/MZnJqFe61TcPBDquXAp0bI1ICwia6QnSGp0tFsUAfTW22LeSC6WqPl2aM7gAhs3bfiTa5xYQa7mPr4NElywMiimKEaTmuQXs1mDbuQAnulunMUyGtY3HBREMJ1/94vVfV8PvcQFotB3A97r7GnXXZTiaDOROXgmXMhrUECQQDrsGvovqLCbnGSbl8HRBbnYa5kaBBZZVXvy5gA1s3azCLu1IPB0D7UQ4K0/GbVHrzF3+MHZrRzmrOA3TNGKkDdAkEA568i5fbX4auJCAUejxD0JzoyrhNgsw9byPSvAUPTebNx9uQjsrs0vxVuPdW+5o91hU6dPIJDTyrLKpiWRuOIYwJAOmzTczC+vCAQu1Hs8KoqW1VVm8JIO0DpmrYHij21bgWoqze3KbZrvMa3ghIHk9E4znx/lPgax4WT9d/ftAcJaQJAX4RB5AM3j49TyF2r8pmvvvhsan4MsOXVFm1iNShMaHFFu97kpMwve9QPBik4Mf/VGKeHhanCrEHdTutnkUQ/NwJARCgUvXOY9FhP7Ts3SieIN1KStSYURwMFdw8el6HMCgbIQNG52h4TcON4rWHbDAVlzEpxuAIBtZtO/21Tvirdag==";
	public static final String ALIPAY_RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVTW8Z7obn2PrmTheOczQJR1C4u2fFUVsTo5o96YpnpLGJYpLhdONSvLoP8tIwEpfz481hEuSS10ilN1spUAbXoQUwJRw0ExgRxew8wmg9KrjB8EAyrZnGI0eQkXhZXbwZl3nUSltW+OVEDXRO1gzF12cJrMkkNUQghsbMhsN9dwIDAQAB";


	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static final String INPUT_CHARSET = "UTF-8";
	
	// 签名方式 不需修改
	public static final String SIGN_TYPE = "MD5";

}