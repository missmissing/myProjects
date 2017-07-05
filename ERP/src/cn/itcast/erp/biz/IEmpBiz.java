package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
/**
 * 员工业务层接口
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public interface IEmpBiz extends IBaseBiz<Emp>{

	/**
	 * 登陆验证
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Emp findEmpByLoginNamePassword(String loginName, String password);

	/**
	 * 得到用户角色
	 * @param empUuid
	 * @return
	 */
	public List<Tree> getEmpRole(Long empUuid);
	
	/**
	 * 更新用户角色
	 * @param empUuid
	 * @param roleIds
	 */
	public void updateEmpRole(Long empUuid,String roleIds);

}
