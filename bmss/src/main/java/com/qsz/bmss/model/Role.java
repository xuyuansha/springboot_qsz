package com.qsz.bmss.model;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author sherry.xu
 * @Date 2020/7/22 15:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends SystemRole {
     private List<SystemMenu> menus;
}
