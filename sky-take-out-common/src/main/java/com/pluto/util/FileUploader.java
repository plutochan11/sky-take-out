package com.pluto.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
	/**
	 * Upload a file to a storage service and return the URL
	 * @param file The file to be uploaded
	 * @return The URL of the uploaded file
	 */
	String upload(MultipartFile file);
}
