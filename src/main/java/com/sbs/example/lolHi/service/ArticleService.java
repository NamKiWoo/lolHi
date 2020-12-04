package com.sbs.example.lolHi.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.ArticleDao;
import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public List<Article> getArticles() {
		// TODO Auto-generated method stub
		return articleDao.getArticles();
	}

	public Article getArticleById(int id) {
		// TODO Auto-generated method stub
		return articleDao.getArticleById(id);
	}

	public void deleteArticle(int id) {
		// TODO Auto-generated method stub
		articleDao.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		// TODO Auto-generated method stub
		articleDao.modifyArticle(id, title, body);
		
	}

	public int writeArticle(Map<String, Object> param) {
		// TODO Auto-generated method stub
		articleDao.writeArticle(param);
		
//		BigInteger bigIntId = (BigInteger)param.get("id");
//		int id = bigIntId.intValue();
		
		int id = Util.getAsInt(param.get("id"));
		
		return id;
	}

}
