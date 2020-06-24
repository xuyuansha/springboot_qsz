package com.qsz.bmss.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_menu")
public class SystemMenu {
    @TableId
    private Integer menuId;

    private Integer menuNumber;

    private String menuName;

    private String menuIcon;

    private String menuUrl;

    private Integer parentMenuId;

    private boolean visible;

    private String path;

    private String component;
}
