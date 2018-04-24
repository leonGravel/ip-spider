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
    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String redisTest(){
        StringBuffer sb = new StringBuffer();
        redisService.set("str", "str");
        sb.append("str=").append(redisService.get("str").toString()).append(",");

        redisService.hmSet("hmset","key","val");
        sb.append("hmset=").append(redisService.hmGet("hmset","key")).append(",");

        redisService.lPush("list","val");
        sb.append("list=").append(redisService.lRange("list",0,1).toString()).append(",");
        redisService.lPush("list","val1");
        redisService.lPush("list","val2");
        redisService.add("set","val");
        sb.append("set=").append(redisService.setMembers("set").toString()).append(",");
        redisService.zAdd("zset","val1",1);
        redisService.zAdd("zset","val2",2);
        sb.append("zset=").append(redisService.rangeByScore("zset",1,2)).append(",");
        return sb.toString();
    }

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

    @RequestMapping(value = "/delIpList", method = {RequestMethod.GET, RequestMethod.POST})
    public String delIpList(){
        String key = "ipList";
        redisService.remove(key);
        return key+"删除成功！";
    }
}
