package com.miya.search.feign;

import com.miya.search.pojo.SearchResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-search-service", fallback = SearchServiceHystric.class)
public interface SearchService {

    @GetMapping(value = "/search")
    SearchResult search(@RequestParam("queryString") String queryString, @RequestParam("limit") int limit ,@RequestParam("currPage") int currPage);

}
