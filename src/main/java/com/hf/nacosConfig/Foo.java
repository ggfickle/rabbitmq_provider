package com.hf.nacosConfig;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@NacosConfigurationProperties(dataId = "test")
public class Foo {

    private String dept;

    private String group;
}
