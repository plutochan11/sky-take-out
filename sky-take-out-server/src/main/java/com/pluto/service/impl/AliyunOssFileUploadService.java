package com.pluto.service.impl;

import com.pluto.service.FileUploadService;
import com.pluto.util.FileUploader;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AliyunOssFileUploadService implements FileUploadService {
	@Resource
	private FileUploader fileUploader;

	@Override
	public String upload(MultipartFile file) {
		return fileUploader.upload(file);
	}
}
