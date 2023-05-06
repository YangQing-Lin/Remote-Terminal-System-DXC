package com.group1.group1_backend.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 日志信息表
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 部署服务器名
     */
    private String server;

    /**
     * 服务器ip
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 查看日志需要执行的命令
     */
    private String command;

    /**
     * 密码
     */
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Log{" +
            "id=" + id +
            ", name=" + name +
            ", server=" + server +
            ", ip=" + ip +
            ", port=" + port +
            ", command=" + command +
            ", password=" + password +
        "}";
    }
}
