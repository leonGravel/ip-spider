package com.gravel.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gravel.domain.ProxyIp;
import com.gravel.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value="/redis")
public class RedisTestController {
    @Autowired
    private RedisService redisService;

    /**
     * 首页数据加载
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/getIpList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getIpList(@RequestParam("start")long start,@RequestParam("end") long end){
        Set<Object> ipList = redisService.range("ipList",start,end);
        JSONArray arr = new JSONArray();
       for(Object o : ipList){
           ProxyIp ip = (ProxyIp) o;
           JSONObject json = new JSONObject();
           json.put("ip",ip.getIp());
           json.put("port",ip.getPort());
           arr.add(json);
       }
        return arr.toString();
    }

}
