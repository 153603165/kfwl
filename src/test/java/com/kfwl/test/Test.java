package com.kfwl.test;

import java.text.ParseException;

import org.sword.wechat4j.user.AccountManager;
import org.sword.wechat4j.user.Qrcode;

public class Test {
public static void main(String[] args) throws ParseException {
	AccountManager a=new AccountManager();
	Qrcode qrCode = a.createQrcodePerpetualstr("洪美霞");
	System.out.println(qrCode.getUrl());
}
}
