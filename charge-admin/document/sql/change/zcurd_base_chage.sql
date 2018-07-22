alter table zcurd_field add default_value varchar(100) DEFAULT NULL COMMENT '默认值';
alter table zcurd_head add handle_class varchar(100) DEFAULT NULL COMMENT '处理类';
alter table zcurd_head_btn add `btn_icon` varchar(50) DEFAULT NULL COMMENT '按钮图标';
alter table zcurd_head_js add `sql_content` varchar(2000) DEFAULT NULL COMMENT '扩展sql';
update sys_menu set menu_url='/zcurdHead/listPage'  where menu_name='在线表单';
