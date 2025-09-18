package com.pluto.configuration;

import com.aliyuncs.exceptions.ClientException;
import com.pluto.properties.AliyunOssProperties;
import com.pluto.util.AliyunOssFileUploader;
import com.pluto.util.FileUploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AliyunOssProperties.class)
public class FileUploaderConfiguration {
	/**
	 * @return An instance of {@link AliyunOssFileUploader} since we solely provide Aliyun OSS implementation for now
	 */
	@ConditionalOnProperty(prefix = "aliyun.oss", name = {"endpoint", "bucket-name", "region"})
	@Bean
	public FileUploader fileUploader(AliyunOssProperties properties) throws ClientException {
		return new AliyunOssFileUploader(properties);
	}
}
