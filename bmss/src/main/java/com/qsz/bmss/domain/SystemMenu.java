package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sys_menu")
public class SystemMenu {
    @Id
    @Column(name="menu_id")
    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private Integer parentMenuId;

    private boolean visible;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_menu",inverseJoinColumns = @JoinColumn(name = "role_id"),joinColumns = @JoinColumn(name = "menu_id"))
    private List<SystemRole> roleList;
}
