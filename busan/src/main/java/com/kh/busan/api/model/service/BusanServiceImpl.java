package com.kh.busan.api.model.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kh.busan.api.model.mapper.CommentMapper;
import com.kh.busan.api.model.vo.CommentDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusanServiceImpl implements BusanService {

	private final CommentMapper mapper;
	
	@Override
	public String getBusan(int page) {
		String requestUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
			   requestUrl += "?serviceKey=zXJRP3B7sFjX5Gt%2BWZJinYEjqdZh6GW3f9ixc3OqfluagBJTcjYHb8RyfJkOo77wiskgMvKozwh0kbWiL9ilgg%3D%3D";
			   requestUrl += "&numOfRows=6";
			   requestUrl += "&pageNo=" + page;
			   requestUrl += "&resultType=json";
		   
		URI uri = null;
		
		try {
			uri = new URI(requestUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		
		return response;
	}

	@Override
	public String getBusanDetail(int pk) {
		String requestUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
			   requestUrl += "?serviceKey=zXJRP3B7sFjX5Gt%2BWZJinYEjqdZh6GW3f9ixc3OqfluagBJTcjYHb8RyfJkOo77wiskgMvKozwh0kbWiL9ilgg%3D%3D";
			   requestUrl += "&numOfRows=10";
			   requestUrl += "&pageNo=" + 1;
			   requestUrl += "&resultType=json";
			   requestUrl += "&UC_SEQ=" + pk;
		   
		URI uri = null;
		try {
			uri = new URI(requestUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		
		return response;
	}

	@Override
	public void save(CommentDTO comment) {
		if(comment.getWriter().equals("") || comment.getContent().equals("")) {
			System.out.println("예외가 발생햇씁니다");
		} else {
			mapper.save(comment);
		}
	}

	@Override
	public List<CommentDTO> getComments(Long foodNo) {

		List<CommentDTO> list = mapper.getComments(foodNo);
		
		return list;
		
	}
	
	


}
