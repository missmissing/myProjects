/**   
 * 项目名：				httb
 * 包名：				com.text.extractdata.shi  
 * 文件名：				QuestionExtractTask.java    
 * 日期：				2015年6月26日-下午2:04:17  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.sydw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.common.enums.DifficultyEnum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.service.QuestionService;
import com.text.extractdata.gwy.util.AreaUtil;
import com.text.extractdata.util.JdbcUtil;
import com.text.extractdata.vo.DanxuantiVo;
import com.text.extractdata.vo.FuhetiVo;
import com.text.extractdata.vo.GuanxiVo;

/**   
 * 类名称：				QuestionExtractTask  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年6月26日 下午2:04:17  
 * @version 		1.0
 */
@Service
public class SFu_QuestionExtractTask {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private QuestionService questionService;

	/**
	 * @throws HttbException 
	 * 抓取复合题
	 * executeFH  
	 * @exception
	 */
	public void executeFH() throws HttbException{
		//总的知识树
		List<Category> categorys = categoryService.findAll();
		
		List<String> cateIdList = new ArrayList<String>();
		for(Category cate : categorys){
			cateIdList.add(cate.getCid());
		}
		
		List<GuanxiVo> guanxivoList = this.getAllzhishiAndques();
		Date  newDate = new Date();
		List<FuhetiVo> flist = this.getFuhetiList();
		for(FuhetiVo fht : flist){
			Question ques = new Question();
			List<String> pcategory = new ArrayList<String>();
			ques.setQid("fh"+fht.getPUKEY());
			ques.setQyear(fht.getSource_year());
			ques.setQarea(AreaUtil.getAreaMap().get(fht.getSource_area()));//地域
			Set<String> points = new HashSet<String>();//知识集合
			String author ="";//作者
			List<ChildQuestion> child = new ArrayList<ChildQuestion>();
			List<DanxuantiVo> dlist = this.getDanxuantiList(fht.getPUKEY());
			for(DanxuantiVo dxt : dlist){
				author = dxt.getZuozhe();//作者
				pcategory.add(dxt.getLeixing());//考试类型--资格证 招教 特岗
				ChildQuestion cq = new ChildQuestion();
				cq.setQcid(dxt.getId());
				cq.setQccontent(dxt.getTigan());//子题干
				cq.setQccomment(dxt.getJiexi());//解析
				cq.setQcans(dxt.getDaan());//答案
				cq.setQcextension(dxt.getKuozhan());//拓展
				cq.setQcscore(Float.parseFloat(dxt.getFenshu()));//分数
				List<String> qcchoiceList = new ArrayList<String>();
				String[] choiceArr = {dxt.getXuan1(),dxt.getXuan2(),dxt.getXuan3(),dxt.getXuan4(),dxt.getXuan5(),dxt.getXuan6(),dxt.getXuan7(),dxt.getXuan8(),dxt.getXuan9(),dxt.getXuan10(),};
				for(String choice : choiceArr){
					if(choice !="{%NULL%}" && !"{%NULL%}".equals(choice)){
						qcchoiceList.add(choice);
					}
				}
				cq.setQcchoiceList(qcchoiceList);//选项子集
				
				//知识点**begin
				List<String> zsdList = new ArrayList<String>();
				for(GuanxiVo gv : guanxivoList){
					if(gv.getQuesId().equals(dxt.getId())){
						if(cateIdList.contains(gv.getCateId())){
							zsdList.add(gv.getCateId());
						}
					}
				}
				points.addAll(zsdList);
				child.add(cq);
				//知识点**end
			
			}//循环结束
			
			ques.setQchild(child);
			
			//合并知识点
			List<String> list = new ArrayList<String>(points);
			ques.setQpoint(list);
			
			//关系 begin--------------
			List<CateQues> guanxiList = new ArrayList<CateQues>();
			for(String zhidian : list){
			    List<CateQues> params = new ArrayList<CateQues>();
				List<String> allRoots = this.getzhishi(categorys, zhidian);
				for (int k = 0; k < allRoots.size(); k++) {
					CateQues cateques = new CateQues();
					//知识点ID
					cateques.setCid(allRoots.get(k));
					//试题ID
					cateques.setQid(ques.getQid());
					//属性
					cateques.setAttr(AreaUtil.getAreaMap().get(fht.getSource_area()));//地区
					List<String> ciqlist = new ArrayList<String>();
					for(ChildQuestion c : ques.getQchild()){
						ciqlist.add(c.getQcid());
					}
					cateques.setQcids(ciqlist);
					
					params.add(cateques);
				}
				guanxiList.addAll(params);
			}
			try {
				categoryService.saveBatchToCateQues(guanxiList);
			} catch (HttbException e) {
				e.printStackTrace();
			}
			//关系 end--------------
			ques.setQcontent(fht.getStem());
			ques.setQtype(QuestionTypesEnum.gytg.getCode());//共用题干
			String area = AreaUtil.getAreaMap().get(fht.getSource_area());
			ques.setQarea(area); //地区
			ques.setQattribute(fht.getIs_ture_answer());//
			ques.setQparaphrase("");
			ques.setQfrom(fht.getSource());
			ques.setQskill("");
			ques.setQauthor(author);
			
			ques.setCreateuser("admin");//创建人
			ques.setCreatetime(newDate);
			ques.setUpdateuser("admin");//修改人
			ques.setUpdatetime(newDate);
			//审核状态--------------------------------
			//if(fht.getShenhe().equals("1")){
				ques.setQstatus(StatusEnum.FB.getCode());
			//}else{
			//	ques.setQstatus(StatusEnum.DSH.getCode());
			//}
		    //难易程度----------------------------------------
			Double nandu = fht.getDifficult_grade();
			if(nandu<=-2.0){
				ques.setQdifficulty(DifficultyEnum.JD.getCode()); 	//简单
			}else if(nandu >-2.0 && nandu<=-0.6){
				ques.setQdifficulty(DifficultyEnum.JY.getCode()); 	//较易
			}else if(nandu>-0.6 && nandu <= 0.6){
				ques.setQdifficulty(DifficultyEnum.ZD.getCode()); 	//中等
			}else if(nandu>0.6 && nandu <= 2.0){
				ques.setQdifficulty(DifficultyEnum.JN.getCode()); 	//较难
			}else if(nandu>2.0){
				ques.setQdifficulty(DifficultyEnum.TN.getCode()); 	//困难
			}
			
			
			try {
				ques.setTombstone("0");
				questionService.save(ques);
			} catch (HttbException e) {
				System.out.println();
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取复合题列表
	 * getFuheti  
	 * @exception
	 */
	private List<FuhetiVo> getFuhetiList() {
		List<FuhetiVo> list = new ArrayList<FuhetiVo>();
		//获取复合题id集合
		List<String> fids = this.getFuIdsToobjques();
		String quessql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		//后面加了 有效状态
		quessql = "select PUKEY,stem,info_from,item_1,item_1_type,item_2,item_2_type,item_3,item_3_type,item_4,item_4_type,item_5,item_5_type,item_6,item_6_type,item_7,item_7_type,item_8,item_8_type,item_9,item_9_type,item_10,item_10_type,is_ture_answer,source,source_year,source_area,point,difficult_grade,BB1B1 from v_multi_question where  ";// SQL语句
		quessql+="PUKEY in(";
		for(int i=0;i<fids.size();i++){
			if(i+1 == fids.size() ){
				quessql+=fids.get(i);
			}else{
				quessql+=fids.get(i)+",";
			}
		}
		quessql+=")";
		db1 = new JdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				FuhetiVo fuheti = new FuhetiVo();
				fuheti.setPUKEY(ret.getString(1));//	试题ID
				fuheti.setStem(ret.getString(2));//		题干
				fuheti.setInfo_from(ret.getString(3));//		信息来源
				List<String> childs = new ArrayList<String>();
				for(int item=4;item<24;item+=2){
					//判断子试题ID不为空
					if(CommonUtil.isNotNull(ret.getString(item))&& !"0".equals(ret.getString(item))){
//						o:客观题
//						s:主观题
//						m:复合题
						if("o".equals(ret.getString((item+1)))){
							childs.add(ret.getString(item));
						}
					}
				}
				fuheti.setChilds(childs);//子试题Id集合
				System.out.println("复合题："+ret.getString(1)+"包含子试题ID集合："+childs.toString());
				fuheti.setIs_ture_answer(ret.getString(24));//		是否为真题
				fuheti.setSource(ret.getString(25));//		试题来源
				fuheti.setSource_year(ret.getString(26));//		试题年份(来源)
				fuheti.setSource_area(ret.getString(27));//		试题来源地区
				fuheti.setPoint(ret.getDouble(28));//		试题分数
				fuheti.setDifficult_grade(ret.getDouble(29));//		难度系数
				fuheti.setShenhe(ret.getString(30));
				//复合题不为空
				if(CommonUtil.isNotNull(fuheti.getChilds()) && fuheti.getChilds().size()>0){
					list.add(fuheti);
				}
			}
			ret.close();
			db1.close();// 关闭连接
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取单选题
	 * getFuheti  
	 * @exception
	 */
	private List<DanxuantiVo> getDanxuantiList(String id) {
		List<DanxuantiVo> list = new ArrayList<DanxuantiVo>();
		String quessql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		quessql = "select PUKEY,stem,type_id,is_multi_part,multi_id,choice_1,choice_2,choice_3,choice_4,choice_5,choice_6,choice_7,choice_8,choice_9,choice_10,stand_answer,answer_comment,answer_explain,answer_skill,answer_expand,comment_author,info_from,is_ture_answer,source,source_year,source_area,point,BB1B1,BB102,difficult_grade,examtype from v_obj_question";// SQL语句
		quessql+=" where multi_id='"+id+"'";
		db1 = new JdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				DanxuantiVo dv = new DanxuantiVo();
				dv.setId(ret.getString(1)); // 试题ID
				dv.setTigan(ret.getString(2)); // 题干
				dv.setTixing(ret.getString(3)); // 题型
				dv.setIsfuhe (ret.getString(4)); // 是否复合题项    1 复合题   -1 单选题
				dv.setFuheID(ret.getString(5)); // 复合题ID
				dv.setXuan1(ret.getString(6)); // 选项-1
				dv.setXuan2(ret.getString(7)); // 选项-2
				dv.setXuan3(ret.getString(8)); // 选项-3
				dv.setXuan4(ret.getString(9)); // 选项-4
				dv.setXuan5(ret.getString(10)); // 选项-5
				dv.setXuan6(ret.getString(11)); // 选项-6
				dv.setXuan7(ret.getString(12)); // 选项-7
				dv.setXuan8(ret.getString(13)); // 选项-8
				dv.setXuan9(ret.getString(14)); // 选项-9
				dv.setXuan10(ret.getString(15)); // 选项-10
				dv.setDaan(ret.getString(16)); // 参考答案
				dv.setJiexi(ret.getString(17)); // 参考解析
				dv.setShiyi(ret.getString(18)); // 参考释义
				dv.setJiqiao(ret.getString(19)); // 解题技巧
				dv.setKuozhan(ret.getString(20)); // 解题拓展
				dv.setZuozhe(ret.getString(21)); // 解析作者
				dv.setLaiyuan(ret.getString(22)); // 信息来源
				dv.setIszhenti(ret.getString(23)); // 是否为真题-----
				dv.setLaiyuan2(ret.getString(24)); // 试题来源
				dv.setNianfen(ret.getString(25)); // 试题年份(来源)
				dv.setDiqu(ret.getString(26)); 		// 试题来源地区
				dv.setFenshu(ret.getString(27)); // 试题分数
				dv.setShenhe(ret.getString(28)); // 审核标示    1-已经校对    -1为未校对
				dv.setBiaoshi(ret.getString(29)); // 有效标示 
				dv.setNandu(ret.getDouble(30));	//难度
				dv.setLeixing(ret.getString(31));//考试类型
				list.add(dv);
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取mysql中全部的试题和知识点关系
	 * getAllzhishiAndques  
	 * @exception
	 */
	public List<GuanxiVo> getAllzhishiAndques(){
		List<GuanxiVo> guanVoList = new ArrayList<GuanxiVo>();
		/**知识树begin*/
		JdbcUtil db2 = null;
		ResultSet ret2 = null;
//		o:客观题
//		s:主观题
//		m:复合题
		String guanxiVoSql = "select pk_id,question_id from v_question_pk_r where question_type='o'";

		db2 = new JdbcUtil(guanxiVoSql);
		try{
			ret2 = db2.pst.executeQuery();// 执行语句，得到结果集
			while (ret2.next()) {
				GuanxiVo gv = new GuanxiVo();
				gv.setCateId(ret2.getString(1));
				gv.setQuesId(ret2.getString(2));
				guanVoList.add(gv);
			}
			ret2.close();
			db2.close();// 关闭连接
		}catch(Exception e){
			e.printStackTrace();
		}
		/**知识树end*/
		return guanVoList;
	}
	/**
	 * nosql中关系树
	 * getzhishi  
	 * @exception 
	 * @param categorys
	 * @param cid
	 * @return
	 */
	private List<String> getzhishi(List<Category> categorys ,String cid){
		List<String> pidList = null;
		//判断当前节点是否为空
		if(CommonUtil.isNotEmpty(cid)){
			//知识分类ID和父节点ID集合
			Map<String, String> cpidMap = new HashMap<String, String>();
			//遍历知识分类集合
			for (int i = 0; i < categorys.size(); i++) {
				//将分类节点ID和父节点ID保存到MAP中
				cpidMap.put(categorys.get(i).getCid(), categorys.get(i).getCpid());
			}
			pidList = new ArrayList<String>();
			//添加当前节点ID
			pidList.add(cid);
			//遍历标识
			boolean flag = true;
			//循环添加父节点
			while (flag) {
				//父节点ID
				cid = cpidMap.get(cid);
				//如果父节点不是根节点,则保存到集合中
				if(CommonUtil.isNotNull(cid) && !cid.equals("0")){
					pidList.add(cid);
				}else{
					flag = false;
				}
			}
		}
		return pidList;
	}
	/**
	 * 在单选题表里面获取复合题IDs
	 * getFuIdsToobjques  
	 * @exception 
	 * @return
	 */
	private List<String> getFuIdsToobjques(){
		List<String> fids = new ArrayList<String>();
		String quessql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		quessql = "select distinct multi_id from v_obj_question where is_multi_part ='1' and bl_teach_sub ='3' ";// SQL语句
		db1 = new JdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();
			while (ret.next()) {
				fids.add(ret.getString(1));
			}
			ret.close();
			db1.close();
		}catch(Exception e){
			System.out.println("在单选题表里面获取复合题ID集合失败");
		}
		return fids;
	}
}
