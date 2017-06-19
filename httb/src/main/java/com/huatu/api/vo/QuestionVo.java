/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.restint.vo  
 * 文件名：				QuestionVo.java    
 * 日期：				2015年5月21日-下午5:01:50  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.vo;

import java.io.Serializable;
import java.util.List;

/**   
 * 类名称：				QuestionVo  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年5月21日 下午5:01:50  
 * @version 		1.0
 */
public class QuestionVo implements Serializable {
	private String qid;				//试题ID
	private String code;			//试题标识
	private String content;			//题干
	private String question;		//问题
	private List<String> choices;	//选项集合
	private List<String> qpoint;	//知识点,考点---中文呈现
	private List<String> answers;	//答案集合
	private String year;			//年份
	private String area;			//区域---中文呈现
	private String difficulty;		//难度---中文呈现
	private String attribute;		//属性( 真题 、 模拟题)---中文呈现
	private String comment;			//解析		
	private String extension;		//拓展
	private String paraphrase;		//释义
	private String source;			//信息源
	private String skill;			//答题技巧
	private String author;			//作者
	private String discussion;		//讨论
	private float score;  			//分值
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getChoices() {
		return choices;
	}
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	public List<String> getQpoint() {
		return qpoint;
	}
	public void setQpoint(List<String> qpoint) {
		this.qpoint = qpoint;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getParaphrase() {
		return paraphrase;
	}
	public void setParaphrase(String paraphrase) {
		this.paraphrase = paraphrase;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDiscussion() {
		return discussion;
	}
	public void setDiscussion(String discussion) {
		this.discussion = discussion;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
}
