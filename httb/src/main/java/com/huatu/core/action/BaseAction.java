package com.huatu.core.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.huatu.core.exception.HttbException;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;

@Controller
public class BaseAction {
	@Autowired
	protected HtSessionManager htSessionManager;

	public  Logger log = Logger.getLogger(this.getClass());
	/**
	 * encodeURI解码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String decode(String str) throws Exception{
		try {
			return java.net.URLDecoder.decode(str,"UTF-8");
		} catch (Exception e) {
			throw new Exception(this.getClass()+"字符串解码时发生异常", e);
		}
	}

	public HtSession newSession(HttpServletRequest request,HttpServletResponse response)  throws HttbException{
		return htSessionManager.newHtSession(request,response);
	}

	protected boolean flushSession(HtSession htsession)  throws HttbException{
		return htSessionManager.flush(htsession);
	}

	protected boolean invalidSession(HtSession htsession)  throws HttbException{
		return htSessionManager.removeSession(htsession.getSessionId());
	}

}
