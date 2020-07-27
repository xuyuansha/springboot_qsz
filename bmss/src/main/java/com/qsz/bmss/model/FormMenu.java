package com.qsz.bmss.model;

import lombok.Data;

/**
 * @author sherry.xu
 * @Date 2020/7/27 9:39
 */
@Data
public class FormMenu {
//    {"menuName":"客户管理","menuNumber":"2","menuIcon":"table","menuUrl":"/","parentMenuId":0,"component":"Layout","path":"/contact"}
    private Integer menuId, menuNumber, parentMenuId;
    private String menuName, menuIcon, menuUrl, component, path;
}

