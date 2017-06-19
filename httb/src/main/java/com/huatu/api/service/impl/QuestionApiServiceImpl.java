package com.huatu.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.api.dao.QuestionDao;
import com.huatu.api.service.QuestionApiService;
import com.huatu.api.task.service.RCategoryService;
import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.util.ComparatorCategoryVo;
import com.huatu.api.util.ModelToVoUtil;
import com.huatu.api.vo.CategoryVo;
import com.huatu.api.vo.ErrorQuestionVo;
import com.huatu.api.vo.QuestionVo;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.dao.impl.RCategoryDaoImpl;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.question.dao.impl.RQuestionDaoImpl;
import com.huatu.tb.question.model.Question;
@Service
public class QuestionApiServiceImpl implements  QuestionApiService{
	
	public  Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	QuestionDao questionDao;
	
	@Autowired
	private IRedisService iRedisService;
	
	@Autowired
	private RCategoryDaoImpl categoryRestDao;

	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	@Autowired
	private RQuestionDaoImpl ruestionDaoImpl;
	@Autowired
	private RCategoryService rCategoryService;
	

	/******************************收藏相关*****************************/
	@Override
	public boolean collectQuestion(String userid, List<String> questionids)
			throws HttbException {
		return questionDao.collectQuestion(userid, questionids);
	}

	@Override
	public List<String> getCollectQuestionListByUserId(String userid)
			throws HttbException {
		return questionDao.getCollectQuestionListByUserId(userid);
	}

	@Override
	public boolean deleteCollectQuestion(String userid, List<String> qids)
			throws HttbException {		
		return questionDao.deleteCollectQuestion(userid, qids);
	}
	/******************************end**************************************/
	
	/******************************错题相关*****************************/
	@Override
	public List<String> getWrongQuestionListByUserId(String userid)
			throws HttbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addWrongQuestion(Map<String, Object> filter)
			throws HttbException {
		List<String> qhqansList  = (List<String>)filter.get("qhqans");
		String[] arr = (String[])qhqansList.toArray(new String[qhqansList.size()]);
		String b = Arrays.toString(arr);
		b = b.replace(",", "#");
		filter.put("qhqans",b);
		List<String> qhuansList  = (List<String>)filter.get("qhuans");
		String[] arr2 = (String[])qhuansList.toArray(new String[qhuansList.size()]);
		String b2 = Arrays.toString(arr2);
		b2 = b2.replace(",", "#");
		filter.put("qhuans",b2);
		return questionDao.addWrongQuestion(filter);
	}

	@Override
	public boolean deleteWrongQuestion(String userid,List<String> qids)
			throws HttbException {
		return questionDao.deleteWrongQuestion(userid, qids);
	}
	/******************************end**************************************/

	@Override
	public CategoryVo getWrongQuestionCategory(String id, String userno,
			String area) throws HttbException {
			//首先在缓存中获取
			List<CategoryVo> lists = new ArrayList<CategoryVo>();
			lists = (List<CategoryVo>) iRedisService.get(TaskMarkKeyUtil.CATEGORY_KEY);
			//3 关系树
			List<CateQues> cqList = (List<CateQues>) iRedisService.get(TaskMarkKeyUtil.CATE_QUES_KEY);//全部章节试题列表
			for(CategoryVo cv : lists){	
				List<String> qids = rCategoryService.getQidList(cv.getCid(), "", cqList);
				cv.setQids(qids);
				cv.setCount(qids.size()+"");
			}
			if(CommonUtil.isNull(lists)){
				//如果缓存失败在数据库中获取
				lists = getCategoryVoList(null);
			}
			//获得用户的所有错题id
			List<String> qlist = questionDao.getWrongQuestionListByUserId(userno);
			
		    //用户错题和章节中的试题id去交集
			for(CategoryVo vo : lists){
				List<String> volist = vo.getQids();
				volist.retainAll(qlist);
				vo.setCount(volist.size()+"");
			}
			
			//续4,重置组装集合【形成父子包含的格式  便于转JSON】
			return settleDataFormat(lists,id);
	}
	
	private CategoryVo settleDataFormat(List<CategoryVo> list , String id){
		Boolean boo = true;
		for(CategoryVo cq : list){
			//System.out.println(cq.getId());
			if(cq.getCid().equals(id)){
				boo = false;
				return setObject(list,cq);
			}
		}
		if(boo){
			CategoryVo cv = new CategoryVo();
			cv.setCid("0");
			return setObjectAll(list,cv);
		}
		return null;
	}
	/**
	 * 递归生成父子集数据
	 * setObject
	 * @exception
	 * @param list
	 * @param cq
	 * @return
	 */
	private CategoryVo setObject(List<CategoryVo> list , CategoryVo cq){
		List<CategoryVo> childCV = new ArrayList<CategoryVo>();
		for(CategoryVo cvo : list){
			if(cq.getCid().equals(cvo.getPid())){
				childCV.add(setObject(list,cvo));
			}
		}
		cq.setChildren(childCV);
		return cq;
	}
	/**
	 * 递归生成父子集数据
	 * setObject
	 * @exception
	 * @param list
	 * @param cq
	 * @return
	 */
	private CategoryVo setObjectAll(List<CategoryVo> list , CategoryVo cq){
		List<CategoryVo> childCV = new ArrayList<CategoryVo>();
		for(CategoryVo cvo : list){
			if(cq.getCid().equals(cvo.getPid())){
				childCV.add(setObject(list,cvo));
			}
		}
		cq.setChildren(childCV);
		return cq;
	}
	
	
	private List<CategoryVo> getCategoryVoList(String attr) throws HttbException {
		List<CategoryVo> lists = new ArrayList<CategoryVo>();
		// 1,遍历全部张章节 获取基本消息
		List<Category> cgList = categoryRestDao.getCategoryList();
		// 1,遍历全部张章节 获取基本消息
		for (Category categ : cgList) {
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setCid(categ.getCid());
			
			categoryVo.setPid(categ.getCpid());
			
			categoryVo.setName(categ.getCname());//名称
			categoryVo.setExplain(categ.getCexplain()); //说明********要不要加********
			categoryVo.setOrdernum(categ.getCordernum()); //排序字段
			lists.add(categoryVo);
		}
		//2,根据排序字段排序
		ComparatorCategoryVo comparator = new ComparatorCategoryVo();
		Collections.sort(lists, comparator);//根据排序字段排序
		
		//3,遍历知识点章节集合 获取试题数量和试题ID集合
		for(CategoryVo cateV : lists){
			Set<String> set = categoryRestDao.getQidsBycid(cateV.getCid(), null);
			cateV.setCount(set.size()+"");//知识点下的试题数量
			cateV.setQids(new ArrayList<String>(set));//试题ID集合
		}
		return lists;
	}

	@Override
	public List<ErrorQuestionVo> getErrorQuestions(List<String> qids, String userno) throws HttbException {
		List<QuestionVo> questionvoList = new ArrayList<QuestionVo>();
		List<ErrorQuestionVo> eqvList = new ArrayList<ErrorQuestionVo>();
		//1 nosql中的实际id
		List<String> prentids = new ArrayList<String>();
		//redis中获取关系
		List<CateQues> cqList = (List<CateQues>) iRedisService.get(TaskMarkKeyUtil.CATE_QUES_KEY);
		for(String qid:qids){
			for(CateQues cq : cqList){
				if(cq.getQid().equals(qid)){
					prentids.add(qid);
					break;
				}else if(CommonUtil.isNotNull(cq.getQcids()) && cq.getQcids().contains(qid)){
					prentids.add(cq.getQid());
					break;
				}
			}
		}
		List<Question> questionList = ruestionDaoImpl.getQuestionResultSet(prentids);
		for(Question question:questionList){
			questionvoList.addAll(ModelToVoUtil.questtionToVo(question));
		}
		Map<String,Object> map =  questionDao.getWrongQuestionMapByUserId(userno);
		for(QuestionVo qv : questionvoList){
			
			if(qids.contains(qv.getQid())){
				ErrorQuestionVo eqv = new ErrorQuestionVo();
				//加错误项
				try {
					PropertyUtils.copyProperties(eqv,qv);
				} catch (Exception e) {
					log.error("对象转换异常!",e);
				}
				eqv.setErroroption((String)map.get(qv.getQid()));
				eqvList.add(eqv);
			}
		}
		return eqvList;
	}

}
