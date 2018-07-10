package com.kfwl.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * 存放各种静态常量的工具类
 * @author lizhuquan
 *
 */
public class StaticUtil
{
	/**
	 * 时间格式: yyyy-MM-dd'T'HH:mm:ss
	 * 	例如: 2015-08-06T15:37:33
	 */
	public final static SimpleDateFormat SDF_YYYYMMDDT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * 时间格式: yyyy-MM-dd
	 */
	public final static SimpleDateFormat SDF_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 100 BigDecimal
	 */
	public final static BigDecimal DECIMAL_HUNDRED = new BigDecimal(100);
	
	/**
	 * html标签匹配器
	 * 例如: <p> <div> </input>等, 匹配 开始标签 + 结束标签
	 */
	public final static Pattern PATTERN_HTML_TAG = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
	
	/**
	 * html代码匹配器
	 * 例如: <p>这是p标签</p>  <div>这是div标签</div>, 匹配 开始标签 + 标签内容 + 结束标签  
	 */
	public final static Pattern PATTERN_HTML_CODE = Pattern.compile("(<[a-zA-Z]+.*?>[\\s\\S]*?</[a-zA-Z]*>)", Pattern.MULTILINE);
	
	/**
	 * 邮箱匹配器
	 */
	public final static Pattern PATTERN_EMAIL = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");

	/**
	 * 手机号码匹配器
	 */
	public final static Pattern PATTERN_MOBILE = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
	
}
