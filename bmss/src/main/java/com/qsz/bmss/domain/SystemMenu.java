package com.qsz.bmss.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_menu")
public class SystemMenu {
    @TableId
    private Long menuId;

    private String menuName;

    private String menuUrl;

    private Integer parentMenuId;

    private boolean visible;
}
