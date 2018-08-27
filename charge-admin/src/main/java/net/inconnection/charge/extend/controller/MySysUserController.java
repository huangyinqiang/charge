//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.account.model.SysRole;
import net.inconnection.charge.admin.account.model.SysUser;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargeBatteryInfo;
import net.inconnection.charge.extend.model.Company;
import net.inconnection.charge.extend.model.Tuser;
import net.inconnection.charge.extend.model.TuserCharge;

import java.util.*;

public class MySysUserController extends BaseController {
    public MySysUserController() {
    }

    public void listPage() {
        this.render("list.html");
    }

    public void listData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_base", "sys_user", properties, symbols, values, orderBy, this.getPager());
        for (Record record: list){
            Object roles = record.get("roles");
            StringBuilder stringBuilder = new StringBuilder();
            if (roles!=null){
                String rolesString = (String) roles;
                String[] split = rolesString.split(",");
                for (String roleId:split){
                    Integer id= Integer.valueOf(roleId);
                    SysRole role = SysRole.dao.findById(id);
                    stringBuilder.append(role.getRoleName()).append(",");
                }
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            }
            record.set("roles",stringBuilder.toString());
        }

        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_base", "sys_user", properties, symbols, values));
    }

    public void addPage() {
        this.render("add.html");
    }

    public void getRoleList() {
        List<SysRole> sysRoles = SysRole.dao.find("select * from sys_role");
        List<Map> list = new ArrayList<>();
        for (SysRole sysRole:sysRoles){
            Map<String, Object> map = new HashMap<>();
            map.put("id",sysRole.getId());
            map.put("name",sysRole.getRoleName());
            list.add(map);
        }
        setAttr("sysRoles",list);
        this.renderJson(list);
    }

    public void add() {
        SysUser SysUser = getModel(SysUser.class, "model");
        SysUser.setCreateTime(new Date());
        SysUser.save();
        renderSuccess();
    }



    public void updatePage() {
        SysUser sysUser = SysUser.me.findById(this.getPara("id"));

        StringBuilder stringBuilder = new StringBuilder();
        if (sysUser.getRoles()!=null){
            String rolesString =sysUser.getRoles();
            String[] split = rolesString.split(",");
            for (String roleId:split){
                Integer id= Integer.valueOf(roleId);
                SysRole role = SysRole.dao.findById(id);
                stringBuilder.append(role.getRoleName()).append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        }
        sysUser.setRoles(stringBuilder.toString());

        this.setAttr("model",sysUser );
        this.render("update.html");
    }



    public void update() {
        int id = this.getParaToInt("model.id");
        String user_name = this.getPara("model.user_name");
        String password = this.getPara("model.password");
        String roles = this.getPara("model.roles");
        SysUser sysUser = SysUser.me.findById(id);
        if (!user_name.isEmpty()){
            sysUser.setUserName(user_name);
        }
        if (!password.isEmpty()){
            sysUser.setPassword(password);
        }
        if (!roles.isEmpty()){
            sysUser.setRoles(roles);
        }
        System.out.println(" sysUser "+sysUser);
        sysUser.update();
        this.renderSuccess();
    }

    public void delete() {
        Integer[] ids = this.getParaValuesToInt("id[]");

        for(int i = 0; i < ids.length; ++i) {
            Integer id = ids[i];
            new SysUser().set("id", id).delete();
        }
        this.renderSuccess();
    }



    public void organizationPage() {

        SysUser sysUser = SysUser.me.findById(this.getPara("id"));
        setAttr("userId",sysUser.getId());
        //方便页面展示
        setAttr("userName",sysUser.getUserName());



        //todo 后续权限
        if (sysUser.getUserName().equals("admin")){

        }

        //当前操作用户已经关联的组织
        List<Integer> idList = new ArrayList<>();
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from sysuser_company where sysuser_id="+sysUser.getId());


        for (Record record:list){
           idList.add(record.getInt("company_id"));
        }



        //所有的组织
        List<Record> listCompany = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_company");
        int size = listCompany.size();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Record record : listCompany) {
            size--;
            sb.append("{\"id\":\"").append(record.get("id").toString());
            sb.append("\",\"pId\":\"").append(record.get("pid").toString());
            sb.append("\",\"name\":\"").append(record.get("company_name").toString());
            sb.append("\",\"checked\":\"");
            if (idList.contains(record.getInt("id"))) {
                sb.append("true");
            }else{
                sb.append("false");
            }
            sb.append("\"}");
            if (size > 0) {
                sb.append(",");
            }
        }
        sb.append("]");


        this.setAttr("sb",sb );
        this.render("organization.html");
    }



    public void updateOrganization() {

        Integer userId = this.getParaToInt("model.userId");
        //难判断更新，只能先清空在存储
        int zcurd_busi = Db.use(ZcurdTool.getDbSource("zcurd_busi")).delete(" delete from sysuser_company where sysuser_id=" + userId);

        String para = this.getPara("model.stringIds");

        if (para==null){
            return;
        }

        String[] split = para.split(",");
        for (String str:split){
            if (!str.equals("")){
                boolean save = Db.use(ZcurdTool.getDbSource("zcurd_busi")).save("sysuser_company", new Record().set("sysuser_id", userId).set("company_id", str));
            }
        }
        this.renderSuccess();
    }




}
