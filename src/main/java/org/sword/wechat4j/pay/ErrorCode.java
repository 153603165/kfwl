package org.sword.wechat4j.pay;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
	private static Map<String,String> map = new HashMap<String,String>();
	public static Map<String, String> getErrorMessage(){
		map.put("CA_ERROR", "请求未携带证书，或请求携带的证书出错");
		map.put("SIGN_ERROR", "商户签名错误");
		map.put("NO_AUTH", "没有权限");
		map.put("FREQ_LIMIT", "受频率限制");
		map.put("XML_ERROR", "请求的xml格式错误，或者post的数据为空");
		map.put("PARAM_ERROR", "参数错误");
		map.put("SYSTEMERROR", "系统繁忙，请再试");
		map.put("NOT_FOUND ", "指定单号数据不存在");
		return map;
	}
}
