package com.pluto.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import com.pluto.properties.AliyunOssProperties;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class AliyunOssFileUploader implements FileUploader{
	private AliyunOssProperties aliyunOssProperties;
	private final EnvironmentVariableCredentialsProvider credentialsProvider;
	private final ClientBuilderConfiguration clientBuilderConfiguration;
	private final OSS ossClient;

	public AliyunOssFileUploader(AliyunOssProperties properties) throws ClientException {
		this.aliyunOssProperties = properties;

		credentialsProvider = CredentialsProviderFactory
			  .newEnvironmentVariableCredentialsProvider();

		clientBuilderConfiguration = new ClientBuilderConfiguration();
		clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

		ossClient = OSSClientBuilder.create()
			  .credentialsProvider(credentialsProvider)
			  .clientConfiguration(clientBuilderConfiguration)
			  .endpoint(aliyunOssProperties.getEndpoint())
			  .region(aliyunOssProperties.getRegion())
			  .build();
	}

	@Override
	public String upload(MultipartFile file) {
		String uploadName = generateRandomFileName(file.getOriginalFilename());
		try(InputStream inputStream = file.getInputStream()){
			ossClient.putObject(aliyunOssProperties.getBucketName(), uploadName, inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return String.format("https://%s.%s/%s",
			  aliyunOssProperties.getBucketName(),
			  aliyunOssProperties.getEndpoint(),
			  uploadName);
	}

	private String generateRandomFileName(String originalFilename) {
		Assert.notNull(originalFilename, "Original filename must not be null");
		Assert.hasLength(originalFilename, "Original filename must not be empty");
		// Generate a random UUID and preserve the original file extension
		return UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
	}
}
