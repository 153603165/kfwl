package org.sword.wechat4j.token;

import org.sword.wechat4j.token.server.IServer;

public class CustomizeAccessTokenMemServer implements IServer{


	  private static CustomizeAccessTokenMemServer tokenServer = new CustomizeAccessTokenMemServer();

	  private AccessToken accessToken = new AccessToken();

	  private int requestTimes = 1;

	  private CustomizeAccessTokenMemServer()
	  {
	    refresh();
	  }

	  public static CustomizeAccessTokenMemServer instance()
	  {
	    return tokenServer;
	  }

	  private AccessToken accessToken()
	  {
	    if (!this.accessToken.isValid()) {
	      refresh();
	    }
	    return this.accessToken;
	  }


	  @Override
	public String token() {
		// TODO Auto-generated method stub
		  return accessToken().getToken();
	}

	private void refresh()
	  {
	    for (int i = 0; i < this.requestTimes; i++)
	    {
	      if (this.accessToken.request())
	        break;
	    }
	  }
	protected void forceRefresh(){
		refresh();
	}
}
