package com.huatu.tb.quesInput.model;

import java.util.List;

import com.huatu.tb.question.model.Question;

public class QuesInputMessage {
	/**试题集合*/
	private List<Question> questions;
	/**错题日志名称*/
	private String errorName;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
}
