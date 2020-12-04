package com.sbs.example.lolHi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.example.lolHi.dto.Article;

@Mapper
public interface ArticleDao {

	Article getArticleById(@Param("id") int id);

	List<Article> getArticles();

	void deleteArticleById(@Param("id") int id);
}
