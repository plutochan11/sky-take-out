package com.pluto.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {
	private String endpoint;
	private String bucketName;
	private String region;
}
