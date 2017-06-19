package com.huatu.core.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import com.google.gson.Gson;

/**
 * @ClassName: JsonUtil
 * @Description: JSon处理工具
 * @author LiXin
 * @date 2015年4月21日 上午8:44:57
 * @version 1.0
 *
 */
public class JsonUtil {
	private static final Gson gson = new Gson();

	public static String Object2Json(Object object) {
		String json = null;
		json = gson.toJson(object);
		return json;
	}

	/**
	 *
	 * objectPropertyFilter				(将指定对象的指定属相转成JSONObject)
	 * @param 		obj					需要转化的对象
	 * @param 		param				对象内的参数
	 * @return  	JSONObject   		返回json对象
	 * @exception
	 * @since  		1.0.0
	 */
	public static JSONObject objectPropertyFilter(Object obj,final String ... param ){
		if(param != null && param.length > 0){
			//json过滤配置
			JsonConfig config = new JsonConfig();
			config.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					//遍历传入参数
					for (int i = 0; i < param.length; i++) {
						//如果匹配则返回该属性json
						if(arg1.equals(param[i])){
							return false;
						}
					}
					return true;
				}
			});
			//根据json过滤配置，生成JSONObject
			JSONObject jsonObject = JSONObject.fromObject(obj, config);
			return jsonObject;
		}else{
			return JSONObject.fromObject(obj);
		}

	}

	/**
	 *
	 * arrayPropertyFilter				(将指定对象的指定属相转成JSONArray)
	 * @param 		list				需要转化的集合
	 * @param 		param				集合对象内的参数
	 * @return  	JSONArray   		返回JSONArray对象
	 * @exception
	 * @since  		1.0.0
	 */
	public static JSONArray arrayPropertyFilter( @SuppressWarnings("rawtypes") List list,final String ... param ){
		if(param != null && param.length > 0){
			//json过滤配置
			JsonConfig config = new JsonConfig();
			config.setJsonPropertyFilter(new PropertyFilter() {

				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					//遍历传入参数
					for (int i = 0; i < param.length; i++) {
						//如果匹配则返回该属性json
						if(arg1.equals(param[i])){
							return false;
						}
					}
					return true;
				}
			});
			//根据json过滤配置，生成JSONArray
			JSONArray array = JSONArray.fromObject(list, config);
			return array;
		}else{
			return JSONArray.fromObject(list);
		}
	}

	/**
	 *
	 * arrayPropertyFilter				(将指定对象的指定属相转成JSONArray)
	 * @param 		list				需要转化的集合
	 * @param 		param				集合对象内的参数
	 * @return  	JSONArray   		返回JSONArray对象
	 * @exception
	 * @since  		1.0.0
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> jsonOjbectToMap(JSONObject jsonObject){
		Map<String, String> mapRE = new HashMap<String, String>();
	    Iterator it = jsonObject.keys();
	    // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            String value = (String) jsonObject.get(key);
            mapRE.put(key, value);
        }
        return mapRE;
	}
}
