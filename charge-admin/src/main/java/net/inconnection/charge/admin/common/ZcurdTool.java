package net.inconnection.charge.admin.common;

import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.admin.online.vo.ZcurdMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZcurdTool {
	
	/**
	 * 获得查询参数
	 */
	public static Map<String, String> getQueryPara(Map<String, String[]> paraMap) {
		Map<String, String> queryPara = new HashMap<String, String>();
		for (String paraName : paraMap.keySet()) {
			queryPara.put(paraName, paraMap.get(paraName)[0]);
		}
		return queryPara;
	}
	
	/**
	 * 替换成字典中的值
	 */
	public static Record replaceDict(ZcurdMeta metaData, Record record) {
		Map<String, Map<String, Object>> dictData = metaData.getDictMap();
		for (String key : dictData.keySet()) {
			if(record.get(key) != null) {
				String dictValueStr = "";
				for(String fieldValue : record.get(key).toString().split(",")) {
					Object dictValue = dictData.get(key).get(fieldValue);
					if(dictValue != null) {
						dictValueStr +=  "," + dictValue.toString();
					}else {
						dictValueStr +=  "," + fieldValue;
					}
				}
				record.set("_" + key, record.get(key));	//原始值
				record.set(key, dictValueStr.substring(1));
			}
		}
		return record;
	}
	
	/**
	 * 替换成字典中的值
	 */
	public static void replaceDict(Map<String, Object> dictData, Record record, String fieldName) {
		record.set("_" + fieldName, record.get(fieldName));	//原始值
		if(record.get(fieldName) != null && dictData.get(record.get(fieldName).toString()) != null) {
			record.set(fieldName, dictData.get(record.get(fieldName).toString()));
		}
	}
	
	/**
	 * 替换成字典中的值
	 */
	public static void replaceDict(Map<String, Object> dictData, List<Record> recordList, String fieldName) {
		for (Record record : recordList) {
			replaceDict(dictData, record, fieldName);
		}
	}
	
	/**
	 * 替换成字典中的值
	 */
	public static List<Record> replaceDict(int headId, List<Record> list) {
		return replaceDict(DbMetaTool.getMetaData(headId), list);
	}
	
	/**
	 * 替换成字典中的值
	 */
	public static List<Record> replaceDict(ZcurdMeta metaData, List<Record> list) {
		for (Record record : list) {
			replaceDict(metaData, record);
		}
		return list;
	}
	
	/**
	 * 获得数据源名称
	 */
	public static String getDbSource(String dbSource) {
		if(StringUtil.isEmpty(dbSource)) {
			dbSource = DbKit.getConfig().getName();
		}
		return dbSource;
	}

}
