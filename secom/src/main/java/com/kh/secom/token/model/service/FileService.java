package com.kh.secom.token.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final Path fileLocation;
	
	public FileService() {
		this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
	}
	
	public String store(MultipartFile file) {
		// 이름바꾸는 메서드 호출 등 여기서 처리 (생략)
		
		// 파일 명 뽑아내기
		String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
		
		// 저장 위치 지정
		Path targetLocation = this.fileLocation.resolve(fileName);
		
		// 저장(복사)
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return "http://localhost/uploads/" + fileName;
		} catch (IOException e) {
			throw new RuntimeException("파일을 찾을 수 없습니다.");
		}
		
	}
	
	
	
	
	
	
}
