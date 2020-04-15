package com.qsz.bmss.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_menu")
public class SystemMenu {
    @TableId
    private Long menuId;

    private Integer menuNumber;

    private String menuName;

    private String menuUrl;

    private Long parentMenuId;

    private boolean visible;

    private String path;

    private String component;
}
