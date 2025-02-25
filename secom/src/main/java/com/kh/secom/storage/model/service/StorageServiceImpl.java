package com.kh.secom.storage.model.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

	// 이 클래스 전체가 AWS 사용하는거

	private final AmazonS3 storage; // 여기에 AccessKey와 SecretKey를 담음

	@Value("${cloud.aws.s3.bucketName}")
	private String bucketName; 

	@Override
	public String upload(MultipartFile image) {

		if (image == null || "".equals(image.getOriginalFilename())) {
			throw new RuntimeException("정상적인 파일이 아닙니다..");
		}

		validateImage(image.getOriginalFilename());

		return uploadImageToStorage(image);
	}

	private String uploadImageToStorage(MultipartFile image) {

		String originName = image.getOriginalFilename();
		String ext = originName.substring(originName.lastIndexOf("."));

		// 랜덤한 이름 만들기 UUID.randomUUID는 랜덤한 난수 생성해주는거
		String s3File = UUID.randomUUID().toString().substring(0, 10) + originName;

		try (InputStream is = image.getInputStream();
				ByteArrayInputStream bais = new ByteArrayInputStream(IOUtils.toByteArray(is))) {

			ObjectMetadata metaData = new ObjectMetadata();

			metaData.setContentType("image/" + ext);
			metaData.setContentLength(bais.available());

			// 인자값으로 버킷이름, 파일이름,
			PutObjectRequest por = new PutObjectRequest(bucketName, s3File, bais, metaData)
					.withCannedAcl(CannedAccessControlList.PublicRead);

			// 파일을 올리는건 마지막 한 줄.
			storage.putObject(por);

		} catch (IOException e) {
			throw new RuntimeException("파일이 이상합니다.");
		}

		return storage.getUrl(bucketName, s3File).toString();
	}

	private void validateImage(String fileName) {

		int lastDot = fileName.lastIndexOf(".");

		if (lastDot == -1) {
			throw new RuntimeException("확장자가 없습니다.");
		}

		String extention = fileName.substring(lastDot + 1).toLowerCase();

		List<String> allowedList = Arrays.asList("jpg", "jpeg", "png", "gif");

		if (!allowedList.contains(extention)) {
			throw new RuntimeException("확장자명을 확인해주세요.");
		}

	}

	@Override
	public void delete(String fileUri) {

		String key = "";

		try {
			URL url = new URL(fileUri);
			URLDecoder.decode(url.getPath(), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException("파일 삭제 실패");
		}

		if (!"".equals(key)) {
			storage.deleteObject(new DeleteObjectRequest(
					bucketName, key.substring(key.lastIndexOf("/") + 1))
					);
		}

	}

}
