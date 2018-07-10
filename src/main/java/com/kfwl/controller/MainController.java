package com.kfwl.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kfwl.Wechat;

@Controller
public class MainController {

	@RequestMapping(value="/wx")
	public void react(HttpServletRequest request,HttpServletResponse response){
		
		Wechat weChat = new Wechat(request);
		String result = weChat.execute();
		try {
			response.getOutputStream().write(result.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
