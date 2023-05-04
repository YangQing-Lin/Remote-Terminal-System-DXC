package com.group1.group1_backend.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 会话信息表
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 主机ip
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Conversation{" +
            "name=" + name +
            ", ip=" + ip +
            ", port=" + port +
            ", username=" + username +
            ", password=" + password +
            ", id=" + id +
        "}";
    }
}
