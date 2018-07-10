package org.sword.wechat4j.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.sword.lang.HttpUtils;
import org.sword.wechat4j.exception.WeChatException;
import org.sword.wechat4j.token.TokenProxy;
import org.sword.wechat4j.util.WeChatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 用户管理
 * 
 * @author Zhangxs
 * @version 2015-7-5
 */
public class UserManager {
	@SuppressWarnings("unused")
	private SimpleDateFormat fm=new SimpleDateFormat("yyyy年MM月dd日"); 
	Logger logger = Logger.getLogger(UserManager.class);
	// private String
	// accessToken;////不能在这里设定accessToken,否则service中所有用到UserManager userManager
	// = new UserManager(),时候token变成唯一值，TokenProxy更新了，这里也不更新
	// 获取用户列表
	private static final String USRE_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
	// 设置用户备注名
	private static final String USER_UPDATE_REMARK_POST_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=";
	// 获取用户基本信息
	private static final String USER_INFO_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
	// 批量获取用户基本信息
	private static final String USER_INFO_LIST_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";
	// 创建分组
	private static final String GROUP_CREATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=";
	// 查询所有分组
	private static final String GROUP_GET_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=";
	// 查询用户所在分组
	private static final String GROUP_GETID_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=";
	// 修改分组名
	private static final String GROUP_UPDATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=";
	// 移动用户分组
	private static final String GROUP_MEMBERS_UPDATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=";
	// 批量移动用户分组
	private static final String GROUP_MEMBERS_DATCHUPDATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=";
	// 删除分组
	private static final String GROUP_DELETE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=";
	//群发信息
	private static final String Qun_Fa_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";
	// public UserManager() {
	// TokenProxy.accessToken() = TokenProxy.accessToken();
	// }
	/**
	 * 获取所有的关注者列表
	 * 
	 * @return
	 */
	public List<String> allSubscriber() {
		Follwers follwers = subscriberList();
		String nextOpenId = follwers.getNextOpenid();
		while (StringUtils.isNotBlank(nextOpenId)) {
			Follwers f = subscriberList(nextOpenId);
			nextOpenId = f.getNextOpenid();
			if (f.getData() != null) {
				follwers.getData().getOpenid().addAll(f.getData().getOpenid());
			}
		}
		return follwers.getData().getOpenid();
	}

	/**
	 * 获取帐号的关注者列表前10000人
	 * 
	 * @return
	 */
	public Follwers subscriberList() {
		return subscriberList(null);
	}

	/**
	 * 获取帐号的关注者列表
	 * 
	 * @param nextOpenId
	 * @return
	 */
	public Follwers subscriberList(String nextOpenId) {
		String url = USRE_GET_URL + TokenProxy.accessToken();
		if (StringUtils.isNotBlank(nextOpenId)) {
			url += "&next_openid=" + nextOpenId;
		}
		String resultStr = HttpUtils.get(url);
		logger.info("return data " + resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return JSONObject.parseObject(resultStr, Follwers.class);
	}

	/**
	 * 设置用户备注名
	 * 
	 * @param openid
	 *            用户openid
	 * @param remark
	 *            新的备注名，长度必须小于30字符
	 * @return
	 * @throws WeChatException
	 */
	public void updateRemark(String openId, String remark)
			throws WeChatException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openid", openId);
		jsonObject.put("remark", remark);
		String requestData = jsonObject.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(USER_UPDATE_REMARK_POST_URL
				+ TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		WeChatUtil.isSuccess(resultStr);
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param openid
	 *            普通用户的标识，对当前公众号唯一
	 * @return
	 */
	public User getUserInfo(String openId) {
		return getUserInfo(openId, null);
	}

	/**
	 * 批量获取用户信息
	 * 
	 * @param openIds
	 * @return
	 * @throws WeChatException
	 */
	public List<User> getUserInfoList(List<String> openIds) {
		JSONObject json = new JSONObject();
		List<User> userList = new ArrayList<User>();
		for (String openId : openIds) {
			User user = new User();
			user.setOpenId(openId);
			user.setLanguage("zh_CN");
			userList.add(user);
		}
		json.put("user_list", userList);
		String requestData = json.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(
				USER_INFO_LIST_GET_URL + TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			e.printStackTrace();
		}
		JSONObject userListJson = (JSONObject) JSON.parse(resultStr);

		List<User> result = JSONObject.parseArray(
				userListJson.get("user_info_list").toString(), User.class);
		return result;
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param openid
	 *            普通用户的标识，对当前公众号唯一
	 * @param lang
	 *            返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return
	 */
	public User getUserInfo(String openId, LanguageType lang) {
		String url = USER_INFO_GET_URL + TokenProxy.accessToken() + "&openid="
				+ openId;
		if (lang != null) {
			url += "&lang=" + lang.name();
		}
		String resultStr = HttpUtils.get(url);
		logger.info("return data " + resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			logger.error(e.getMessage());
			if (e.getMessage().indexOf("40001") != -1) {// //出现40001token过时
				TokenProxy.accessToken(true);
				logger.info("token强制刷新");
				return this.getUserInfo(openId, lang);
			}
			return null;
		}
		User user = JSONObject.parseObject(resultStr, User.class);
		return user;
	}

	/**
	 * 创建分组
	 * 
	 * @param name
	 *            分组名字（30个字符以内）
	 * @return
	 * @throws WeChatException
	 */
	public Group createGroup(String name) throws WeChatException {
		JSONObject nameJson = new JSONObject();
		JSONObject groupJson = new JSONObject();
		nameJson.put("name", name);
		groupJson.put("group", nameJson);
		String requestData = groupJson.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(
				GROUP_CREATE_POST_URL + TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		WeChatUtil.isSuccess(resultStr);
		return JSONObject.parseObject(resultStr)
				.getObject("group", Group.class);
	}

	/**
	 * 查询所有分组
	 * 
	 * @return
	 */
	public List<Group> getGroup() {
		String resultStr = HttpUtils.post(GROUP_GET_POST_URL
				+ TokenProxy.accessToken());
		logger.info("return data " + resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		JSONObject jsonObject = JSONObject.parseObject(resultStr);
		List<Group> groups = JSON.parseArray(jsonObject.getString("groups"),
				Group.class);
		return groups;
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param openId
	 *            用户的OpenID
	 * @return 用户所属的groupid
	 */
	public Integer getIdGroup(String openId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openid", openId);

		String requestData = jsonObject.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(
				GROUP_GETID_POST_URL + TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		JSONObject resultJson = JSONObject.parseObject(resultStr);
		int groupId = resultJson.getIntValue("groupid");
		return groupId;
	}

	/**
	 * 修改分组名
	 * 
	 * @param groupId
	 *            分组id
	 * @param name
	 *            分组名称
	 * @throws WeChatException
	 */
	public void updateGroup(int groupId, String name) throws WeChatException {
		JSONObject nameJson = new JSONObject();
		JSONObject groupJson = new JSONObject();
		nameJson.put("id", groupId);
		nameJson.put("name", name);
		groupJson.put("group", nameJson);
		String requestData = groupJson.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(
				GROUP_UPDATE_POST_URL + TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		WeChatUtil.isSuccess(resultStr);
	}

	/**
	 * 移动用户分组
	 * 
	 * @param openid
	 *            用户的OpenID
	 * @param groupId
	 *            分组id
	 * @throws WeChatException
	 */
	public void membersUpdateGroup(String openId, int groupId)
			throws WeChatException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openid", openId);
		jsonObject.put("to_groupid", groupId);
		String requestData = jsonObject.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(GROUP_MEMBERS_UPDATE_POST_URL
				+ TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		WeChatUtil.isSuccess(resultStr);
	}

	/**
	 * 批量移动用户分组
	 * 
	 * @param openids
	 *            用户唯一标识符openid的列表（size不能超过50）
	 * @param toGroupid
	 *            分组id
	 * @return 是否修改成功
	 * @throws WeChatException
	 */
	public void membersDatchUpdateGroup(String[] openIds, int groupId)
			throws WeChatException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openid_list", openIds);
		jsonObject.put("to_groupid", groupId);
		String requestData = jsonObject.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(GROUP_MEMBERS_DATCHUPDATE_POST_URL
				+ TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		WeChatUtil.isSuccess(resultStr);
	}

	/**
	 * 删除分组
	 * 
	 * @param groupId
	 * @throws WeChatException
	 */
	public void deleteGroup(int groupId) throws WeChatException {
		JSONObject idJson = new JSONObject();
		idJson.put("id", groupId);
		JSONObject groupJson = new JSONObject();
		groupJson.put("group", idJson);
		String requestData = groupJson.toJSONString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(
				GROUP_DELETE_POST_URL + TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		WeChatUtil.isSuccess(resultStr);
	}
	
	public void sendMsgQunFa(List<String> openIds){
		JSONObject json = new JSONObject();
		List<String> touser = new ArrayList<String>();
		for (String openId : openIds) {
			touser.add(openId);
		}
		json.put("touser", touser);
		Map<String,String> map = new HashMap<String,String>();
		map.put("content", "看到吗?");
		json.put("msgtype", "text");
		json.put("text", map);
		String requestData = json.toString();
		logger.info("request data " + requestData);
		String resultStr = HttpUtils.post(
				Qun_Fa_URL + TokenProxy.accessToken(), requestData);
		logger.info("return data " + resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 向列表用户发送指定模版的模版信息
	 * @param openIds 需要接受模版信息的用户列表
	 * @param templateIdShort 模板编号
	 * @return 成功true,失败false
	 * 
	 * @date 2016年4月12日 下午4:40:15
	 */
	/*public boolean sendTemplateMsg(List<DailyRemindVo> dailyRemindList,String templateIdShort){
		UserManager userManager = new UserManager();
		TemplateMsgBody msgBody= new TemplateMsgBody();
//		List<String>openIds = new ArrayList<String>();
//		for (DailyRemindVo vo : dailyRemindList) {
//			String openId = vo.getOpenId();
//			openIds.add(openId);
//		}
		List<TemplateMsgData> msgDataList = new ArrayList<TemplateMsgData>();
//		List<User> users= userManager.getUserInfoList(openIds);
		TemplateMsg msg = new TemplateMsg();
			for (DailyRemindVo user : dailyRemindList) {
				msgDataList.clear();
				////内容
				TemplateMsgData msgData0 = new TemplateMsgData();
//				msgData0.setColor("#66B3FF");			
				msgData0.setName("first");
				msgData0.setValue("尊敬的会员"+user.getNickName()+"，您好，您昨日的积分(活力值)增加如下");
				msgDataList.add(msgData0);
				////第一个参数
				TemplateMsgData msgData1 = new TemplateMsgData();
//				msgData1.setColor("#66B3FF");			
				msgData1.setName("FieldName");
				msgData1.setValue("结算时间");
				msgDataList.add(msgData1);
				////第二个参数
				TemplateMsgData msgData2 = new TemplateMsgData();
				msgData2.setColor("#66B3FF");			
				msgData2.setName("Account");
				msgData2.setValue(fm.format(new Date()));
				msgDataList.add(msgData2);
				////第三个参数
				TemplateMsgData msgData3 = new TemplateMsgData();
//				msgData3.setColor("#66B3FF");			
				msgData3.setName("change");
				msgData3.setValue("昨日");
				msgDataList.add(msgData3);
				////第三个参数
				TemplateMsgData msgData4 = new TemplateMsgData();
				msgData4.setColor("#66B3FF");			
				msgData4.setName("CreditChange");
				msgData4.setValue(user.getLastGain()+"");
				msgDataList.add(msgData4);
				////第三个参数
				TemplateMsgData msgData5 = new TemplateMsgData();
				msgData5.setColor("#66B3FF");			
				msgData5.setName("CreditTotal");
				msgData5.setValue(user.getBalance()+"");
				msgDataList.add(msgData5);
				////第三个参数
				TemplateMsgData msgData6 = new TemplateMsgData();
//				msgData6.setColor("#66B3FF");			
				msgData6.setName("Remark");
				msgData6.setValue("注：每日推送数据仅供参考，具体数据请进入系统查看哦");
				msgDataList.add(msgData6);

				
				msgBody.setData(msgDataList);
//				msgBody.setUrl("v.jianke.com");
				msgBody.setTopcolor("#66B3FF");
				msgBody.setTemplateId(templateIdShort);
				msgBody.setTouser(user.getOpenId());
				if(msg.send(msgBody)==null)
					return false;
			}
			return true;
	}*/
}
