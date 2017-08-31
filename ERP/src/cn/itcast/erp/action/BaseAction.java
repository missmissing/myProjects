package cn.itcast.erp.action;

import cn.itcast.erp.biz.IBaseBiz;
import cn.itcast.erp.exception.ERPException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseAction<T> {
	
	private IBaseBiz baseBiz;//业务逻辑层
	
	public void setBaseBiz(IBaseBiz baseBiz) {
		this.baseBiz = baseBiz;
	}

	private T t1;//查询条件
	public T getT1() {
		return t1;
	}
	public void setT1(T t1) {
		this.t1 = t1;
	}
	
	private T t2;//查询条件	
	public T getT2() {
		return t2;
	}
	public void setT2(T t2) {
		this.t2 = t2;
	}
	
	private String q;  //属性驱动，接受参数q，实现自动补全功能
	public void setQ(String q) {
		this.q = q;
	}
	public String getQ() {
		if(q==null){
			this.q="";
		}
		return q;
	}

	private Object param;  //用来存储activiti中业务id
	
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	/**
	 * 部门列表
	 * @return
	 */
	public void list()
	{
		//获取部门列表
		List<T> list = baseBiz.getList(t1,t2,null);				
		//生成JSON
		String jsonString;
		try {
			//util.MapUtil.convertCollection(List,0)的作用是为了解决双向关联造成的死循环,0表示深度为2层。
			  jsonString = JSON.toJSONString(util.MapUtil.convertCollection(list, 0)
					,SerializerFeature.DisableCircularReferenceDetect);

			  //调用输出
			 write(jsonString);	
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private int page;//页码
	private int rows;//每页记录数
		
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	/**
	 * 部门列表(分页)
	 * @return
	 */
	public void listByPage()
	{
		System.out.println( "页码："+page+"记录数：" +rows);
		//页码换算
		int firstResult=(page-1)*rows;
		//获取部门列表
		List<T> list = baseBiz.getListByPage(t1,t2,param, firstResult, rows);		
		//统计记录个数
		long count = baseBiz.getCount(t1,t2,param);
		//构建分页所需  json结构数据
		Map map=new HashMap();
		map.put("total", count);
		try {
			 map.put("rows", util.MapUtil.convertCollection(list, 0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 			
		//生成JSON
		String jsonString = JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
		write( jsonString);
		
	}
	
	private T t;//部门信息
	
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
	/**
	 * 增加部门
	 */
	public void add()
	{
		try {
			baseBiz.add(t);
			write("ok");
		} catch (ERPException e) {			
			write(e.getMessage());
		}catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改部门
	 */
	public void update()
	{
		try {
			baseBiz.update(t);
			write("ok");
		}catch (ERPException e) {			
			write(e.getMessage());
		} catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
	
	private Long id;//主键
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 删除方法
	 */
	public void delete()
	{
		try {
			baseBiz.delete(id);
			write("ok");
		}catch (ERPException e) {			
			write(e.getMessage());
		}  catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询实体
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public void get()
	{		
		
		try {
			T t3 = (T) baseBiz.get(id);
			//转MAP  在每个属性添加前缀
			Map map= util.MapUtil.convertBean(t3, "t.");
			//String jsonString = JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
			//用JSON的toJSONStringWithDateFormat为了解决查出的数据与前端html文件中回显的日期数据格式不一致的问题。
			String jsonString = JSON.toJSONStringWithDateFormat(map,"yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
			write(jsonString);//输出	
		
		} catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	/**
	 * 输出信息
	 * @param jsonString
	 */
	public void write(String jsonString)
	{		
		//得到响应对象 		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		//输出
		try {
			response.getWriter().print(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	

}
