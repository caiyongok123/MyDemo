package com.example.cy.myapplication.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by joson on 2017/4/27.
 */
@Entity
public class User {
    /**
     * @Id表示该字段是id
     * @Property则表示该属性将作为表的一个字段
     * nameInDb 标示在数据库中对应的数据名称
     * @Transient表示这个属性将不会作为数据表中的一个字段
     * @NotNull表示该字段不可以为空
     * @Unique表示该字段唯一
     */

    @Id
    private Long id;
    @Property(nameInDb = "USERNAME")
    private String username;
    @Property(nameInDb = "NICKNAME")
    private String nickname;
    @Property(nameInDb = "ADDRESS")
    private String address;
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Generated(hash = 854598961)
    public User(Long id, String username, String nickname, String address) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.address = address;
    }
    @Generated(hash = 586692638)
    public User() {
    }

}
