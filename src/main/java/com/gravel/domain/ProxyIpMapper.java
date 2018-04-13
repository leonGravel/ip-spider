package com.gravel.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
/**
 * Created by gravel on 2018/04/13.
 */
@Mapper
public interface ProxyIpMapper {

    @Insert("insert into ips (`ip`,`port`) values (#{ip},#{port})")
    void insert(ProxyIp proxyIp);
}