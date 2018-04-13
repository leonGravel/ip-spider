package com.gravel.domain;

import java.io.Serializable;
/**
 * Created by gravel on 2018/04/13.
 */
public class ProxyIp implements Serializable{
	private static final long serialVersionUID = -3699072211264713025L;

	private Long id;

    private String ip;

    private Integer port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}