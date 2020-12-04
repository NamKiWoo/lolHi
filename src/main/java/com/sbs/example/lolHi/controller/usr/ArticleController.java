package com.sbs.example.lolHi.controller.usr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/list")	
	public String showList(Model model) {
		
		List<Article> articles = articleService.getArticles();
		
		
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")	
	public String showDetail(Model model, int id) {
		
		Article article = articleService.getArticleById(id);		
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
		articleService.deleteArticleById(id);		
				
		//location.href 는 백스페이스를 누르면 삭제 페이지로 이동, location.replace로 해야된다.
		return String.format("<script>alert('%d번 글을 삭제하였습니다.'); location.replace('/usr/article/list') </script>",id);
		
	}

}
