package com.hf.nacosConfig;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@NacosConfigurationProperties(prefix = "apple", dataId = "apple", type = ConfigType.YAML, autoRefreshed = true)
@Configuration
@Data
@ToString
public class Apple {

    private List<String> list;

    private Map<String, List<String>> listMap;
}
