package cn.itcast.erp.action;

import cn.itcast.erp.biz.IDepBiz;
import cn.itcast.erp.entity.Dep;
/**
 * 部门 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class DepAction extends BaseAction<Dep> {
	
	private IDepBiz depBiz;

	public void setDepBiz(IDepBiz depBiz) {
		this.depBiz = depBiz;
		setBaseBiz(depBiz);
	}
	
	
	
}
