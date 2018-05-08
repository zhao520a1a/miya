package com.miya.search.controller;

import com.golden.util.StringUtil;
import com.miya.search.pojo.SearchResult;
import com.miya.search.feign.SearchService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 搜索服务Controller
 */
@Slf4j
@Controller
public class SearchController {


	@Autowired
	private SearchService searchService;

	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;

	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,
			@RequestParam(defaultValue="1")Integer currPage, Model model) throws Exception {
		try {
			//调用服务执行查询
			SearchResult searchResult = searchService.search(queryString , SEARCH_RESULT_ROWS, currPage);

			if(searchResult != null ) {
				model.addAttribute("currPage", currPage);
				model.addAttribute("totalPages", searchResult.getTotalPages());
				model.addAttribute("itemList", searchResult.getItemList());
			}

			//把结果传递给页面
			model.addAttribute("queryStr", queryString);
		} catch (Exception e) {
			log.error("查询数据{}", StringUtil.getTrace(e));
		}
		//返回逻辑视图
		return "search";
	}
}
