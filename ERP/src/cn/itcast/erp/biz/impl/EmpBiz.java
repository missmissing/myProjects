package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 员工 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;//数据访问层
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		setBaseDao(empDao);
	}
	
	private IRoleDao roleDao;  //注入角色数据访问层
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 登陆验证
	 */
	public Emp findEmpByLoginNamePassword(String loginName, String password) {
		return empDao.findEmpByLoginNamePassword(loginName,password);
	}

	/**
	 * 得到用户角色
	 * @param empUuid
	 * @return
	 */
	public List<Tree> getEmpRole(Long empUuid){
		List<Tree> trees = new ArrayList<Tree>();
		//根据员工编号查询该员工所具有的角色
		List<Role> roles = empDao.get(empUuid).getRoles();
		
		//获得全部的角色
		List<Role> roleList = roleDao.getList(null, null, null);
		for(Role role1 :roleList ){
			Tree tree1 = new Tree();  
			tree1.setId(String.valueOf(role1.getUuid()));
			tree1.setText(role1.getName());
			if(roles.contains(role1)){
				tree1.setChecked(true);  
			}
			trees.add(tree1);
		}
		return trees;
	}
	
	/**
	 * 更新用户角色
	 * @param empUuid
	 * @param roleIds
	 */
	public void updateEmpRole(Long empUuid,String roleIds){
		//获得用户实体
		Emp emp = empDao.get(empUuid);
		emp.setRoles(new ArrayList());  //清除原有的所以角色
		
		if(roleIds!=null && !roleIds.equals("")){
			//得到菜单ID
			String[] ids = roleIds.split(",");		
			for(String id:ids){
				Role role = roleDao.get(Long.valueOf(id));
				emp.getRoles().add(role);  //利用快照进行更新
			}
		}
		
	}

	
	/**
	 * 添加员工
	 */
	public void add(Emp emp) {	
		//把密码加密
		//设置盐
		String salt = emp.getUserName();
		//生成MD5的hash  第一个参数：原始密码，第二个参数：盐，第三个参数：散列次数
		Md5Hash hash = new Md5Hash(emp.getPwd(),salt,2);
		//设置新密码
		emp.setPwd(hash.toString());
		empDao.add(emp);
		
	}
	
	/**
	 * 修改员工
	 * @param t
	 */
	public void update(Emp emp) {
		if(emp.getPwd()!=null && !emp.getPwd().equals("")){
			//把密码加密
			//设置盐
			String salt = emp.getUserName();
			//生成MD5的hash  第一个参数：原始密码，第二个参数：盐，第三个参数：散列次数
			Md5Hash hash = new Md5Hash(emp.getPwd(),salt,2);
			//设置新密码
			emp.setPwd(hash.toString());
		}
		empDao.update(emp);		
	}
}
