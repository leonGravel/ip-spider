package com.gravel.controller;

import com.gravel.webmagic.downloader.MyProxyProvider;
import com.gravel.webmagic.pageprocessor.XCipProxyPoolProcessor;
import com.gravel.webmagic.pageprocessor.KDLipProxyPoolProcessor;
import com.gravel.webmagic.pipeline.IPSpiderPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * Created by gravel on 2018/04/13.
 */
@RestController
public class StartUpController {

    @Autowired
    IPSpiderPipeline ipSpiderPipeline;

    /**
     * 抓取西刺代理的ip
     * @return
     */
    @GetMapping("/getXCip")
    public String getXCip() {
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
//                new Proxy("115.218.125.225",9000)
//                ,new Proxy("115.218.120.56",9000)));
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置动态转发代理，使用定制的ProxyProvider
        httpClientDownloader.setProxyProvider(MyProxyProvider.from(new Proxy("forward.xdaili.cn", 80)));
        Spider.create(new XCipProxyPoolProcessor())
                .setDownloader(httpClientDownloader)
                .addUrl("http://www.xicidaili.com/nn")
                .addPipeline(ipSpiderPipeline)
                .thread(4)
                .run();
        return "开始抓取西刺代理的ip";
    }

    /**
     * 抓取快代理的ip
     * @return
     */
    @GetMapping("/getKdlip")
    public String index2() {
        Spider.create(new KDLipProxyPoolProcessor())
                .addUrl("http://www.kuaidaili.com/free/")
                .addPipeline(ipSpiderPipeline)
                .thread(4)
                .run();
        return "开始抓取快代理的ip";
    }
}
