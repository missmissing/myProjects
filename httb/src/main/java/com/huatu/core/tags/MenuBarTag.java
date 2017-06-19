/**   
 * 项目名：				httb
 * 包名：				com.huatu.core.tags  
 * 文件名：				MenuBarTag.java    
 * 日期：				2015年4月30日-下午4:37:34  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.core.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.huatu.ou.menu.model.Menu;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**   
 * 类名称：				MenuBarTag  
 * 类描述：  			菜单导航栏自定义标签
 * 创建人：				LiXin
 * 创建时间：			2015年4月30日 下午4:37:34  
 * @version 		1.0
 */
public class MenuBarTag  extends BodyTagSupport {
	private static final long serialVersionUID = -7988315263578344023L;
	protected final Log log = LogFactory.getLog(getClass());
	private List<Menu> menus;//菜单列表
	@Override
	public int doStartTag() throws JspException {
		@SuppressWarnings("unused")
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		JspWriter out = pageContext.getOut(); 
		
		if( menus!=null && menus.size()>0 ){
			StringBuilder sb = new StringBuilder(100);
			sb.append("<ul class=\"submenu\">");
			for(Menu menu : menus){
				//一级菜单遍历
				if(menu.getLevel()==1){
					sb.append("<li>");
					sb.append("<span>"+menu.getName()+"</span>");
					if(menu.getChildren()!=null){
						sb.append("<ul>");
						for(Menu childmenu : menu.getChildren()){
							sb.append("<li class=\"\"><a href=\"javascript:void(0);\" onclick=\"loadPage('"+childmenu.getUrl()+"')\">"+childmenu.getName()+"</a></li>");
						}
						sb.append("</ul>");
					}
					sb.append("</li>");
				}
			}
			sb.append("</ul>");
			try {
				out.print(sb.toString());
			}catch (IOException e) {
				log.error("生成菜单栏出现 错误!");
				e.printStackTrace();
			} 
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
}
