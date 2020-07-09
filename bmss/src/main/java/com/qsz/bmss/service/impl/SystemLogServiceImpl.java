package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.qsz.bmss.dao.SystemLogDao;
import com.qsz.bmss.domain.SystemLog;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.SelfUser;
import com.qsz.bmss.security.utils.SecurityUtil;
import com.qsz.bmss.service.ISystemLogService;
import com.qsz.bmss.service.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogDao,SystemLog> implements ISystemLogService {
	

	
	@Autowired
    private ISystemUserService systemUserService;
    

    
    private static final String YES = "Y";
    private static final String NO = "N";
    private static final String USER = "用户";

	@Override
	public List<SystemLog> getLogs(Integer pageNo,Integer pageSize, QueryParams params) {
		PageHelper.startPage(pageNo==null?1:pageNo, pageSize==null?10:pageSize);
		List<SystemLog> list = this.baseMapper.selectList(null);

		return list;
	}


	@Override
	public boolean log(String summary, boolean success, String description, Object... params) {
		final String detail = (params != null) ? String.format(description, params) : description;
		String state = success ? YES : NO;
		return logMessage(summary, detail, state);
	}
	
	/**
	 * 向数据库中插入操作日志信息
	 * @param summary
	 * @param detail
	 * @param state 
	 * @return
	 */
	private boolean logMessage(String summary, String detail, String state) {
		SelfUser userInfo = SecurityUtil.getUserInfo();

        SystemLog logInfo = new SystemLog();
        logInfo.setSysUserid(userInfo.getUserId());
        logInfo.setOperateSummary(summary);
        logInfo.setOperateDesc(userInfo.getUsername() + USER + detail);
        logInfo.setOperateState(state);
        logInfo.setOperateDate(new Date());
     
        try {
//        	systemLogMapper.insertSelective(logInfo);
			save(logInfo);
    		return true;
		} catch (Exception e) {
			return false;
		}
	}

}
