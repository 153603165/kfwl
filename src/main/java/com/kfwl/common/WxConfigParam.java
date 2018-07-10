package com.kfwl.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * JSSDK参数
 * 
 *
 */
@Getter
@Setter
public class WxConfigParam {
	/**
	 * 公众号的唯一标识
	 */
	private String appId;
	/**
	 * 生成签名的时间戳
	 */
	private Long timestamp;
	/**
	 * 生成签名的随机串
	 */
	private String nonceStr;
	/**
	 * 签名
	 */
	private String signature;
	/**
	 * 请求API的集合
	 */
	private List<String> jsApiList;

}
