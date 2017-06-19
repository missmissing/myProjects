package com.huatu.sys.filter;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.ProcessPropertiesConfigUtil;
import com.huatu.core.util.ServerContextUtil;
import com.huatu.core.util.SpringContextHolder;
import com.huatu.ou.user.model.User;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;

public class SessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 不过滤的uri列表
    	Properties p = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("httbContext");
        String nonFileterURIs = p.getProperty("NONFileterURI");
        String[] notFilter  = nonFileterURIs.split(",");
        String uri = request.getRequestURI();
        boolean doFilter = true;
        for (String s : notFilter) {
            if (uri.indexOf(s) != -1) {
                // 如果uri中包含不过滤的uri，则不进行过滤
                doFilter = false;
                break;
            }
        }
        if((!uri.endsWith("index.jsp"))&&uri.endsWith(".jsp")){
        	doFilter = true;
        }
        if (doFilter) {
        	String sessionId =  ServerContextUtil.getSessionId(request);
        	IRedisService iRedisService  = SpringContextHolder.getBean("IRedisServiceImpl");
        	try {
        		if(null!=sessionId)
				iRedisService.refreshEX(sessionId);
			} catch (HttbException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            //执行过滤
        	HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
        	HtSession htsession = null;
			try {
				htsession = htSessionManager.getSession(request,response);
			} catch (HttbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (htsession==null) {
            	 //从session中获取登录者实体
                //Object obj = htsession.getAttribute("correntUser");
                // 如果session中不存在登录者，则弹出框提示重新登录
                // 设置request和response的字符集，防止乱码
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                request.getRequestDispatcher("/common/noSession.jsp").forward(request,response);
            } else {
            	if(uri.endsWith("httb/")||uri.endsWith("httb"))
            		request.getRequestDispatcher("/pages/common/main.jsp").forward(request,response);
            	else
            		// 如果session中存在登录者实体，则继续
            		filterChain.doFilter(request, response);
            }
        } else {
            // 如果不执行过滤，则继续
            filterChain.doFilter(request, response);
        }
    }
}
