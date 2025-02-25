package com.kh.secom.storage.model.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	String upload(MultipartFile image);
	
	void delete(String fileUri);
	
}
