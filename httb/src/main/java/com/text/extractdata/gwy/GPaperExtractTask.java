/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata  
 * 文件名：				PaperTask.java    
 * 日期：				2015年6月5日-上午11:15:18  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.gwy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.paper.service.PaperService;
import com.text.extractdata.gwy.util.AreaUtil;
import com.text.extractdata.gwy.util.GwyJdbcUtil;
import com.text.extractdata.vo.ShijuanVo;

/**   
 * 类名称：				PaperTask  
 * 类描述：  			公务员 -- 试卷抓取
 * 创建人：				LiXin
 * 创建时间：			2015年6月5日 上午11:15:18  
 * @version 		1.0
 */
@Service
public class GPaperExtractTask {
	@Autowired
	private PaperService paperService;
	@Autowired
	private TaskVersionService taskVersionService;

	/**
	 * 抓取真题试卷
	 * executeDX  
	 * @exception
	 */
	public void executeZhen() {
		Date newDate = new Date();
		List<ShijuanVo> list = this.getShujuanList();
		for(ShijuanVo sjv : list){
			Paper paper = new Paper();
			paper.setPid(sjv.getPUKEY());
			paper.setPtitle(sjv.getPastpaper_name());
			paper.setPsubtitle(sjv.getPastpaper_mark());
			String area = AreaUtil.getAreaMap().get(sjv.getPast_area());
			if(CommonUtil.isNotNull(area)){
				List<String> areas = new ArrayList<String>();
				areas.add(area);
				paper.setPareas(areas);
			}
			paper.setPyear(sjv.getPast_year());
			List<String> quesList = this.getQuesListByPid(sjv.getPUKEY());
			paper.setPqids(quesList);
			paper.setPattribute("0");
			if("1".equals(sjv.getShenhe()) ){
				paper.setPstatus(StatusEnum.FB.getCode());
				
				//发版本
				Date nowTime=new Date(); 
				System.out.println(nowTime); 
				SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
				TaskVersion tv = new TaskVersion();
				tv.setTkey(paper.getPid());
				tv.setTvalue(time.format(nowTime));
				tv.setTpapertype("0");
				tv.setCreatetime(nowTime);
				try {
					taskVersionService.addVersion(tv);
				} catch (HttbException e) {
					e.printStackTrace();
				}
				
			}else{
				paper.setPstatus(StatusEnum.DSH.getCode());
			}
			paper.setCreatetime(newDate);
			paper.setCreateuser("admin");
			paper.setUpdateuser("admin");
			paper.setCreatetime(newDate);
			
			try {
				paperService.save(paper);
			} catch (HttbException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 抓取模拟试卷
	 * executeDX  
	 * @exception
	 */
	public void executeMo() {
		Date newDate = new Date();
		List<ShijuanVo> list = this.getShujuanMoList();
		for(ShijuanVo sjv : list){
			Paper paper = new Paper();
			paper.setPid("m"+sjv.getPUKEY());
			paper.setPtitle(sjv.getPastpaper_name());
			paper.setPsubtitle(sjv.getPastpaper_mark());
			String area = AreaUtil.getAreaMap().get(sjv.getPast_area());
			if(CommonUtil.isNotNull(area)){
				List<String> areas = new ArrayList<String>();
				areas.add(area);
				paper.setPareas(areas);
			}
			List<String> quesList = this.getQuesMoListByPid(sjv.getPUKEY());
			paper.setPattribute("1");//模拟题
			paper.setPstatus(StatusEnum.FB.getCode());//查询的为审批完成的试卷
			paper.setCreatetime(newDate);
			paper.setCreateuser("admin");
			paper.setUpdateuser("admin");
			paper.setCreatetime(newDate);
			paper.setPqids(quesList);
			
			//发版本
			Date nowTime=new Date(); 
			System.out.println(nowTime); 
			SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
			TaskVersion tv = new TaskVersion();
			tv.setTkey(paper.getPid());
			tv.setTvalue(time.format(nowTime));
			tv.setTpapertype("1");
			tv.setCreatetime(nowTime);
			try {
				taskVersionService.addVersion(tv);
			} catch (HttbException e) {
				e.printStackTrace();
			}
			
			try {
				paperService.save(paper);
			} catch (HttbException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 获取试卷(真题)
	 * getShujuanList  
	 * @exception 
	 * @return
	 */
	private List<ShijuanVo> getShujuanList(){
		List<ShijuanVo> list = new ArrayList<ShijuanVo>();
		String quessql = null;
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		//有效状态为1   类型为1即行测
		quessql = "select PUKEY,pastpaper_name,past_year,past_area,pastpaper_mark,BB1B1,BB102 from v_pastpaper_info where bl_exam_sub='1' and BB102='1'";// SQL语句
		db1 = new GwyJdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				ShijuanVo  sj = new ShijuanVo();
				sj.setType("0");
				sj.setPUKEY(ret.getString(1));//PUKEY	记录ID
				sj.setPastpaper_name(ret.getString(2));//pastpaper_name	真题卷名称
				sj.setPast_year(ret.getString(3));
				sj.setPast_area(ret.getString(4));
				sj.setPastpaper_mark(ret.getString(5));
				sj.setShenhe(ret.getString(6));
				sj.setYouxia(ret.getString(7));
				list.add(sj);
				//System.out.println(sj.getPastpaper_name()+"|"+sj.getShenhe()+"|"+sj.getYouxia());
				
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 通过试卷ID获取试题集合
	 * getQuesListByPid  
	 * @exception 
	 * @param id
	 * @return
	 */
	private List<String> getQuesListByPid(String id){
		Set<String> set = new HashSet<String>();
		String quessql = null;
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		//有效状态为1   类型为1即行测
		quessql = "select question_id,question_type  from v_pastpaper_question_r where pastpaper_id='"+id+"'";// SQL语句
		db1 = new GwyJdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
//				o:客观题
//				s:主观题
//				m:复合题
				String type = ret.getString(2);
				
				if(type.equals("o")){
					set.add(ret.getString(1));//单选
				}else if(type.equals("m")) {
					set.add(ret.getString(1));//单选题
				}
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<String>(set);
		return list;
		
	}
	
	/**
	 * 获取试卷(模拟题)----
	 * &……………………………………………………………………获取出审批通过的全部模拟题***************
	 * getShujuanList  
	 * @exception 
	 * @return
	 */
	private List<ShijuanVo> getShujuanMoList(){
		List<ShijuanVo> list = new ArrayList<ShijuanVo>();
		String quessql = null;
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		//有效状态为1   类型为1即行测
		quessql = "select t.PUKEY,t.name,m.test_area from v_modetest_paper_info  m  left join  v_testpaper_info  t  on t.PUKEY  = m.testpaper_id where m.BB102 ='1'and t.BB102 ='1' and t.BB1B1='1'";// SQL语句
		db1 = new GwyJdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				ShijuanVo  sj = new ShijuanVo();
				sj.setType("1");
				sj.setPUKEY(ret.getString(1));//PUKEY	记录ID
				sj.setPastpaper_name(ret.getString(2));//pastpaper_name	真题卷名称
				sj.setPast_area(ret.getString(3));
				
				list.add(sj);
				
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 通过试卷ID获取试题集合
	 * getQuesListByPid  
	 * @exception 
	 * @param id
	 * @return
	 */
	private List<String> getQuesMoListByPid(String id){
		List<String> list = new ArrayList<String>();
		String quessql = null;
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		//有效状态为1   类型为1即行测
		quessql = "select question_id from v_testpaper_question_r where testpaper_id='"+id+"'";// SQL语句
		db1 = new GwyJdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				list.add(ret.getString(1));//PUKEY	记录ID
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	

}
