package com.ytoxl.module.uhome.uhomebase.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtils {
	protected static Logger log = LoggerFactory.getLogger(CookieUtils.class);
	private final static String HISTORY_PATH = "/"; // Cookie的路径
	private final static int TIME = 3600 * 24 * 30; // 30天

	// 添加一个cookie
	public static void addCookie(HttpServletResponse response, String cookieName, String value) {
		if (cookieName == null || value == null) {
			log.error("cookieName或者value为空！");
			return;
		}
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setPath(HISTORY_PATH);
		cookie.setMaxAge(TIME);
		response.addCookie(cookie);
	}
	
	// 添加一个cookie
	public static void addCookie(HttpServletResponse response, String cookieName, String value,int time) {
		if (cookieName == null || value == null) {
			log.error("cookieName或者value为空！");
			return;
		}
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setPath(HISTORY_PATH);
		cookie.setMaxAge(time);
		response.addCookie(cookie);
	}
	
	// 得到cookie
	public static String getCookie(HttpServletRequest request, String cookieName) {
		String cookieValue = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return cookieValue;
	}

	// 删除cookie
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		if (cookieName == null) {
			log.error("cookieName为空！");
			return;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					cookie.setValue("");
					cookie.setMaxAge(0);
					cookie.setPath(HISTORY_PATH);
					response.addCookie(cookie);
				}
			}
		}
	}
}
