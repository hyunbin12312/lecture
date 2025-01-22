package com.kh.busan.api.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.busan.api.model.vo.CommentDTO;

@Mapper
public interface CommentMapper {
	
	@Insert("INSERT INTO TB_FOOD_REPLY VALUES(#{writer}, #{content}, #{foodNo})")
	void save(CommentDTO commnet);
	
	List<CommentDTO> getCommnets(Long foodNo);
	
}
