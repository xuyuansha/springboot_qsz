package com.qsz.bmss.service;


import com.github.pagehelper.PageInfo;
import com.qsz.bmss.domain.SystemLog;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.model.QueryParams;

import java.util.List;

public interface ISystemLogService {
	/**
	 * 分页获取系统日志信息
	 * @param keyword 关键字
	 * @return
	 */
	PageInfo<SystemLog> getLogs(Integer pageNo, Integer pageSize, QueryParams keyword);
	/**
	 * 记录日志
	 * @param summary "操作概要"
	 * @param success "操作是否成功"
	 * @param description "含有占位符的描述信息"
	 * 		占位符介绍：
	 * 			%s 字符串类型
	 * 			%c 字符类型
	 * 			%b 布尔型
	 * 			%d 整数类型(十进制)
	 * 			%x 整数类型(十六进制)
	 * 			%o 整数类型(八进制)
	 * @param params "替换占位符的具体参数"
	 * @return
	 */
	boolean log(String summary, boolean success, String description, Object... params);

}
