package com.gravel.controller;

import com.gravel.redis.RedisService;
import com.gravel.webmagic.pageprocessor.KDLipProxyPoolProcessor;
import com.gravel.webmagic.pipeline.IPSpiderPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gravel on 2018/04/13.
 */
@Controller
public class StartUpController {

    @Autowired
    IPSpiderPipeline ipSpiderPipeline;

    @Autowired
    KDLipProxyPoolProcessor kdlProcessor;

    @Autowired
    private RedisService redisService;


    /**
     * index页面展示
     * @return
     */
    @RequestMapping("/")
    public String index() {
        //爬取结果之前，先删除ip的缓存
        String key = "ipList";
        redisService.remove(key);
        new Thread(() -> kdlProcessor.start(kdlProcessor, ipSpiderPipeline)).start();
        return "/index";
    }

}
