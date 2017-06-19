package com.huatu.sys.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HtSession implements Serializable{
	private static final long serialVersionUID = -8069121814728801068L;
	/**sessionId*/
	private String sessionId;
	/**创建时间*/
	private Long createtime;
	/**最后访问时间*/
	private Long lastaccesstime;
	/**session存储对象map*/
	private Map<String, Object> attribute = new HashMap<String, Object>();

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	public Long getLastaccesstime() {
		return lastaccesstime;
	}
	public void setLastaccesstime(Long lastaccesstime) {
		this.lastaccesstime = lastaccesstime;
	}
	public Object getAttribute(String key) {
		//如果为空，返回空
		if(attribute == null){
			return null;
		}else{
			//返回key对应值
			return attribute.get(key);
		}
	}
	public void setAttribute(String key, Object value) {
		//判断attribute是否为空
		if(this.attribute == null){
			this.attribute = new HashMap<String, Object>();
		}
		this.attribute.put(key, value);
	}
}
