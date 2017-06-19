/**
 * 项目名：				httb
 * 包名：				com.text.extractdata.shi
 * 文件名：				PaperExtractTask.java
 * 日期：				2015年6月26日-下午2:04:42
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.text.extractdata.js;

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
import com.text.extractdata.gwy.util.AreaUtil;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.paper.service.PaperService;
import com.text.extractdata.util.JdbcUtil;
import com.text.extractdata.vo.ShijuanVo;

/**
 * 类名称：				PaperExtractTask
 * 类描述：  			模拟题
 * 创建人：				LiXin
 * 创建时间：			2015年6月26日 下午2:04:42
 * @version 		1.0
 */
@Service
public class JMo_PaperExtractTask {
	@Autowired
	private PaperService paperService;
	
	@Autowired
	private TaskVersionService taskVersionService;
	
	/**
	 * 抓取模拟
	 * executeDX  
	 * @exception
	 */
	public void executeMo() {
		List<ShijuanVo> list = this.getShujuanZhenList();
		for(ShijuanVo sjv : list){
			Paper paper = new Paper();
			paper.setPid(sjv.getPUKEY());
			paper.setPyear(sjv.getPast_year());
			paper.setPtitle(sjv.getPastpaper_name());
			paper.setPsubtitle(sjv.getPastpaper_mark());
			String area = AreaUtil.getAreaMap().get(sjv.getPast_area());
			if(CommonUtil.isNotNull(area)){
				List<String> areas = new ArrayList<String>();
				areas.add(area);
				paper.setPareas(areas);
			}
			List<String> quesList = this.getQuesZhenListByPid(sjv.getPUKEY());
			paper.setPattribute("1");//模拟题
			paper.setPstatus(StatusEnum.FB.getCode());//查询的为审批完成的试卷
			paper.setCreatetime(sjv.getCreatetime());
			paper.setCreateuser("admin");
			paper.setUpdateuser("admin");
			paper.setUpdatetime(sjv.getUpdatetime());
			paper.setPqids(quesList);
			List<String> pcategorys = new ArrayList<String>();
			pcategorys.add("yj00"+sjv.getCategory());//一级分类
			paper.setPcategorys(pcategorys);
			
			//发版本
			Date nowTime=new Date(); 
			System.out.println(nowTime); 
			SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
			TaskVersion tv = new TaskVersion();
			tv.setTkey(paper.getPid());
			tv.setTvalue(time.format(nowTime));
			tv.setTpapertype("1");//模拟题
			tv.setCreatetime(nowTime);
			try {
				taskVersionService.addVersion(tv);
			} catch (HttbException e) {
				e.printStackTrace();
			}
			
			try {
				if(CommonUtil.isNotNull(paper.getPqids())&& paper.getPqids().size()>0){
					paperService.save(paper);
				}
			} catch (HttbException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 获取试卷(模拟题)----
	 * &……………………………………………………………………获取出审批通过的全部模拟题***************
	 * getShujuanList  
	 * @exception 
	 * @return
	 */
	private List<ShijuanVo> getShujuanZhenList(){
		List<ShijuanVo> list = new ArrayList<ShijuanVo>();
		String quessql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		//bl_exam_sub !='3' 为教师        and  simulate='1' 为模拟题
		quessql = "select PUKEY,pastpaper_name,past_area,past_year,bl_exam_sub from v_pastpaper_info where bl_exam_sub !='3' and  simulate='1' ";// SQL语句
		db1 = new JdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				ShijuanVo  sj = new ShijuanVo();
				sj.setType("1");//模拟题
				sj.setPUKEY(ret.getString(1));//PUKEY	记录ID
				sj.setPastpaper_name(ret.getString(2));//pastpaper_name	真题卷名称
				sj.setPast_area("");
				sj.setPast_year(ret.getString(4));
				sj.setCategory(ret.getString(5));//一级分类
				sj.setCreatetime(new Date());
				sj.setUpdatetime(new Date());
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
	private List<String> getQuesZhenListByPid(String id){
		Set<String> set = new HashSet<String>();
		String quessql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		quessql = "select question_id,question_type from v_pastpaper_question_r where pastpaper_id='"+id+"' and question_type='m'";// SQL语句
		db1 = new JdbcUtil(quessql);
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
					set.add("fh"+ret.getString(1));//单选题
				}
			}
			ret.close();
			db1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<String>(set);
		list.addAll(getQuesZhenListByPidTokeguan(id));
		return list;
		
	}
	/**
	 * 通过试卷ID获取试题集合
	 * getQuesListByPid  
	 * @exception 
	 * @param id
	 * @return
	 */
	private List<String> getQuesZhenListByPidTokeguan(String id){
		Set<String> set = new HashSet<String>();
		String quessql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		quessql = "select t1.PUKEY,t1.bl_teach_sub from v_obj_question t1, v_pastpaper_question_r t2 where t1.PUKEY=t2.question_id and  t2.pastpaper_id='"+id+"' and t2.question_type='o'";// SQL语句
		db1 = new JdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				//属于教师排除事业单位的试题
				if(!ret.getString(2).equals("3")){
					set.add(ret.getString(1));//
				}
			}
			ret.close();
			db1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<String>(set);
		return list;
		
	}
}
