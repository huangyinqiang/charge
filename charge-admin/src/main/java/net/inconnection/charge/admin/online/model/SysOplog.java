package net.inconnection.charge.admin.online.model;

import com.jfinal.plugin.activerecord.Model;
import net.inconnection.charge.admin.common.DbMetaTool;

import java.util.Map;

public class SysOplog extends Model<SysOplog> {
	private static final long serialVersionUID = 1L;
	public static final SysOplog me = new SysOplog();
		
	public Map<String, Object> getDictDatauser_id() {
		return DbMetaTool.getDictData("select id, user_name from sys_user ");
	}	
	
}
