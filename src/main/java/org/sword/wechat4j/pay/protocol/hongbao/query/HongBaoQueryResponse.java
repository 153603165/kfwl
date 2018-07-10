package org.sword.wechat4j.pay.protocol.hongbao.query;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class HongBaoQueryResponse {
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
	 * 签名
	 */
	private String sign;
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
	 * 商户使用查询API填写的商户单号的原路返回 
	 */
	private String mch_billno;
	/**
	 * 微信支付分配的商户号 
	 */
	private String mch_id;
	/**
	 * 使用API发放现金红包时返回的红包单号 
	 */
	private String detail_id;
	/**
	 * SENDING:发放中 
	 * SENT:已发放待领取 
	 * FAILED：发放失败 
	 * RECEIVED:已领取 
	 * REFUND:已退款
	 */
	private String status;
	/**
	 * API:通过API接口发放 
	 * UPLOAD:通过上传文件方式发放 
	 * ACTIVITY:通过活动方式发放
	 */
	private String send_type;
	/**
	 * GROUP:裂变红包 
	 * NORMAL:普通红包 
	 */
	private String hb_type;
	/**
	 * 红包个数
	 */
	private String total_num;
	/**
	 * 红包总金额(单位分)
	 */
	private String total_amount;
	/**
	 * 发送失败原因 
	 */
	private String reason;
	/**
	 * 发送时间
	 */
	private String send_time;
	/**
	 * 红包的退款时间(如果其未领取的退款)
	 */
	private String refund_time;
	/**
	 * 红包退款金额 
	 */
	private String refund_amount;
	/**
	 * 祝福语 
	 */
	private String wishing;
	/**
	 * 活动描述，低版本微信可见
	 */
	private String remark;
	/**
	 * 发红包的活动名称 
	 */
	private String act_name;
	/**
	 * 裂变红包的领取列表 
	 */
	private Hbinfo hblist;
	/**
	 * 领取红包的openid
	 */
	private String openid;

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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public String getSend_type() {
		return send_type;
	}
	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}
	public String getHb_type() {
		return hb_type;
	}
	public void setHb_type(String hb_type) {
		this.hb_type = hb_type;
	}
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getRefund_time() {
		return refund_time;
	}
	public void setRefund_time(String refund_time) {
		this.refund_time = refund_time;
	}
	public String getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}


	public Hbinfo getHblist() {
		return hblist;
	}
	public void setHblist(Hbinfo hblist) {
		this.hblist = hblist;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Override
	public String toString() {
		return "HongBaoQueryResponse [return_code=" + return_code
				+ ", return_msg=" + return_msg + ", sign=" + sign
				+ ", result_code=" + result_code + ", err_code=" + err_code
				+ ", err_code_des=" + err_code_des + ", mch_billno="
				+ mch_billno + ", mch_id=" + mch_id + ", detail_id="
				+ detail_id + ", status=" + status + ", send_type=" + send_type
				+ ", hb_type=" + hb_type + ", total_num=" + total_num
				+ ", total_amount=" + total_amount + ", reason=" + reason
				+ ", send_time=" + send_time + ", refund_time=" + refund_time
				+ ", refund_amount=" + refund_amount + ", wishing=" + wishing
				+ ", remark=" + remark + ", act_name=" + act_name + ", hblist="
				+ hblist.toString() + ", openid=" + openid ;
	}
}
