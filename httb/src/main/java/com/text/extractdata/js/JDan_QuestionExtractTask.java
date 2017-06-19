/**
 * 项目名：				httb
 * 包名：				com.text.extractdata.shi
 * 文件名：				Dan_QuestionExtractTask.java
 * 日期：				2015年6月27日-下午9:48:08
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
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;
import com.text.extractdata.gwy.util.AreaUtil;
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
import com.text.extractdata.util.JdbcUtil;
import com.text.extractdata.vo.GuanxiVo;

/**
 * 类名称：				Dan_QuestionExtractTask
 * 类描述：  			教师-单选题抓取
 * 创建人：				LiXin
 * 创建时间：			2015年6月27日 下午9:48:08
 * @version 		1.0
 */
@Service
public class JDan_QuestionExtractTask {

	private static int PAPESIZE = 1000;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private QuestionService questionService;
	/*查询单选题总个数*/
	public static  final String  dxcountSql = "select count(*) from v_obj_question where is_multi_part ='-1'  and bl_teach_sub !='3' ";
	/*分页读取*/
	public static  final String  quesPageSql = "select PUKEY,stem,type_id,is_multi_part,multi_id,choice_1,"
								+ "choice_2,choice_3,choice_4,choice_5,choice_6,choice_7,choice_8,"
								+ "choice_9,choice_10,stand_answer,answer_comment,answer_explain,answer_skill,"
								+ "answer_expand,comment_author,info_from,is_ture_answer,source,source_year,"
								+ "source_area,point,BB1B1,BB102,difficult_grade,examtype  from v_obj_question "
								+ "where is_multi_part ='-1' and  bl_teach_sub !='3' ";// SQL语句
	
	public void executeDX() throws HttbException {
		//总的知识树
		List<Category> categorys =  categoryService.findAll();
		//mysql中所有章节和试题关系
		List<GuanxiVo> guanxiList = this.getAllzhishiAndques();
		JdbcUtil db1 = null;
		ResultSet ret = null;
		int count = 0;//试题中个数
		db1 = new JdbcUtil(dxcountSql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				count = ret.getInt(1); 
			}
			ret.close();
			db1.close();// 关闭连接
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		int papecount = count%PAPESIZE==0?count/PAPESIZE : count/PAPESIZE+1;//总页数
		
		for(int i=0;i<papecount;i++){
			
			this.danXuanTask(guanxiList,categorys, i*PAPESIZE);
		}
		
		
	}
	//遍历单选题保存
	private void danXuanTask(List<GuanxiVo> guanxiList ,List<Category> categorys,int startpage){
		
		List<String> nosqlList = new ArrayList<String>();//nosql中的
		for(Category cate : categorys){
			nosqlList.add(cate.getCid());
		}
		int index =0;
		List<Question> quesList = new ArrayList<Question>();
		List<CateQues> cateList = new ArrayList<CateQues>();
		Date newDate = new Date();
		JdbcUtil db1 = null;
		ResultSet ret = null;
		
		db1 = new JdbcUtil(quesPageSql+"limit "+startpage+","+PAPESIZE+"");
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			System.out.println(startpage+"--"+ret.getRow());
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
				String examtype = ret.getString(31);//考试类型
				
				Question ques = new Question();
				ques.setQid(id);
				//知识点
				List<String> zhishidianList = new ArrayList<String>();
				for(GuanxiVo gv : guanxiList){
					if(id.equals(gv.getQuesId())){
						if(nosqlList.contains(gv.getCateId())){
							zhishidianList.add(gv.getCateId());
						}
					}
				}
				ques.setQpoint(zhishidianList);
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
				
				//考试类型 start
				List<String> qcategory = new ArrayList<String>();
				qcategory.add(examtype);
				ques.setQcategory(qcategory);
				//考试类型 end
				
				ques.setCreateuser("admin");//创建人
				ques.setCreatetime(newDate);
				ques.setUpdateuser("admin");//修改人
				ques.setUpdatetime(newDate);
				ques.setQstatus(StatusEnum.FB.getCode());//默认发布
				List<CateQues> params = new ArrayList<CateQues>();
				for(String zhidian : zhishidianList){
					List<String> allRoots = this.getzhishi(categorys, zhidian);
					System.out.println("当前节点为【"+zhidian+"】所有上级节点集合为 "+allRoots);
					for (int k = 0; k < allRoots.size(); k++) {
						CateQues cateques = new CateQues();
						//知识点ID
						cateques.setCid(allRoots.get(k));
						//试题ID
						cateques.setQid(ques.getQid());
						//属性
						cateques.setAttr(ques.getQcategory().get(0));//地区
						params.add(cateques);
					}
				}
				ques.setTombstone("0");
				quesList.add(ques);
				cateList.addAll(params);
				
				
			}
			ret.close();
			db1.close();// 关闭连接
			try {
				questionService.saveBatch(quesList);
				categoryService.saveBatchToCateQues(cateList);
			} catch (HttbException e) {
				e.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 在nosql中建立试题和章节的关系
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
	
}
