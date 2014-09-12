package com.flopsar.extensions.http;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HTTPFormatter {

	private static ThreadLocal<String> st = new ThreadLocal<String>(){
		protected synchronized String initialValue(){
			return "0";
		}
	};
	
	
	private static ThreadLocal<RequestHolder> rh = new ThreadLocal<RequestHolder>(){
		protected synchronized RequestHolder initialValue(){
			return null;
		}
	};
	
			
	
	
	public static String status(Object[] input){     
		try {
			String s = String.valueOf(input[0]);
			st.set(s);
			return "STATUS|"+s;
		} catch (Throwable e) {
			return "NA";
		}            
	}
	

	
	
	
	public static String reqresp(Object[] input){
            try {
				HttpServletRequest req = (HttpServletRequest)input[0];
				
				RequestHolder r = rh.get();
				
				if(null != r && r == req){
					return r.getParam();
				}


//				try {
//					req.setCharacterEncoding("windows-1250");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}

        
				StringBuffer header = new StringBuffer();
				Enumeration hnames = req.getHeaderNames();
				while(hnames.hasMoreElements()){
				    String name = (String)hnames.nextElement();
				    header.append(name);
				    header.append("=");
				    header.append(req.getHeader(name));
				    header.append("+");
				}
				
				StringBuffer params = new StringBuffer();
				Enumeration pnames = req.getParameterNames();
				while(pnames.hasMoreElements()){
				    String name = (String)pnames.nextElement();
				    params.append(name);
				    params.append("=");
				    params.append(req.getParameter(name));
				    params.append("+");
				}
				
				StringBuffer attrs = new StringBuffer();
				HttpSession session = req.getSession(false);
				
				String sessionID = "null";
				
				if(session == null)
				    attrs.append("null");
				else{
					sessionID = session.getId();
					
				    Enumeration snames = session.getAttributeNames();
				    while(snames.hasMoreElements()){
				        String name = (String)snames.nextElement();
				        attrs.append(name);
				        attrs.append("=");
				        attrs.append(String.valueOf(session.getAttribute(name)));
				        attrs.append("+");
				    }
				}
				
				r = new RequestHolder(req, "URL|"+req.getRequestURL().toString()+"|REQPARAMS|"+params.toString()+"|HEADER|"+header.toString()+"|ATTRS|"+attrs.toString()+"|SID|"+sessionID);
				rh.set(r);
				
				return r.getParam();
			} catch (Throwable e) {
				return "EXCEPTION|"+e.getMessage();
			}
        }
	
	

	
	
}
