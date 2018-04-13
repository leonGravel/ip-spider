package com.gravel.webmagic.pipeline;

import com.gravel.domain.ProxyIp;
import com.gravel.domain.ProxyIpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * Created by gravel on 2018/04/13.
 */
@Component("IPSpiderPipeline")
public class IPSpiderPipeline implements Pipeline {

    @Autowired
    ProxyIpMapper proxyIpMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().equals("result")) {
                List<ProxyIp> ipList = (List<ProxyIp>) entry.getValue();
                for(ProxyIp proxyIp: ipList) {
                    proxyIpMapper.insert(proxyIp);
                }
            }
        }
    }
}
