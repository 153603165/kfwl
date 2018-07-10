package com.kfwl.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class FileUtil {
	private static final Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 下载项目根目录下doc下的文件
	 * 
	 * @param response
	 *            response
	 * @param fileName
	 *            文件名
	 * @return 返回结果 成功或者文件不存在
	 */
	public static void downloadFile(HttpServletResponse response, HttpServletRequest request, String fileName) {
		try {
			InputStream fis = FileUtil.class.getResourceAsStream("/doc/" + fileName);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			response.setContentType("bin");

			String fileNames = fileName;
			String agent = request.getHeader("USER-AGENT");

			String codedfilename = "";
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie

				String name = java.net.URLEncoder.encode(fileNames, "UTF8");

				codedfilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

				codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}

			response.addHeader("Content-Disposition", "attachment; filename=\"" + codedfilename + "\"");
			response.getOutputStream().write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug("下载合并系统导入模板文件报错" + e.getMessage(), e);
		}
	}
	
	

	/**
	 * 解决中文乱码
	 * 
	 * @param request
	 * @param realFileName
	 * @return
	 * @throws Exception
	 */
	public static String getStr(HttpServletRequest request, String realFileName) throws Exception {
		String browName = null;
		String clientInfo = request.getHeader("User-agent");
		if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {//
			// IE采用URLEncoder方式处理
			if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6，用GBK，此处实现由局限性
				browName = new String(realFileName.getBytes("GBK"), "ISO-8859-1");
			} else {// ie7+用URLEncoder方式
				browName = java.net.URLEncoder.encode(realFileName, "UTF-8");
			}
		} else {// 其他浏览器
			browName = new String(realFileName.getBytes("GBK"), "ISO-8859-1");
		}
		return browName;
	}
}
