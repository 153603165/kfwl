package com.kfwl;

import javax.servlet.http.HttpServletRequest;

import org.sword.wechat4j.WechatSupport;

public class Wechat extends WechatSupport {

	public Wechat(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onText() {
//		MyWeixin myWeixin = (MyWeixin) SpringUtil
//				.getApplicationContext().getBean("myWeixin");
//		myWeixin.getPageHtml("http://mp.weixin.qq.com/s?__biz=MzAwMDM0Nzk2NA==&mid=410282142&idx=3&sn=8773105cd9ddd11d549d1b5bcf6e1196&scene=0#rd");
		// TODO Auto-generated method stub
//		AccountManager accountManager = new AccountManager();
//		switch (this.wechatRequest.getContent()) {
//		case "token":
//			responseText(TokenProxy.accessToken());
//			break;
//		case "jsTicket":
//			responseText(TokenProxy.jsApiTicket());
//			break;
//		case "openId":
//			responseText(this.wechatRequest.getFromUserName());
//			break;
//		case "headimg":
//			responseText(new UserManager().getUserInfo(
//					this.wechatRequest.getFromUserName()).getHeadimgUrl());
//			break;
//		case "ticket":
//			responseText(this.wechatRequest.getTicket());
//			break;
//		case "qrcode":
//			MediaFile mediaFile = new MediaFile();
//			String qrcodeFile = super.request.getSession().getServletContext()
//					.getRealPath("/")
//					+ "/WEB-INF/qrcode.png";
//			Qrcode qrCode = accountManager.createQrcodeTemporary(2, 604800);
//			qrCode.getQrcode(qrcodeFile);
//			String mediaId = mediaFile.upload(new File(qrcodeFile),
//					MediaType.image);
//			System.out.println(mediaId);
//			System.out.println("mediaId--->" + mediaId);
//			responseImage(mediaId);
//			// Qrcode qrCode = accountManager.createQrcodeTemporary(55, 604800);
//			// responseText(qrCode.getQrcodeUrl());
//			break;
//		case "headImage":
//			User user = new UserManager().getUserInfo(this.wechatRequest
//					.getFromUserName());
//			String headImageUrl = user.getHeadimgUrl();
//			responseText(headImageUrl.substring(0, headImageUrl.length() - 1)
//					+ "96");
//			break;
//		case "spring":
//			MainController mainController = (MainController) SpringUtil
//					.getApplicationContext().getBean("mainController");
//			responseText("xxx");
//			break;
//		default:
//			responseText("你输入文字信息  " + this.wechatRequest.getContent());
//			break;
//		}
		responseText();
//		responseNew("哦嗨哟!", "快点进来看看~~~", "http://pic.yupoo.com/ttmeiju/DbDkWUJ6/medish.jpg", "v.jianke.com");
	}

	@Override
	protected void onImage() {
		// TODO Auto-generated method stub
//		responseText("你输入图片信息");
	}

	@Override
	protected void onVoice() {
		// TODO Auto-generated method stub
//		responseText("你输入声音信息");
	}

	@Override
	protected void onVideo() {
		// TODO Auto-generated method stub
//		responseText("你输入视频信息");
	}

	@Override
	protected void onShortVideo() {
//		 TODO Auto-generated method stub
//		responseText("你发了小视频信息");
	}

	@Override
	protected void onLocation() {
		// TODO Auto-generated method stub
//		responseText("你输入位置信息");
	}

	@Override
	protected void onLink() {
		// TODO Auto-generated method stub
//		responseText("你输入网页链接");
	}

	@Override
	protected void onUnknown() {
//		// TODO Auto-generated method stub
//		responseText("未知信息");
	}

	@Override
	protected void click() {
		responseText();
	}

	@Override
	protected void subscribe() {
//		UserManager userManager = new UserManager();
//		User user = userManager.getUserInfo(this.wechatRequest
//				.getFromUserName());
		//responseText("欢迎" + user.getNickName() + "关注本公众号");
		// responseText("欢迎" + user.getNickName() + "关注本公众号\n"
		// + "回复token,将获取accessToken\n" + "回复jsTicket,将获取jsapi-ticket\n"
		// + "回复openId,将获取用户的openId\n" + "回复qrcode,将获取账户二维码地址");
//		responseText("【福利】【福利】【福利】\n"
//				+ "嘿~ 亲爱的朋友，我们4月30日~5月3日活动期间，福利多多！活动只有三天哦，快动起你的手指参与进来吧！\n"
//				+ "一重福利~欣赏阅读文章活力值翻倍！0.03→0.08！\n"
//				+ "猛戳下方↓↓↓\n"
//				+ "    <a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2edff8e7edc68d25&redirect_uri=http%3A%2F%2Fwx.jksource.com%2Factivity%2Frw&response_type=code&scope=snsapi_base&state=test+info#wechat_redirect'>开始欣赏美文</a>\n"
//				+ "二重福利~邀请小伙伴一起来欣赏美文，活力值翻倍！0.5→1！\n"
//				+ "猛戳下方↓↓↓\n"
//				+ "    <a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2edff8e7edc68d25&redirect_uri=http%3A%2F%2Fwx.jksource.com%2Factivity%2Fhhr&response_type=code&scope=snsapi_base&state=test+info#wechat_redirect'>开始邀请伙伴</a>\n"
//				+ "三重福利~ 活动期间的总收益最高的前五名将获得额外的奖励");
//		
//		responseText("欢迎关注智客众传！同时也欢迎加入我们智客众传的官方服务群：102398323");
//		Integer cpsMemId = null;
//		if (StringUtils.isNotBlank(this.wechatRequest.getEventKey())) {
//			cpsMemId = Integer.parseInt(this.wechatRequest.getEventKey()
//					.replace("qrscene_", ""));
//		}
//		String openId = this.wechatRequest.getFromUserName();
//		/**
//		 * 注册用户
//		 */
//		WXAccountInfoController wXAccountInfoController = (WXAccountInfoController) SpringUtil
//				.getApplicationContext().getBean("wXAccountInfoController");
//		wXAccountInfoController.addAccountInfo(openId, cpsMemId);
	}

	@Override
	protected void unSubscribe() {
//		String openId = this.wechatRequest.getFromUserName();
//		WXAccountInfoController wXAccountInfoController = (WXAccountInfoController) SpringUtil
//				.getApplicationContext().getBean("wXAccountInfoController");
//		wXAccountInfoController.unSubscribe(openId);
//		responseText();
	}

	@Override
	protected void scan() {
		// TODO Auto-generated method stub
//		responseText("扫描事件" + this.wechatRequest.getEventKey());
		responseText();
	}

	@Override
	protected void location() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void view() {
		responseText();
	}

	@Override
	protected void templateMsgCallback() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void scanCodePush() {
		// TODO Auto-generated method stub
//		responseText("欢迎回来");
		responseText();
	}

	@Override
	protected void scanCodeWaitMsg() {
		// TODO Auto-generated method stub
//		responseText("扫描处理中，请稍等");
		responseText();
	}

	@Override
	protected void picSysPhoto() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void picPhotoOrAlbum() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void picWeixin() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void locationSelect() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void kfCreateSession() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void kfCloseSession() {
		// TODO Auto-generated method stub
		responseText();
	}

	@Override
	protected void kfSwitchSession() {
		// TODO Auto-generated method stub
		responseText();
	}

}
