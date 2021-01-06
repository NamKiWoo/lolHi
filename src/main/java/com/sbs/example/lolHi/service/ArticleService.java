package com.sbs.example.lolHi.service;

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

	public List<Article> getForPrintArticles(Map<String, Object> param) {
		// TODO Auto-generated method stub
		//페이지 수 가져오기
		int page = Util.getAsInt(param.get("page"),1);
				
		//한페이지에 표시할 개수
		int itemsCountInAPage = Util.getAsInt(param.get("itemsCountInAPage"),10);
		
		if(itemsCountInAPage > 100) {
			itemsCountInAPage = 100;
		} else if (itemsCountInAPage < 1) {
			itemsCountInAPage = 10;
		} 
		
		int limitFrom = (page-1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;
		
		param.put("limitFrom", limitFrom);
		param.put("limitTake", limitTake);
		
		return articleDao.getForPrintArticles(param);
	}

	public Article getForPrintArticleById(int id) {
		// TODO Auto-generated method stub
		return articleDao.getForPrintArticleById(id);
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

	public int getTotalCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return articleDao.getTotalCount(param);
	}

}
