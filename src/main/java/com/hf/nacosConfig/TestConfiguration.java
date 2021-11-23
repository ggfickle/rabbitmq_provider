package com.hf.nacosConfig;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;

@Configuration
@Data
public class TestConfiguration {

    @NacosValue(value = "${people.count:0}", autoRefreshed = true)
    private String count;

    @NacosConfigListener(dataId = "listener.test", timeout = 500)
    public void onChange(String newContent) throws Exception {
        System.out.println("onChange : " + newContent);
    }
}
