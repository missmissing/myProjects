package com.huatu.core.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.SpringContextHolder;
import com.huatu.sys.session.HtSessionManager;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		String sessionId = session.getId();
    	HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		try {
			htSessionManager.removeSession(sessionId);
		} catch (HttbException e) {
			e.printStackTrace();
		}

	}

}
