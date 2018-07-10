package com.kfwl.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sword.wechat4j.common.Config;
import org.sword.wechat4j.token.TokenProxy;
import org.sword.wechat4j.util.RandomStringGenerator;

import com.kfwl.common.WxConfigParam;
import com.kfwl.utils.SHA1;

public class WxConfig {
	public void toSession(HttpServletRequest request) {
		WxConfigParam param = new WxConfigParam();
		param.setAppId(Config.instance().getAppid());
		param.setTimestamp(System.currentTimeMillis());
		param.setNonceStr(RandomStringGenerator.generate(28));
		param.setSignature(getSignature(param.getNonceStr(),
				param.getTimestamp() + "", request));
		HttpSession session = request.getSession();
		session.setAttribute("cfg", param);
	}

	/**
	 * 获取签名
	 * 
	 * @param nonceStr
	 * @param timestampstr
	 * @param request
	 * @return
	 * 
	 * @date 2016年1月30日 上午10:51:52
	 */
	public String getSignature(String nonceStr, String timestampstr,
			HttpServletRequest request) {
		String jsapi_ticket = TokenProxy.jsApiTicket();
		String url = request.getRequestURL().toString()
				+ (request.getQueryString() != null ? "?"
						+ request.getQueryString() : "");
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + timestampstr + "&url=" + url;// +"&wxref=mp.weixin.qq.com";
		return new SHA1().getDigestOfString(str.getBytes()).toLowerCase();
	}
}
