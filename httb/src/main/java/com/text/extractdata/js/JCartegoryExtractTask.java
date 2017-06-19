/**   
 * 项目名：				httb
 * 包名：				com.text.extractdata.shi  
 * 文件名：				CartegoryExtractTask.java    
 * 日期：				2015年6月26日-下午2:03:32  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.js;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.text.extractdata.util.JdbcUtil;
import com.text.extractdata.vo.ZhangjieVo;

/**   
 * 类名称：				CartegoryExtractTask  
 * 类描述：  			章节提取
 * 创建人：				LiXin
 * 创建时间：			2015年6月26日 下午2:03:32  
 * @version 		1.0
 */
@Service
public class JCartegoryExtractTask {
	private static final String TYPE ="15";
	private static final int CL = 4;
	@Autowired
	private CategoryService categoryService;
	public void execute() {
		List<ZhangjieVo> list =new ArrayList<ZhangjieVo>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("388","教育学");
		map.put("462","教育心理学");
		map.put("537","心理学");
		map.put("569","教育法律法规");
		map.put("572","新课改");
		map.put("575","教师职业道德");
		map.put("578","时事政治");
		map.put("581","公基");
		map.put("1513","教育技术");
		for(String key : map.keySet()){
			List<String> qq = new ArrayList<String>();
			qq.add(key);
			list.addAll(this.getZhangjieList(qq));
		}
		for(String key : map.keySet()){
			ZhangjieVo one = new ZhangjieVo();
			one.setId(key);
			one.setName(map.get(key));
			one.setPid("yj00"+TYPE);
			one.setLevels("3");
			one.setCreateTime(new Date());
			one.setCreateUser("admin");
			one.setUpdateTime(new Date());
			one.setUpdateUser("admin");
			list.add(one);
		}
		for(ZhangjieVo zv : list){
			Category cate = new Category();
			cate.setCid(zv.getId());
			cate.setCname(zv.getName());
			cate.setCpid(zv.getPid());
			cate.setCexplain(zv.getDescrp());
			cate.setCordernum(0);
			cate.setClevels(zv.getLevels());
			cate.setCreatetime(zv.getCreateTime());
			cate.setCreateuser(zv.getCreateUser());
			cate.setUpdatetime(zv.getUpdateTime());
			cate.setUpdateuser(zv.getUpdateUser());
			try {
				categoryService.save(cate);
			} catch (HttbException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取章节列表
	 * getZhangjieList  
	 * @exception 
	 * @return
	 */
    private List<ZhangjieVo> getZhangjieList(List<String> pids){
    	int cj = CL;
    	List<ZhangjieVo> list = new ArrayList<ZhangjieVo>();
    	for(int i=0;i<10;i++){
    		List<String> chids = this.getChilds(pids);
    		if(CommonUtil.isNotNull(chids) && chids.size()>0){
    			pids.clear();
        		pids.addAll(chids);
        		String sql = null;
        		JdbcUtil db1 = null;
        		ResultSet ret = null;
        		//事业单位标识为 3
        		sql = "select PUKEY,name,prev_kp from  v_knowledge_point where bl_sub ='"+TYPE+"'";// SQL语句
        		sql +="and PUKEY in(";
        		for(int j=0;j<chids.size();j++){
    				if(j+1 == chids.size()){
    					sql+=chids.get(j);
    				}else{
    					sql+=chids.get(j)+",";
    				}
        		}
        		sql +=")";
        		db1 = new JdbcUtil(sql);
        		try {
        			ret = db1.pst.executeQuery();// 执行语句，得到结果集
        			while (ret.next()) {
        				ZhangjieVo zvo = new ZhangjieVo();
        				String id = ret.getString(1);
        				String name = ret.getString(2);
        				String pid = ret.getString(3);
        				zvo.setId(id);
        				zvo.setName(name);
        				zvo.setPid(pid);
        				zvo.setCreateTime(new Date());
        				zvo.setCreateUser("admin");
        				zvo.setUpdateTime(new Date());
        				zvo.setUpdateUser("admin");
        				zvo.setLevels((cj+i)+"");
        				System.out.println(name+"--"+(cj+i));
        				list.add(zvo);
        			}// 显示数据
        			ret.close();
        			db1.close();// 关闭连接
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
    		}
    	}
    	
		return list;
    }
    /**
     * 通过父节点ID获取子集Id集合
     * getChilds  
     * @exception 
     * @param pid
     * @return
     */
    private List<String> getChilds(List<String> pids){
    	List<String> childid = new ArrayList<String>();
    	String sql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		//事业单位标识为 3
		sql = "select PUKEY from  v_knowledge_point where bl_sub ='"+TYPE+"'";// SQL语句
		sql +=" and prev_kp in(";
		for(int i=0;i<pids.size();i++){
			if(i+1 == pids.size()){
				sql+=pids.get(i);
			}else{
				sql+=pids.get(i)+",";
			}
		}
		sql +=")";
		db1 = new JdbcUtil(sql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				String id = ret.getString(1);
				childid.add(id);
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return childid;
    }

}
