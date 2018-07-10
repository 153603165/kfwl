package org.sword.wechat4j.pay.protocol.hongbao.query;

import javax.xml.bind.annotation.XmlRootElement;

import org.sword.wechat4j.common.Config;

@XmlRootElement(name = "xml")
public class HongBaoQueryRequest {
	/**
	 * 随机字符串，不长于32位 
	 */
	private String nonce_str;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 商户发放红包的商户订单号28位
	 */
	private String mch_billno;
	/**
	 * 微信支付分配的商户号 
	 */
	private String mch_id = Config.instance().getMchId();
	/**
	 * 微信分配的公众账号ID
	 */
	private String appid  = Config.instance().getAppid();
	/**
	 * MCHT:通过商户订单号获取红包信息。 
	 */
	private String bill_type;
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getBill_type() {
		return bill_type;
	}
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	@Override
	public String toString() {
		return "HongBaoQueryRequest [nonce_str=" + nonce_str + ", sign=" + sign
				+ ", mch_billno=" + mch_billno + ", mch_id=" + mch_id
				+ ", appid=" + appid + ", bill_type=" + bill_type + "]";
	}
}
