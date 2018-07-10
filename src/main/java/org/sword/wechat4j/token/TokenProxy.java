/**
 * 
 */
package org.sword.wechat4j.token;

import org.sword.wechat4j.token.server.JsApiTicketServer;
import org.sword.wechat4j.token.server.TicketServer;


/**
 * AccessToken代理
 * 所有获取accessToken的地方都通过此代理获得
 * 获得方法String token = AccessTokenProxy.token()
 * @author ChengNing
 * @date   2015年1月9日
 */
public class TokenProxy {
	
	/**
	 * 通过代理得到accessToken的串
	 */
	public static String accessToken(){
//		TokenServer accessTokenServer = new AccessTokenServer();
//		return accessTokenServer.token();
		CustomizeAccessTokenMemServer accessTokenServer = CustomizeAccessTokenMemServer.instance();
		return accessTokenServer.token();
	}
	/**
	 * 通过代理得到accessToken的串
	 * @param isForceRefresh true强制刷新
	 * @return
	 * 
	 * @date 2016年3月18日 下午4:15:14
	 */
	public static String accessToken(boolean isForceRefresh){
		CustomizeAccessTokenMemServer accessTokenServer = CustomizeAccessTokenMemServer.instance();
		if(isForceRefresh){
			accessTokenServer.forceRefresh();
			return accessTokenServer.token();
		}else{
			return accessTokenServer.token();
		}
	}
	
	/**
	 * 通过代理得到jsapi_ticket
	 */
	public static String jsApiTicket(){
		TicketServer ticketServer = new JsApiTicketServer();
		return ticketServer.ticket();
	}
	

	
	
}
