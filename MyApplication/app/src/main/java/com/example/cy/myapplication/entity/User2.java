package com.example.cy.myapplication.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by joson on 2017/4/27.
 */

@Entity
public class User2 {
    @Id
    private Long id;
    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "SEX")
    private String sex;
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 932439418)
    public User2(Long id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }
    @Generated(hash = 377798761)
    public User2() {
    }
}
