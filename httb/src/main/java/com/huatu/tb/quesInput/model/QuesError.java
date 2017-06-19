package com.huatu.tb.quesInput.model;

import java.io.Serializable;
import java.util.Map;

import com.huatu.tb.question.model.Question;

public class QuesError implements Serializable{
	/**
	 *QuesError.java:TODO
	 */

	private static final long serialVersionUID = 1L;

	private Question question;

	private Map<String, Object> resultMap;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
}
