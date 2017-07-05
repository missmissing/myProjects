package cn.itcast.erp.action;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;

public class LoginAction extends BaseAction<Emp> {
	//注入业务逻辑层
	private IEmpBiz empBiz;
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		setBaseBiz(empBiz);
	}
	
	//属性驱动
	private String loginName;  //登陆用户名
	private String password;  //登陆密码
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/*//登陆验证（普通验证）
	public void checkLogin(){
		Emp emp = empBiz.findEmpByLoginNamePassword(loginName,password);
		//判断是否验证成功
		if(emp==null){//验证失败
			write("用户名或密码错误");
			return;
		}else{ //登陆成功
			write("ok");
			ActionContext.getContext().getSession().put("user", emp);
			return;
		}
	}*/
	
	/**
	 * 登陆验证（利用shiro框架实现）
	 */
	public void checkLogin(){
		//把密码按MD5加密算法加密
		String salt = loginName;
		//生成MD5的hash  第一个参数：原始密码，第二个参数：盐，第三个参数：散列次数
		Md5Hash hash = new Md5Hash(password,salt,2);
		System.out.println(hash);
		//1、建立令牌
		UsernamePasswordToken token = new UsernamePasswordToken(loginName, hash.toString());
		//2、得到subject
		Subject subject = SecurityUtils.getSubject();
		//3、执行认证
		try {
			 subject.login(token);
			 //把登陆用户信息放到Session对象中，其中subject.getPrincipal()表示获取主角，也就是emp对象
			 ActionContext.getContext().getSession().put("user", subject.getPrincipal());
			 write("ok");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			write("用户名或密码错误");
		} catch(Exception e){
			e.printStackTrace();
			write("登陆发生异常");
		}
		
	}
	
	//退出登陆
	public String loginOut(){
		ActionContext.getContext().getSession().remove("user");
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		session.removeAttribute("user");
		return "login";
	}
}
