package com.huatu.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 处理properties配置文件 用法：把properties的配置文件放到properties文件夹下 即可，
 * 然后再用的时候直接获得propertiesMap，文件名即是key 里边的value就是这个配置文件的内容。
 *
 * @author xxj
 *
 */
public class ProcessPropertiesConfigUtil implements ApplicationContextAware {

	public static Map<String, Object> propertiesMap = new HashMap<String, Object>();
	protected static ApplicationContext appContext;
	public static ApplicationContext ac;
	private static ServletContext servletContext = null;

	public static void initProperties(ServletContext in_servletContext) {
		servletContext = in_servletContext;
		try {
			URL urls = PathUtil.getResource("properties", null);
			if (urls == null) {

			} else {
				File file = new File(urls.toURI());
				if (file != null) {
					String[] filelist = file.list();
					if (filelist != null) {
						for (int i = 0; i < filelist.length; i++) {
							String name = filelist[i];
							URL fileUrl = PathUtil.getResource("properties/" + name, null);
							File delfile = new File(fileUrl.toURI());
							InputStream in = new BufferedInputStream(new FileInputStream(delfile));
							Properties p = new Properties();
							p.load(in);
							propertiesMap.put(delfile.getName().substring(0, delfile.getName().lastIndexOf(".")), p);

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {

		this.appContext = appContext;
	}

	/**
	 * 获得当前的servlet路径
	 *
	 * @return
	 */
	public static String getAppPath() {
		return servletContext.getContextPath();
	}
}
