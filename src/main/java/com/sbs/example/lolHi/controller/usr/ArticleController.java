package com.sbs.example.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.service.ArticleService;
import com.sbs.example.lolHi.util.Util;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/list")	
	public String showList(Model model, @RequestParam Map<String, Object> param) {
		
		int totalCount = articleService.getTotalCount();
		int itemsCountInAPage = 10;
		int totalPage = (int)Math.ceil(totalCount / (double)itemsCountInAPage);
		int pageMenuArmSize = 5;
		int page = Util.getAsInt(param.get("page"),1);
		int pageMenuStart = page - pageMenuArmSize;
		if(pageMenuStart < 1) {
			pageMenuStart = 1;
		}
		int pageMenuEnd = page + pageMenuArmSize;
		if (pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;					
		}
		
		param.put("itemsCountInAPage", itemsCountInAPage);
		List<Article> articles = articleService.getArticles(param);		
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageMenuArmSize", pageMenuArmSize);
		model.addAttribute("pageMenuStart", pageMenuStart);
		model.addAttribute("pageMenuEnd", pageMenuEnd);
		model.addAttribute("page", page);
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
		
		articleService.deleteArticle(id);		
				
		//location.href 는 백스페이스를 누르면 삭제 페이지로 이동, location.replace로 해야된다.
		return String.format("<script>alert('%d번 글을 삭제하였습니다.'); location.replace('/usr/article/list') </script>",id);
		
	}
	
	@RequestMapping("/usr/article/modify")	
	public String showModify(Model model, int id) {
		
		Article article = articleService.getArticleById(id);
		model.addAttribute("article", article);
		
		return "usr/article/modify";		
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		
		articleService.modifyArticle(id, title, body);		
				
		//location.href 는 백스페이스를 누르면 삭제 페이지로 이동, location.replace로 해야된다.
		return String.format("<script>alert('%d번 게시글을 수정하였습니다.'); location.replace('/usr/article/detail?id=%d') </script>",id,id);
		
	}
	
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		
		return "usr/article/write";
		
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(@RequestParam Map<String, Object> param) {
		
		int id = articleService.writeArticle(param);		
				
		//location.href 는 백스페이스를 누르면 삭제 페이지로 이동, location.replace로 해야된다.
		return String.format("<script>alert('%d 게시글을 등록하였습니다.'); location.replace('/usr/article/list') </script>",id);
		
	}

}
