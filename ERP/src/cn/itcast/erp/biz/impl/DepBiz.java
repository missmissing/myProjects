package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.biz.IDepBiz;
import cn.itcast.erp.dao.IDepDao;
import cn.itcast.erp.entity.Dep;
import cn.itcast.erp.exception.ERPException;
/**
 * 部门 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class DepBiz extends BaseBiz<Dep> implements IDepBiz {

	private IDepDao depDao;//数据访问层
	
	public void setDepDao(IDepDao depDao) {
		this.depDao = depDao;
		setBaseDao(depDao);
	}
	
	
	/**
	 * 添加部门(添加后端验证)
	 */
	public void add(Dep dep)
	{
		//部门名称非空验证
		if(dep.getName()==null || dep.getName().equals(""))
		{
			throw new ERPException("部门不能为空");
		}
		//判断部门名称是否存在
		Dep dep0=new Dep();
		dep0.setName(dep.getName());//设置条件
		
		long count = depDao.getCount(dep0, null, null);
		if(count>0)
		{
			throw new ERPException("部门已经存在!");
		}
		
		depDao.add(dep);
	}
	
	/**
	 * 修改部门(添加后端验证)
	 */
	public void update(Dep dep)
	{
		//部门名称非空验证
		if(dep.getName()==null || dep.getName().equals(""))
		{
			throw new ERPException("部门不能为空");
		}
		//判断部门名称是否存在
		Dep dep0=new Dep();
		dep0.setName(dep.getName());//设置条件
		
		Dep dep11=new Dep();   //设置条件，排除自身
		dep11.setUuid(dep.getUuid());
		long count = depDao.getCount(dep0, dep11, null);
		if(count>0)
		{
			throw new ERPException("部门已经存在!");
		}
		
		depDao.update(dep);
	}
	
	/**
	 * 当部门中还有员工存在时，不允许删除该部门
	 */
 public void delete(Long uuid){
	 Dep dep = depDao.get(uuid);
	 if(dep.getEmps().size()>0){
		 throw new ERPException("该部门下有员工，不允许删除！");
	 }
	 depDao.delete(uuid);
 }

}
