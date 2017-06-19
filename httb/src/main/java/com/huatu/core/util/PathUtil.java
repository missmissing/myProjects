package com.huatu.core.util;

import java.net.URL;

public class PathUtil {

	public static URL getResource(String resourceName,@SuppressWarnings("rawtypes") Class callingClass){
		URL url = null;
		if (callingClass != null){
			url = callingClass.getResource(resourceName);
			if (url == null){
				ClassLoader cl = callingClass.getClassLoader();
				if (cl != null){
					url = cl.getResource(resourceName);
				}
			}
		}
		if (url == null){
			url = PathUtil.class.getClassLoader().getResource(resourceName);
		}
		if (url == null){
			url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		}

		if ((url == null) && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) {
            return getResource('/' + resourceName, callingClass);
        }

		return url;
	}

}
