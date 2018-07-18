package net.inconnection.charge.admin.online.model;

import com.jfinal.plugin.activerecord.Model;
import net.inconnection.charge.admin.common.DbMetaTool;

import java.util.Map;

public class SysMenuBtn extends Model<SysMenuBtn> {
	private static final long serialVersionUID = 1L;
	public static final SysMenuBtn me = new SysMenuBtn();
		
	public Map<String, Object> getDictDatamenu_id() {
		return DbMetaTool.getDictData("select id, menu_name from sys_menu");
	}	
	
}
