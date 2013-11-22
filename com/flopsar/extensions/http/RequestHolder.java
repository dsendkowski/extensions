package com.flopsar.extensions.http;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

	private final HttpServletRequest req;
	private final String param;
	
	public RequestHolder(HttpServletRequest r,String p){
		req = r;
		param = p;
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public String getParam() {
		return param;
	}
}
