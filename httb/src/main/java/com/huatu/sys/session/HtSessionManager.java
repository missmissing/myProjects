package com.huatu.sys.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.core.util.ServerContextUtil;
/**
 *
 * 类名称：				HtSessionManager
 * 类描述：				session管理类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月10日 下午1:49:34
 * @version 			0.0.1
 */
@Component
public class HtSessionManager {
	@Autowired
	private IRedisService baseRedisService;
    /**session超时时间*/
    private static int sessionTimeout = Constants.REDIS_SESSIONIDS_TIMEOUT;
    public HtSession getCurrentSession() throws HttbException{
    	return (HtSession)baseRedisService.get("currentSession");
    }
    /**
     *
     * createSession				(创建session)
     * 								(创建session的时候调用)
     * @param 		sessionId		sessionID
     * @return		HtSession		返回htSession对象
     * @throws 		HttbException
     */
    private HtSession createSession(String sessionId) throws HttbException{
    	HtSession session = new HtSession();
    	//设置sessionID
    	session.setSessionId(sessionId);
    	//设置创建时间(毫秒)
    	session.setCreatetime(System.currentTimeMillis());
    	//设置最后一次访问时间
    	session.setLastaccesstime(System.currentTimeMillis());
    	//插入redis中
    	try {
			baseRedisService.put(sessionId, session);
			Object userNum = baseRedisService.getString(Constants.SESSION_CURRENT_USERCOUNT);
			if(null == userNum){
				baseRedisService.putString(Constants.SESSION_CURRENT_USERCOUNT, "0");
			}
			baseRedisService.incr(Constants.SESSION_CURRENT_USERCOUNT);
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, "创建session对象时发生异常", e);
		}
    	return session;
    }

    /**
     *
     * deleteSession				(删除session)
     * 								(session失效或手动删除session时调用)
     * @param 		sessionId		sessionID
     * @return
     * @throws 		HttbException
     */
    public boolean removeSession(String sessionId) throws HttbException{
    	boolean res = false;
    	try{
    		baseRedisService.remove(sessionId);
    		baseRedisService.decr(Constants.SESSION_CURRENT_USERCOUNT);
    		res = true;
    	}catch(Exception e){
			throw new HttbException(ModelException.JAVA_REDIS_DELETE, "删除session对象时发生异常", e);
    	}
    	return res;
    }

    /**
     *
     * getSession					(获取htSession)
     * 								(根据sessionId获取htSession)
     * @param 		sessionId		sessionId
     * @return
     * @throws 		HttbException
     */
    public  HtSession getSession(HttpServletRequest request,HttpServletResponse response) throws HttbException{
    	String sessionid=ServerContextUtil.getSessionId(request);
    	//第一次登陆
    	if(CommonUtil.isNull(sessionid)){
    		return null;
    	}else{
    		return (HtSession)baseRedisService.get(sessionid);
    	}

    	//是否超时
    /*	boolean isTimeOut = validateTimeOut(htSession);
    	//如果没超时，则保存到集合中
    	if(!isTimeOut){
    		//将sessionId添加到sessionId集合中
        	refreshSessionIds(sessionId, "add");
        	htSession.setLastaccesstime(System.currentTimeMillis());
    	}else{
    		htSession = null;
    	}*/

    }

    /**
     *
     * newHtSession					(新建一个htsession)
     * @param 		response
     * @param 		sessionid
     * @return
     * @throws 		HttbException
     */
	public HtSession newHtSession(HttpServletRequest request,HttpServletResponse response) throws HttbException {
		String sessionId = Constants.REDIS_SESSION_ID+CommonUtil.getUUID();
		Cookie cookie = new Cookie(ServerContextUtil.SESSION_ID,sessionId);

		//cookie.setMaxAge(5 * 60);

		//cookie.setMaxAge(sessionTimeout);
		cookie.setPath("/");
		response.setHeader("P3P","CP=\"NON DSP COR CURa ADMa DEVa TAIa PSAa PSDa IVAa IVDa CONa HISa TELa OTPa OUR UNRa IND UNI COM NAV INT DEM CNT PRE LOC\"");

		response.addCookie(cookie);
		HtSession htSession = createSession(sessionId);
		return htSession;
	}

    /**
     *
     * putSession					(保存session)
     * 								(往session中设置新值后，需要重新保存一次session)
     * @param htSession
     * @return
     * @throws HttbException
     */
    public boolean flush(HtSession htSession) throws HttbException{
    	boolean flag = false;
    	//判断htSession是否为空
    	if(htSession != null){
    		try {
    			//保存htsession
				baseRedisService.putEX(htSession.getSessionId(), htSession);
				flag = true;
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_REDIS_ADD,this.getClass()+"保存session时发生异常",e);
			}
    	}
    	return flag;
    }

    /**
     *
     * getCurrentUsersCount			(获取当前系统登录人数)
     * @return						当前系统登录人数
     * @throws 		HttbException
     */
    public int getCurrentUsersCount() throws HttbException{
    	int count = 0;
    	List<Object> objCount;
		try {
			//当前系统登录人数
			//objCount = baseRedisService.get(Constants.SESSION_CURRENT_USERCOUNT);
			objCount = baseRedisService.getKeys(Constants.SESSION_CURRENT_USERCOUNT);
			if(CommonUtil.isNotNull(objCount)){
				String sum = (String) objCount.get(0);
				count = Integer.parseInt(sum);
			}
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, "获取当前系统登录人数时发生异常", e);
		}
    	return count;
    }

    /**
     *
     * validateTimeOut					(验证session是否超时)
     * @param 			session			session对象
     * @return
     */
    @SuppressWarnings("unused")
	private boolean validateTimeOut(HtSession session){
    	//验证结果
    	boolean flag = false;
    	//当前时间
		Long nowTime = System.currentTimeMillis();
		//session最后访问时间
		Long lastTime = session.getLastaccesstime();
		//如果超时，则返回true
		if(lastTime != null){
			//当前时间减去最后一次访问时间
			flag = nowTime-lastTime > sessionTimeout;
		}
    	return flag;
    }
}
