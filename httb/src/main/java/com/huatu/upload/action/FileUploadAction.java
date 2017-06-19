/**
 *
 */
package com.huatu.upload.action;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huatu.core.util.CommonUtil;

/**
 * @ClassName: 						FileuploadAction
 * @Description: 					文件上传Action
 * @author 							Aijunbo
 * @date 							2015年4月22日 上午15:28:14
 * @version 						0.0.1
 *
 */
@Controller
@RequestMapping("/fileUpload")
public class FileUploadAction {
	public  Logger log = Logger.getLogger(this.getClass());

	/**
	 *
	 * deleteOldFile(删除已上传的文件)
	 * @param 		oldFilePath 				原文件路径
	 */
	private void deleteOldFile(String oldFilePath, String temporary) {
		// 如果传入原文件参数不为空则执行删除操作
		if (CommonUtil.isNotEmpty(oldFilePath)) {
			temporary = temporary.substring(0, temporary.lastIndexOf("\\") + 1);
			// 依据传入path实例化文件
			File oldFile = new File(temporary + oldFilePath);
			// 如果文件参数则删除文件
			if (oldFile.exists()) {
				// 删除文件
				oldFile.delete();
			}
		}
	}

}
