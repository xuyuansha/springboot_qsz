package com.qsz.bmss.model;

import lombok.Data;

/**
 * @author sherry.xu
 * @Date 2020/6/11 16:22
 */
@Data
public class FormRole {
    private Integer roleId;
    private String roleName;
    private String roleLabel;
    private String roleDesc;
    private Integer[] menus;
}
