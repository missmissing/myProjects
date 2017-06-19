httb 项目结构说明

一、前端:
a.非系统数据公共页面及首页包
/WebContent/common

b.业务数据展示层
/WebContent/pages

/WebContent/pages/common    	#业务数据公共相关
/WebContent/pages/category		#知识点（考点）相关
/WebContent/pages/question		#试题管理相关
/WebContent/pages/question/contentchild		#共用题干类型子集维护相关
/WebContent/pages/question/optionchild		#共用答案类型子集维护相关
/WebContent/pages/image			#图片管理 相关

c.样式及脚本
/WebContent/web-resource

/WebContent/web-resource/scripts   #脚本相关
/WebContent/web-resource/scripts/custom #自定义js
/WebContent/web-resource/styles    #样式相关


二、 后台:
com.huatu.core        	 # 核心
com.huatu.ou         	 # 用户组织
com.huatu.sys            # 系统管理
com.huatu.tb             # 题库业务
com.huatu.upload         # 文件管理
com.huatu.api            # app题库接口
com.huatu.api.task       # 定时任务
三、 配置文件:


四、redis缓存说明：
章节缓存  - key为【REDIS_CATEGORY_KEY】 如公务员区分地域则为【REDIS_CATEGORY_KEY+地区编码（即为一级分类）】 
	            常量：Constants.REDIS_CATEGORY_KEY
	            
章节对应试题缓存  - key为【REDIS_CATEQUES_KEY】 如公务员区分地域则为【REDIS_CATEQUES_KEY+地区编码（即为一级分类）】 
			常量：Constants.REDIS_CATEQUES_KEY
			
真题试卷缓存  - key为【REDIS_CATEGORY_KEY】 如公务员区分地域则为【REDIS_CATEGORY_KEY+地区编码（即为一级分类）】 
		      常量：Constants.REDIS_CATEGORY_KEY
模拟题试卷缓存  - key为【REDIS_PAPERZT_KEY】 如公务员区分地域则为【REDIS_PAPERZT_KEY+地区编码（即为一级分类）】
		       常量：Constants.REDIS_PAPERZT_KEY












