/**
 * 
 */
package com.huatu.tb.question.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.question.dao.QuestionDao;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.service.QuestionService;

/** 
 * @ClassName: QuestionServiceImpl 
 * @Description: 试题Service 实现类
 * @author LiXin 
 * @date 2015年4月20日 上午10:42:58 
 * @version 1.0
 *  
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionDao questionDao;
	
	@Override
	public Question get(String id) throws HttbException {
		return questionDao.get(id);
	}
	@Override
	public boolean save(Question question) throws HttbException {
		return questionDao.insert(question);
	}

	@Override
	public boolean saveBatch(List<Question> list) throws HttbException {
		return questionDao.insertBatch(list);
	}

	@Override
	public boolean delete(Question question) throws HttbException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delete(String id) throws HttbException {
		return questionDao.delete(id);
	}

	@Override
	public boolean deleteToIds(List<String> list) throws HttbException {
		return questionDao.deleteBatch(list);
	}

	@Override
	public boolean update(Question question) throws HttbException {
		return questionDao.update(question);
	}

	@Override
	public boolean updateBatch(List<Question> list) throws HttbException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Question> findAll() throws HttbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findList(Map<String, Object> condition) throws HttbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> findPage(Map<String, Object> condition) throws HttbException {
		Page<Question> page =  new Page<Question>();
		List<Question> lists = questionDao.getQuestionResultSet(condition);
		page.setRows(lists);
		return page;
	}
	@Override
	public List<Question> gets(List<String> ids) throws HttbException {
		return questionDao.gets(ids);
	}

}
