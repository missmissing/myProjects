/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata.gongwuyuan  
 * 文件名：				QuestionTask.java    
 * 日期：				2015年6月3日-上午9:36:52  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.gwy;

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
import com.huatu.tb.common.enums.AttributeEnum;
import com.huatu.tb.common.enums.DifficultyEnum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.service.QuestionService;
import com.text.extractdata.gwy.util.AreaUtil;
import com.text.extractdata.gwy.util.GwyJdbcUtil;
import com.text.extractdata.util.JdbcUtil;
import com.text.extractdata.vo.DanxuantiVo;
import com.text.extractdata.vo.FuhetiVo;
import com.text.extractdata.vo.Guanxian;

/**
 * 类名称： QuestionTask 
 * 类描述： 公务员-试题抓取 
 * 创建人： LiXin 
 * 创建时间： 2015年6月3日 上午9:36:52
 * 
 * @version 1.0
 */
@Service
public class GQuestionExtractTask {
	private static int PAPESIZE = 1000;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private QuestionService questionService;
	/**
	 * 抓取单选题
	 * executeDX  
	 * @exception
	 */
	public void executeDX() {
		//总的知识树
		List<Category> categorys = new ArrayList<Category>();
		try {
			categorys = categoryService.findAll();
		} catch (HttbException e) {
			e.printStackTrace();
		}
		String quessql = null;
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		int count = 0;//试题中个数
		quessql = "select count(*) from v_obj_question where is_multi_part ='-1' ";// SQL语句
		db1 = new GwyJdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				count = ret.getInt(1); 
			}
			ret.close();
			db1.close();// 关闭连接
		}catch(Exception e){
		}
		List<Guanxian> gxList = this.getGuanXian();
		int papecount = count%PAPESIZE==0?count/PAPESIZE : count/PAPESIZE+1;//总页数
		for(int i=0;i<papecount;i++){
			this.danXuanTask(gxList,categorys, i*PAPESIZE);
		}
		
		
	}
	private void danXuanTask(List<Guanxian> gxList,List<Category> categorys,int startpage){
		int index =0;
		List<Question> quesList = new ArrayList<Question>();
		List<CateQues> cateList = new ArrayList<CateQues>();
		Date newDate = new Date();
		String quessql = null;
		JdbcUtil db1 = null;
		ResultSet ret = null;
		quessql = "select PUKEY,stem,type_id,is_multi_part,multi_id,choice_1,"
				+ "choice_2,choice_3,choice_4,choice_5,choice_6,choice_7,choice_8,"
				+ "choice_9,choice_10,stand_answer,answer_comment,answer_explain,answer_skill,"
				+ "answer_expand,comment_author,info_from,is_ture_answer,source,source_year,"
				+ "source_area,point,BB1B1,BB102,difficult_grade from v_obj_question "
				+ "where is_multi_part ='-1' limit "+startpage+","+PAPESIZE;// SQL语句
		
		db1 = new JdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			System.out.println(ret.getRow());
			while (ret.next()) {
				String id = 	ret.getString(1); // 试题ID
				String tigan = 	ret.getString(2); // 题干
				String tixing = 	ret.getString(3); // 题型
				String isfuhe  = 	ret.getString(4); // 是否复合题项    1 复合题   -1 单选题
				String fuheID = 	ret.getString(5); // 复合题ID
				String xuan1 = 	ret.getString(6); // 选项-1
				String xuan2 = 	ret.getString(7); // 选项-2
				String xuan3 = 	ret.getString(8); // 选项-3
				String xuan4 = 	ret.getString(9); // 选项-4
				String xuan5 = 	ret.getString(10); // 选项-5
				String xuan6 = 	ret.getString(11); // 选项-6
				String xuan7 = 	ret.getString(12); // 选项-7
				String xuan8 = 	ret.getString(13); // 选项-8
				String xuan9 = 	ret.getString(14); // 选项-9
				String xuan10 = 	ret.getString(15); // 选项-10
				String daan = 	ret.getString(16); // 参考答案
				String jiexi = 	ret.getString(17); // 参考解析
				String shiyi = 	ret.getString(18); // 参考释义
				String jiqiao = 	ret.getString(19); // 解题技巧
				String kuozhan = 	ret.getString(20); // 解题拓展
				String zuozhe = 	ret.getString(21); // 解析作者
				String laiyuan = 	ret.getString(22); // 信息来源
				String iszhenti = 	ret.getString(23); // 是否为真题-----
				String laiyuan2 = 	ret.getString(24); // 试题来源
				String nianfen = 	ret.getString(25); // 试题年份(来源)
				String diqu = 	ret.getString(26); 		// 试题来源地区
				String fenshu = 	ret.getString(27); // 试题分数
				String shenhe = 	ret.getString(28); // 审核标示    1-已经校对    -1为未校对
				String biaoshi = 	ret.getString(29); // 有效标示 
				Double nandu =       ret.getDouble(30);	//难度
				
				Question ques = new Question();
				ques.setQid(id);
				List<String> zhishi = new ArrayList<String>();
				for(Guanxian gx : gxList){
					if(gx.getValue().equals(id)){
						zhishi.add(gx.getKey());
					}
				}
				ques.setQpoint(zhishi);
				ques.setQcontent(tigan);
				ques.setQtype(QuestionTypesEnum.danx.getCode());//单选题
				String area = AreaUtil.getAreaMap().get(diqu);
				ques.setQarea(area); //地区 
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
				ques.setQyear(nianfen); //年份
				ChildQuestion cq = new ChildQuestion();
				cq.setQccomment(jiexi);
				cq.setQcans(daan);
				cq.setQcextension(kuozhan);
				cq.setQcscore(Float.parseFloat(fenshu));
				List<String> qcchoiceList = new ArrayList<String>();
				String[] choiceArr = {xuan1,xuan2,xuan3,xuan4,xuan5,xuan6,xuan7,xuan8,xuan9,xuan10};
				for(String choice : choiceArr){
					if(choice !="{%NULL%}" && !"{%NULL%}".equals(choice)){
						qcchoiceList.add(choice);
					}
				}
				cq.setQcchoiceList(qcchoiceList);//选项子集
				List<ChildQuestion> child =  new ArrayList<ChildQuestion>();
				child.add(cq);
				ques.setQchild(child);
				
				if(iszhenti.equals("1")){
					ques.setQattribute(AttributeEnum.ZHENTI.getCode());//
				}else{
					ques.setQattribute(AttributeEnum.MONITI.getCode());//
				}
				ques.setQparaphrase(shiyi);
				ques.setQfrom(laiyuan);
				ques.setQskill(jiqiao);
				ques.setQauthor(zuozhe);
				
				ques.setCreateuser("admin");//创建人
				ques.setCreatetime(newDate);
				ques.setUpdateuser("admin");//修改人
				ques.setUpdatetime(newDate);
				if(shenhe.equals("1")){
					ques.setQstatus(StatusEnum.FB.getCode());
					ques.setTombstone("0");
					quesList.add(ques);
				}else{
					ques.setQstatus(StatusEnum.DSH.getCode());
				}
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
			try {
				questionService.saveBatch(quesList);
				//categoryService.saveBatchToCateQues(cateList);
			} catch (HttbException e) {
				System.out.println();
				e.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 抓取复合题
	 * executeFH  
	 * @exception
	 */
	public void executeFH(){
		Date  newDate = new Date();
		List<FuhetiVo> flist = this.getFuhetiList();
		for(FuhetiVo fht : flist){
			Question ques = new Question();
			ques.setQid(fht.getPUKEY());
			ques.setQyear(fht.getSource_year());
			ques.setQarea(AreaUtil.getAreaMap().get(fht.getSource_area()));//地域
			Set<String> points = new HashSet<String>();//知识集合
			String author ="";//作者
			List<ChildQuestion> child = new ArrayList<ChildQuestion>();
			List<DanxuantiVo> dlist = this.getDanxuantiList(fht.getPUKEY());
			for(DanxuantiVo dxt : dlist){
				author = dxt.getZuozhe();//作者
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
				List<String> zsdList = this.getZhishishuList(dxt.getId());
				points.addAll(zsdList);
				child.add(cq);
			}
			
			
			ques.setQchild(child);
			//合并知识点
			List<String> list = new ArrayList<String>();
			for(String key : points){
				list.add(key);
			}
			ques.setQpoint(list);
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
			if(fht.getShenhe().equals("1")){
				ques.setQstatus(StatusEnum.FB.getCode());
			}else{
				ques.setQstatus(StatusEnum.DSH.getCode());
			}
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
			} catch( Exception e) {
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
		String quessql = null;
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		//后面加了 有效状态
		quessql = "select PUKEY,stem,info_from,item_1,item_2,item_3,item_4,item_5,item_6,item_7,item_8,item_9,item_10,is_ture_answer,source,source_year,source_area,point,difficult_grade,BB1B1 from v_multi_question where  bb102 !='-1'";// SQL语句
		
		db1 = new GwyJdbcUtil(quessql);
		try {
			int i=0;
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				i++;
				if(i>=0){
					FuhetiVo fuheti = new FuhetiVo();
					fuheti.setPUKEY(ret.getString(1));//	试题ID
					fuheti.setStem(ret.getString(2));//		题干
					fuheti.setInfo_from(ret.getString(3));//		信息来源
					fuheti.setItem_1(ret.getString(4));//		关联题项-1
					fuheti.setItem_2(ret.getString(5));//		关联题项-2
					fuheti.setItem_3(ret.getString(6));//		关联题项-3
					fuheti.setItem_4(ret.getString(7));//		关联题项-4
					fuheti.setItem_5(ret.getString(8));//		关联题项-5
					fuheti.setItem_6(ret.getString(9));//		关联题项-6
					fuheti.setItem_7(ret.getString(10));//		关联题项-7
					fuheti.setItem_8(ret.getString(11));//		关联题项-8
					fuheti.setItem_9(ret.getString(12));//		关联题项-9
					fuheti.setItem_10(ret.getString(13));//		关联题项-10
					fuheti.setIs_ture_answer(ret.getString(14));//		是否为真题
					fuheti.setSource(ret.getString(15));//		试题来源
					fuheti.setSource_year(ret.getString(16));//		试题年份(来源)
					fuheti.setSource_area(ret.getString(17));//		试题来源地区
					fuheti.setPoint(ret.getDouble(18));//		试题分数
					fuheti.setDifficult_grade(ret.getDouble(19));//		难度系数
					fuheti.setShenhe(ret.getString(20));
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
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		quessql = "select PUKEY,stem,type_id,is_multi_part,multi_id,choice_1,choice_2,choice_3,choice_4,choice_5,choice_6,choice_7,choice_8,choice_9,choice_10,stand_answer,answer_comment,answer_explain,answer_skill,answer_expand,comment_author,info_from,is_ture_answer,source,source_year,source_area,point,BB1B1,BB102,difficult_grade from v_obj_question";// SQL语句
		quessql+=" where multi_id='"+id+"'";
		
		db1 = new GwyJdbcUtil(quessql);
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
	 * 通过试题id获取知识点
	 * getZhishishuList  
	 * @exception 
	 * @param id
	 * @return
	 */
	private List<String> getZhishishuList(String id){
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		
		List<String> zhishi = new ArrayList<String>();
		String zhisql = "select pk_id from v_question_pk_r  where question_id='"+id+"'";
		db1 = new GwyJdbcUtil(zhisql);
		try{
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				zhishi.add(ret.getString(1)); // 试题ID
			}
			ret.close();
			db1.close();// 关闭连接
		}catch(SQLException e){
			e.printStackTrace();
		}
		return zhishi;
	}
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
	private List<Guanxian> getGuanXian(){
		GwyJdbcUtil db2 = null;
		ResultSet ret2 = null;
		
		List<Guanxian> gxList = new ArrayList<Guanxian>();
		try{
			String zhisql = "select pk_id,question_id from v_question_pk_r";
			db2 = new GwyJdbcUtil(zhisql);
			ret2 = db2.pst.executeQuery();// 执行语句，得到结果集
			while (ret2.next()) {
				Guanxian gx = new Guanxian();
				gx.setKey(ret2.getString(1));
				gx.setValue(ret2.getString(2));
				gxList.add(gx);
			}
			ret2.close();
			db2.close();// 关闭连接
		}catch(Exception e){
			e.printStackTrace();
		}
		return gxList;
		/**知识树end*/
	}

}
