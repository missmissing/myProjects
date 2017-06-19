package com.huatu.core.init;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.huatu.api.task.CategoryTask;
import com.huatu.api.task.ExamAnalysisTask;
import com.huatu.api.task.MoniTiPaperTask;
import com.huatu.api.task.QuesToCateTask;
import com.huatu.api.task.QuestionAnalysisTask;
import com.huatu.api.task.ZhenTiPaperTask;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.Constants;
import com.huatu.jms.listener.AnalyzePaperListener;
import com.huatu.jms.listener.AnalyzeQuesListener;
import com.huatu.jms.listener.AnalyzeRankListener;
import com.huatu.jms.listener.SaveRecordListener;

@Component
public class InitCache implements ServletContextAware {
	private static Logger logger = Logger.getLogger(InitCache.class);


	@Autowired
	private CategoryTask categoryTask;
	@Autowired
	private MoniTiPaperTask moniTiPaperTask;
	@Autowired
	private QuesToCateTask quesToCateTask;
	@Autowired
	private ZhenTiPaperTask zhenTiPaperTask;
	@Autowired
	private ExamAnalysisTask examAnalysisTask;
	@Autowired
	private QuestionAnalysisTask questionAnalysisTask;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private SaveRecordListener myAnalyzeListener;
	@Autowired
	private AnalyzeQuesListener analyzeQuesListener;
	@Autowired
	private AnalyzePaperListener analyzePaperListener;
	@Autowired
	private AnalyzeRankListener analyzeRankListener;



	@Override
	public void setServletContext(ServletContext arg0) {
		//初始化参数
		AppTypePropertyUtil.appinit();
		if(Constants.ISINIT){
			logger.info("系统初始化....");
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(10000);
						categoryTask.execute();
					} catch (HttbException e) {
						logger.error("更新章节出现异常");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(60000);
						moniTiPaperTask.execute();
					} catch (HttbException e) {
						logger.error("更新模拟题出现异常");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(120000);
						quesToCateTask.execute();
					} catch (HttbException e) {
						logger.error("更新章节试题打包出现异常");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();


			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(180000);
						zhenTiPaperTask.execute();
					} catch (HttbException e) {
						logger.error("更新真题打包出现异常");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(240000);
						examAnalysisTask.execute();
					} catch (HttbException e) {
						logger.error("分析试卷出现异常");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(300000);
						questionAnalysisTask.execute();
					} catch (HttbException e) {
						logger.error("分析试题出现异常");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

			logger.info("系统初始化完成");
		}

	}
}
