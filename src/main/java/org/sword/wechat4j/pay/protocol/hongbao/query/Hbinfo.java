package org.sword.wechat4j.pay.protocol.hongbao.query;
/**
 * 裂变红包的领取列表 (微信文档是这样命名的-.-!!)
 * @author lixiaoyong
 *
 */
public class Hbinfo {
	/**
	 * 领取红包的openid
	 */
	private String openid;
	/**
	 * 领取金额
	 */
	private String status;
	private String amount;
	/**
	 * 领取红包的时间 
	 */
	private String rcv_time;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRcv_time() {
		return rcv_time;
	}
	public void setRcv_time(String rcv_time) {
		this.rcv_time = rcv_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "hbinfo [openid=" + openid + ", status=" + status + ", amount="
				+ amount + ", rcv_time=" + rcv_time + "]";
	}
	
	
}
