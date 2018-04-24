package com.gravel.webmagic.pageprocessor;

import com.gravel.domain.ProxyIp;
import com.gravel.utils.IpUtils;
import com.gravel.utils.UserAgentUtil;
import com.gravel.webmagic.pipeline.IPSpiderPipeline;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gravel on 18/04/13.
 */
@Component
public class KDLipProxyPoolProcessor implements PageProcessor {

    private Site site = Site.me().setDisableCookieManagement(true)
            .setTimeOut(6000).setRetryTimes(3)
            .setSleepTime(1000)
            .setCharset("UTF-8")
            .addHeader("Accept-Encoding", "/")
            .setUserAgent(UserAgentUtil.getRandomUserAgent());

    @Override
    public void process(Page page) {
        List<String> ipList = page.getHtml().xpath("//table[@class='table table-bordered table-striped']/tbody/tr").all();
        List<ProxyIp> result = new ArrayList<>();

        if(ipList != null && ipList.size() > 0){
            for(String tmp : ipList){
                Html html = Html.create(tmp);
                ProxyIp proxyIp = new ProxyIp();
                String[] data = html.xpath("//body/text()").toString().trim().split("\\s+");

                proxyIp.setIp(data[0]);
                proxyIp.setPort(Integer.valueOf(data[1]));

                //暂时取消ip验证
               // if(IpUtils.checkIP(data[0],Integer.valueOf(data[1]))){
                    result.add(proxyIp);
                //}
            }
        }
        page.putField("result", result);
        for(int i=1;i<15;i++) {
            page.addTargetRequest("http://www.kuaidaili.com/free/inha/"+(i+1)+"/");
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(KDLipProxyPoolProcessor processor, IPSpiderPipeline ipPipeline) {
        final String ip = "forward.xdaili.cn";
        final int port = 80;

        Spider.create(processor)
                .addUrl("http://www.kuaidaili.com/free/")
                .thread(5)
                .addPipeline(ipPipeline)
                .run();
    }
}
