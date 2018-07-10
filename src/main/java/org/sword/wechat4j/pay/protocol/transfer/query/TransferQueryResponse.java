package org.sword.wechat4j.pay.protocol.transfer.query;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 转账查询返回对象
 * @author lixiaoyong
 *
 */
@XmlRootElement(name = "xml")
public class TransferQueryResponse {
	/**
	 * SUCCESS/FAIL 
	 * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 */
	private String return_code;
	/**
	 * 返回信息，如非空，为错误原因 
	 * 签名失败 
	 * 参数格式校验错误 
	 */
	private String return_msg;
	/**
	 * SUCCESS/FAIL 
	 */
	private String result_code;
	/**
	 * 错误码信息 
	 */
	private String err_code;
	/**
	 * 结果信息描述 
	 */
	private String err_code_des;
	/**
	 * 商户使用查询API填写的单号的原路返回. 
	 */
	private String partner_trade_no;
	/**
	 * 微信支付分配的商户号
	 */
	private String mch_id;
	/**
	 * 调用企业付款API时，微信系统内部产生的单号 
	 */
	private String detail_id;
	/**
	 * SUCCESS:转账成功 
	 * FAILED:转账失败 
	 * PROCESSING:处理中
	 */
	private String status;
	/**
	 * 如果失败则有失败原因 
	 */
	private String reason;
	/**
	 * 转账的openid 
	 */
	private String openid;
	/**
	 * 收款用户姓名 
	 */
	private String transfer_name;
	/**
	 * 付款金额单位分
	 */
	private String payment_amount;
	/**
	 * 发起转账的时间
	 */
	private String transfer_time;
	/**
	 * 付款时候的描述 
	 */
	private String desc;
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTransfer_name() {
		return transfer_name;
	}
	public void setTransfer_name(String transfer_name) {
		this.transfer_name = transfer_name;
	}
	public String getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(String payment_amount) {
		this.payment_amount = payment_amount;
	}
	public String getTransfer_time() {
		return transfer_time;
	}
	public void setTransfer_time(String transfer_time) {
		this.transfer_time = transfer_time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "TransferQueryResponse [return_code=" + return_code
				+ ", return_msg=" + return_msg + ", result_code=" + result_code
				+ ", err_code=" + err_code + ", err_code_des=" + err_code_des
				+ ", partner_trade_no=" + partner_trade_no + ", mch_id="
				+ mch_id + ", detail_id=" + detail_id + ", status=" + status
				+ ", reason=" + reason + ", openid=" + openid
				+ ", transfer_name=" + transfer_name + ", payment_amount="
				+ payment_amount + ", transfer_time=" + transfer_time
				+ ", desc=" + desc + "]";
	}
}
