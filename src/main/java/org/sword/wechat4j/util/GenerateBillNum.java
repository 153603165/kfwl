package org.sword.wechat4j.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateBillNum {
	/**
	 * 生成28位随机数订单号
	 * @author lixiaoyong
	 *
	 */
	public static String generate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String preNumber    = RandomNumberGenerator.generate(4);
		String currentTime  = sdf.format(new Date());//14位
		String postNumber   = RandomNumberGenerator.generate(10);
		return preNumber + currentTime + postNumber;
	}
	/**
	 * 按照红包的订单号规则生成订单号
	 * @return
	 * 
	 * @date 2016年2月22日 上午9:35:01
	 */
	public static String generateForHongBao(String unid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String preNumber    = Config.instance().getMchId();//10位
		String preNumber    = RandomNumberGenerator.generate(10);
		String currentTime  = sdf.format(new Date());//8位
		String postNumber   = RandomNumberGenerator.generate(4);
		String localId = "";
		if(unid.length()<=6){
			for(int i=0;i<6-unid.length();i++){
				localId+="0";
			}
			localId+=unid;
			return preNumber + currentTime + postNumber + localId;
		}
		return preNumber + currentTime + postNumber + unid;
	}
	public static void main(String[] args) {
		System.out.println(generate());
		System.out.println(generateForHongBao("1"));
	}
}
