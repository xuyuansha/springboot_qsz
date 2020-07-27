package com.qsz.bmss.common;

/**
 * 操作日志信息内容定义
 * 	变量说明：S_开头为操作概要信息; D_开头为详细操作信息
 * @author Administrator
 *
 */
public class LogDescription {
	
	/**系统登录模块日志信息定义**/
	public static final String S_LOGIN = "登录系统";
	public static final String D_LOGIN = "从%s登录系统";
	
	/**用户管理模块日志信息定义**/
	public static final String S_ADD_USER = "添加用户";
	public static final String D_ADD_USER = "添加新用户%s";
	public static final String S_MODIFY_USER = "修改用户";
	public static final String S_MODIFY_USER_STATUS = "修改用户状态";
	public static final String D_MODIFY_USER_STATUS = "修改用户“%s”的状态";
	public static final String D_MODIFY_USER = "修改用户“%s”的信息";
	public static final String S_DELETE_USER = "删除用户";
	public static final String D_DELETE_USER = "删除用户“%s”";
	public static final String S_BATDELETE_USER = "批量删除用户";
	public static final String D_BATDELETE_USER = "批量删除用户";
	
	/**角色管理模块日志信息定义**/
	public static final String S_ADD_ROLE = "添加角色";
	public static final String D_ADD_ROLE = "添加新角色%s";
	public static final String S_MODIFY_ROLE = "修改角色";
	public static final String D_MODIFY_ROLE = "修改角色“%s”信息";
	public static final String S_DELETE_ROLE = "删除角色";
	public static final String D_DELETE_ROLE = "删除角色“%s”";
	public static final String S_BATDELETE_ROLE = "批量删除角色";
	public static final String D_BATDELETE_ROLE = "批量删除角色";
	public static final String S_MODIFY_PERMISSION = "编辑权限";
	public static final String D_MODIFY_PERMISSION = "编辑“%s”角色的权限";
	
	/**菜单管理模块日志信息定义**/
	public static final String S_MODIFY_MENU = "修改菜单名称";
	public static final String D_MODIFY_MENU = "将菜单“%s”改为“%s”";
	public static final String S_SORT_MENU = "菜单排序";
	public static final String D_SORT_MENU = "修改菜单顺序";
	public static final String S_ADD_MENU = "添加菜单";
	public static final String D_ADD_MENU = "添加新菜单%s";
	public static final String S_DELETE_MENU = "删除菜单";
	public static final String D_DELETE_MENU = "删除菜单%s";

	
	/**升级包信息管理**/
	public static final String S_ADD_UPGRADE = "添加升级包";
	public static final String D_ADD_UPGRADE = "添加升级包“%s”";
	public static final String S_MODIFY_UPGRADE = "修改升级包";
	public static final String D_MODIFY_UPGRADE = "修改升级包“%s”信息";
	public static final String S_DELETE_UPGRADE = "删除升级包";
	public static final String D_DELETE_UPGRADE = "删除升级包“%s”";
	public static final String S_BATDELETE_UPGRADE = "批量删除";
	public static final String D_BATDELETE_UPGRADE = "批量删除升级包";
	

	/**医院信息管理**/
	public static final String S_ADD_AGENCY = "添加医院信息";
	public static final String D_ADD_AGENCY = "添加医院“%s”";
	public static final String S_MODIFY_AGENCY = "修改医院";
	public static final String D_MODIFY_AGENCY = "修改医院“%s”信息";
	public static final String S_DELETE_AGENCY = "删除医院";
	public static final String D_DELETE_AGENCY = "删除医院“%s”";
	public static final String S_BATDELETE_AGENCY = "批量删除";
	public static final String D_BATDELETE_AGENCY = "批量删除医院";


	/**医生*/
	public static final String S_ADD_DOCTOR = "添加医生信息";
	public static final String D_ADD_DOCTOR = "添加医生“%s”";
	public static final String S_MODIFY_DOCTOR = "修改医生";
	public static final String D_MODIFY_DOCTOR = "修改医生“%s”信息";
	public static final String S_DELETE_DOCTOR = "删除医生";
	public static final String D_DELETE_DOCTOR = "删除医生“%s”";

	/**
	 * 配置内容管理
	 */
	public static final String S_ADD_CONFIG = "添加配置内容";
	public static final String D_ADD_CONFIG = "添加配置内容“%s”";
	public static final String S_DELETE_CONFIG = "删除配置内容";
	public static final String D_DELETE_CONFIG = "删除配置内容“%s”";
	public static final String S_MODIFY_CONFIG = "修改配置内容";
	public static final String D_MODIFY_CONFIG = "修改配置内容“%s”";
	
	

	/**
	 * 升级配置管理
	 */
	public static final String S_ADD_UPGRADECONFIG = "添加升级配置";
	public static final String D_ADD_UPGRADECONFIG = "添加升级配置“%s”";
	public static final String S_DELETE_UPGRADECONFIG = "删除升级配置";
	public static final String D_DELETE_UPGRADECONFIG = "删除升级配置“%s”";
	public static final String S_BATDELETE_UPGRADECONFIG = "批量删除升级配置";
	public static final String D_BATDELETE_UPGRADECONFIG = "批量删除升级配置";



}
