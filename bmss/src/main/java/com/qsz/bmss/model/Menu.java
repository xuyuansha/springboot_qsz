package com.qsz.bmss.model;

import com.qsz.bmss.domain.SystemMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Menu extends SystemMenu {
    private String parentMenuName;
    private List<Menu> subMenu;
    private Boolean active;
}
