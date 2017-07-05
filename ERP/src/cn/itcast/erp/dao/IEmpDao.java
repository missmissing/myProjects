package cn.itcast.erp.dao;

import java.util.List;

import cn.itcast.erp.entity.Emp;
/**
 * 员工数据访问层接口 
 * @author liujunling
 * 
 */
public interface IEmpDao extends IBaseDao<Emp>{

	/**
	 * 登陆验证
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Emp findEmpByLoginNamePassword(String loginName, String password);
	
	
}
